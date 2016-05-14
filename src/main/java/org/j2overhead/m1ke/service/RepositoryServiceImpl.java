package org.j2overhead.m1ke.service;

import org.j2overhead.m1ke.model.Repository;
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
    private final static String DEFAULT_FOLDER = "/.m1ke";
    private final static String DEFAULT_BRANCH = "/master";
    private final static String DEFAULT_BRANCHES_FOLDER = "/branches";
    private final static String DEFAULT_SYSTEM_FOLDER = "/system";

    public void createProgramFolders(File pathToFolder) {
        if (new File(pathToFolder + DEFAULT_FOLDER + DEFAULT_BRANCHES_FOLDER).mkdirs() && new File(pathToFolder + DEFAULT_FOLDER + DEFAULT_SYSTEM_FOLDER).mkdirs()) {
            System.out.println("Program folders is created");
        } else {
            System.out.println("Failed to create folders");
        }
    }

    @Override
    public Repository integrate(File file) {
        return null;
    }

    public void saveBranch(File path, String targetPath) {
        if (new File(path.getPath() + targetPath).mkdir()) {
            for (File file : FileUtils.getFilesFromFolder(path)) {
                try {
                    String name = file.getName();
                    Files.copy(Paths.get(file.getPath()), Paths.get(path + targetPath + File.separator + name), REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("не робит");
        }
    }


    public void createDefaultBranch(File path) {
        createProgramFolders(path);
        saveBranch(path, DEFAULT_FOLDER + DEFAULT_BRANCHES_FOLDER + DEFAULT_BRANCH);
    }

    @Override
    public List<Branch> getBranchList(String path) {
        List<Branch> branchList = new ArrayList<>();
        List<File> branchesPath = FileUtils.getBranches(new File(path + DEFAULT_FOLDER + DEFAULT_BRANCHES_FOLDER));
//        List<File> fileList = FileUtils.getFilesFromFolder(new File(path));
        for (File branchPath : branchesPath) {
            Branch branch = new Branch();
            branch.setName(branchPath.getName());
            branch.setFiles(FileUtils.getFilesFromFolder(branchPath));
            //            branch.set

            branchList.add(branch);
        }
        return branchList;
    }

    @Override
    public void getBranch(String path, Branch branch) {
        FileUtils.deleteFiles(path);
        for (File file : branch.getFiles()) {
            copyFiles(file, path);
        }
    }

    @Override
    public void createBranch(String name) {

    }

    @Override
    public void removeBranch(String nameOfBranch) {

    }

    @Override
    public void getBranch(String name) {

    }

    @Override
    public void saveChangesToBranch(String s) {

    }

    private void copyFiles(File file, String path) {
        try {
            Files.copy(Paths.get(file.getPath()), Paths.get(path + file.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
