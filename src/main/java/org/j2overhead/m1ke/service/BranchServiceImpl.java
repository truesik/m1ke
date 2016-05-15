package org.j2overhead.m1ke.service;

import org.j2overhead.m1ke.model.Branch;
import org.j2overhead.m1ke.utils.FileSystemUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.j2overhead.m1ke.utils.FileSystemUtils.DEFAULT_BRANCHES_FOLDER;
import static org.j2overhead.m1ke.utils.FileSystemUtils.DEFAULT_FOLDER;

public class BranchServiceImpl implements BranchService {

    private static BranchService instance;

    private BranchServiceImpl() {
    }

    public static BranchService getInstance() {
        if (instance == null) {
            instance = new BranchServiceImpl();
        }
        return instance;
    }


    /**
     * Сканирует папку /.m1ke/branches и выдает список всех созданных веток (папок).
     *
     * @param pathToFolder путь к запрашиваемой папке юзера.
     * @return Список папок (веток).
     */
    @Override
    public List<Branch> getBranches(String pathToFolder) {
        List<Branch> branchList = new ArrayList<>();
        List<File> branchesPath = FileSystemUtils.getFolders(new File(pathToFolder + DEFAULT_FOLDER + DEFAULT_BRANCHES_FOLDER));
        for (File branchPath : branchesPath) {
            Branch branch = new Branch();
            branch.setName(branchPath.getName());
            branch.setFiles(FileSystemUtils.getFilesFromFolder(branchPath));
            branchList.add(branch);
        }
        return branchList;
    }

    public Branch getBranchByName(String name, String pathToFolder) {
        for (Branch branch : getBranches(pathToFolder)) {
            if (branch.getName().equals(name)) {
                return branch;
            }
        }
        throw new RuntimeException("Branch doesn't exist.");
    }
}
