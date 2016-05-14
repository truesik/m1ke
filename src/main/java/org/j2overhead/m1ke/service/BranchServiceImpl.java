package org.j2overhead.m1ke.service;

import org.j2overhead.m1ke.model.Branch;
import org.j2overhead.m1ke.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.j2overhead.m1ke.utils.FileUtils.DEFAULT_BRANCHES_FOLDER;
import static org.j2overhead.m1ke.utils.FileUtils.DEFAULT_FOLDER;

public class BranchServiceImpl implements BranchService {

    private static BranchService instance;
    private RepositoryService repositoryService;

    private BranchServiceImpl() {
        this.repositoryService = RepositoryServiceImpl.getInstance();
    }

    public static BranchService getInstance() {
        if (instance == null) {
            instance = new BranchServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Branch> getBranches(String pathToFolder) {
        List<Branch> branchList = new ArrayList<>();
        List<File> branchesPath = FileUtils.getFolders(new File(pathToFolder + DEFAULT_FOLDER + DEFAULT_BRANCHES_FOLDER));
        for (File branchPath : branchesPath) {
            Branch branch = new Branch();
            branch.setName(branchPath.getName());
            branch.setFiles(FileUtils.getFilesFromFolder(branchPath));
            branchList.add(branch);
        }
        return branchList;
    }

    public Branch getBranchByName(String name) {
        for (Branch branch : getBranches(name)) {
            if (branch.getName().equals(name)) {
                return branch;
            }
        }
        throw new RuntimeException("Branch doesn't exist.");
    }
}
