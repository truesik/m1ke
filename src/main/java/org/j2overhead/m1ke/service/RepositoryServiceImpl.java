package org.j2overhead.m1ke.service;

import org.j2overhead.m1ke.utils.FileUtils;
import org.j2overhead.m1ke.model.Branch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class RepositoryServiceImpl implements RepositoryService {
    private final static String DEFAULT_BRANCH = "/.m1ke/branches/master/";

    public void createProgramFolders(File pathToFolder) {
        if (new File(pathToFolder + "/.m1ke/branches").mkdirs() && new File(pathToFolder + "./m1ke/system").mkdirs()) {
            System.out.println("Program folders is created");
        } else {
            System.out.println("Failed to create folders");
        }
    }

    public void save(File path, String targetPath) {
        for (File file : FileUtils.getFilesFromFolder(path)) {
            try {
                Files.copy(Paths.get(file.getPath()), Paths.get(path + targetPath), REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createDefault(File path) {
        save(path, DEFAULT_BRANCH);
    }

    @Override
    public List<Branch> scan(String path) {
        List<Branch> branchList = new ArrayList<>();
        List<File> branchesPath = FileUtils.getBranches(new File(path));
//        List<File> fileList = FileUtils.getFilesFromFolder(new File(path));
        for (File branchPath : branchesPath) {
            Branch branch = new Branch();
            branch.setName(getNameOfBranchFolder(branchPath));
            branch.setFiles(FileUtils.getFilesFromFolder(branchPath));
//            branch.set

            branchList.add(branch);
        }
        return branchList;
    }

    private String getNameOfBranchFolder(File branchPath) {
        return branchPath.getParent().substring(branchPath.getParent().lastIndexOf(File.separator) + 1);
    }
}
