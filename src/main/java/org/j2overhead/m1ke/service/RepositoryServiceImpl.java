package org.j2overhead.m1ke.service;

import org.j2overhead.m1ke.model.Branch;
import org.j2overhead.m1ke.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.j2overhead.m1ke.utils.FileUtils.*;

public class RepositoryServiceImpl implements RepositoryService {

    private static RepositoryService instance;

    public static RepositoryService getInstance() {
        if (instance == null) {
            instance = new RepositoryServiceImpl();
        }
        return instance;
    }

    public void createProgramFolders(File pathToFolder) {
        if (new File(pathToFolder + DEFAULT_FOLDER + DEFAULT_BRANCHES_FOLDER).mkdirs() && new File(pathToFolder + DEFAULT_FOLDER + DEFAULT_SYSTEM_FOLDER).mkdirs()) {
            System.out.println("Program folders is created");
        } else {
            System.out.println("Failed to create folders");
        }
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
    public void getBranch(String path, Branch branch) {
        FileUtils.deleteFiles(path);
        for (File file : branch.getFiles()) {
            FileUtils.copyFiles(file, path);
        }
    }

    @Override
    public void createBranch(String name) {

    }

    @Override
    public void removeBranch(String nameOfBranch) {

    }

    @Override
    public void saveChangesToBranch(String s) {

    }

    @Override
    public void integrate(String pathToFolder) {

    }
}
