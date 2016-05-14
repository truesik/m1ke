package org.j2overhead.m1ke.service;

import org.j2overhead.m1ke.model.Branch;
import org.j2overhead.m1ke.utils.FileUtils;
import org.j2overhead.m1ke.utils.LastOpenedBranchPropertyService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.j2overhead.m1ke.utils.FileUtils.*;

public class RepositoryServiceImpl implements RepositoryService {

    private static RepositoryService instance;
    private BranchService branchService;

    private RepositoryServiceImpl() {
        branchService = BranchServiceImpl.getInstance();
    }

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
    public void branchRewritePerository(String path, Branch branch) {
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
        if (!branchService.getBranches(pathToFolder).isEmpty()) {
            String nameOfLastOpenedBranch = LastOpenedBranchPropertyService.getInstance().readLastOpenedBranch(pathToFolder);
            Branch lastOpenedBranch = branchService.getBranchByName(nameOfLastOpenedBranch, pathToFolder);
            List<File> files = lastOpenedBranch.getFiles();
            List<File> filesFromFolder = FileUtils.getFilesFromFolder(new File(pathToFolder));
            for (File file : files) {
                for (File file1 : filesFromFolder) {
                    if (!FileUtils.compareTwoFiles(file, file1)) {
                        System.out.println("different");
                        System.out.println("delete your bullshit? y/n");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        try {
                            String m = reader.readLine();
                            if (m.equals("y")) {
                                branchRewritePerository(pathToFolder, lastOpenedBranch);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        System.out.println("same");
                    }
                }
            }
        } else {
            //             Если в этой папке ничего никогда со стороны m1ke не запускалось то
            // запускается первичная ветка master. Юзер должен об этом предупрежден сообщением.
            createDefaultBranch(new File(pathToFolder));
            System.out.println("m1ke found there was no branch here\n" +
                    "'master' branch choosed");
        }
    }
}
