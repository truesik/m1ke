package org.j2overhead.m1ke.service;

import org.j2overhead.m1ke.model.Branch;
import org.j2overhead.m1ke.utils.AppProperties;
import org.j2overhead.m1ke.utils.FileSystemUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.j2overhead.m1ke.utils.FileSystemUtils.*;

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
        if (FileSystemUtils.createFolder(new File(path.getPath() + targetPath).getPath())) {
            for (File file : FileSystemUtils.getFilesFromFolder(path)) {
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
    public void getFilesFromBranch(String pathToIntegratedFolder, Branch branch) {
        // Удаляем файлы из рабочей папки от предыдущей ветки
        FileSystemUtils.deleteFiles(pathToIntegratedFolder);
        // Копируем файлы из нужной ветки в рабочую папку
        for (File file : branch.getFiles()) {
            FileSystemUtils.copyFile(file, pathToIntegratedFolder);
        }
    }

    @Override
    public void createBranch(String name) {
        // проверить есть ли ветка с таким же названием
        // скопировать все файлы из открытой в данный момент ветки в новоую ветку
        // перезаписать last-opened-branch.property
    }

    @Override
    public void removeBranch(String nameOfBranch) {
        // проверить чтобы ветка которую хотим удалить не являлась открытой в данный момент
        // удалить
    }

    @Override
    public void saveChangesToBranch(String s) {

    }

    @Override
    public void integrate(String pathToFolder) {
        if (!branchService.getBranches(pathToFolder).isEmpty()) {
            String nameOfLastOpenedBranch = AppProperties.getInstance().readLastOpenedBranch(pathToFolder);
            Branch lastOpenedBranch = branchService.getBranchByName(nameOfLastOpenedBranch, pathToFolder);
            List<File> lastOpenedBranchFiles = lastOpenedBranch.getFiles();
            List<File> currentFilesFromRepository = FileSystemUtils.getFilesFromFolder(new File(pathToFolder));
            for (File lastOpenedBranchFile : lastOpenedBranchFiles) {
                for (File currentFolderFile : currentFilesFromRepository) {
                    if (!FileSystemUtils.compareTwoFiles(lastOpenedBranchFile, currentFolderFile)) {
                        System.out.println("different");
                        System.out.println("delete your data? y/n");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        try {
                            String m = reader.readLine();
                            if (m.equals("y")) {
                                getFilesFromBranch(pathToFolder, lastOpenedBranch);
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
