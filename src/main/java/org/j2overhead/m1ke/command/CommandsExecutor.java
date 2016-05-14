package org.j2overhead.m1ke.command;

import org.j2overhead.m1ke.model.Branch;
import org.j2overhead.m1ke.model.Repository;
import org.j2overhead.m1ke.service.RepositoryService;
import org.j2overhead.m1ke.utils.FileUtils;
import org.j2overhead.m1ke.utils.InitProperty;
import org.j2overhead.m1ke.utils.LastOpenedBranchPropertyService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CommandsExecutor {
    private final RepositoryService repositoryService;

    public CommandsExecutor(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    public void execute(String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "init":
                    init();
                    break;
                case "integrate":
                    integrate(args[1]);
                    break;
                case "create-branch":
                    createBranch(args[1]);
                    break;
                case "remove-branch":
                    removeBranch(args[1]);
                    break;
                case "get-branch":
                    getBranch(args[1]);
                    break;
                case "save":
                    save(args);
                    break;
                case "quit":
                    quit();
                    break;
                case "help":
                    help();
                    break;
            }
        } else {
            System.out.println("m1ke help");
        }
    }

    private void init() {
        //Для того чтобы запустить m1ke нужно выполнить команду m1ke 'init"
        InitProperty.getInstance().writeInitStatus(true);
        System.out.println("m1ke initiated");
    }

    private void integrate(String pathToFolder) {
        if (isInit()) {
//            К примеру у нас есть папка C:\project . Для того чтобы просканировать папку на файлы
// и уже сделанные изменения нужно выполнить:  m1ke integrate %FOLDER_NAME%
            Repository repository = new Repository();
          //  repository.setPath();
            repository.setBranches(repositoryService.getBranchList(pathToFolder));
//            После этой команды m1ke открывает конкретную юзер ветку (branch) которая там сохранилась
//             (если она там не одна, то открывается та ветка, в которой произошли последние изменения).
            if (!repository.getBranches().isEmpty()) {
                String nameOfLastOpenedBranch = LastOpenedBranchPropertyService.getInstance().readLastOpenedBranch(pathToFolder);
                Branch lastOpenedBranch = repository.getBranchByName(nameOfLastOpenedBranch);
                repository.setLastOpenedBranch(lastOpenedBranch);
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
                                    repositoryService.getBranch(pathToFolder, lastOpenedBranch);
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
                repositoryService.createDefaultBranch(new File(pathToFolder));
                System.out.println("" +
                        "m1ke found there was no branch here\n" +
                        "'master' branch choosed");
            }

        }
    }

    private void createBranch(String name) {
        if (isInit()) {
            repositoryService.createBranch(name);
//            Создать ветку (чтобы был не master к примеру):
//            m1ke create-branch someBranchName
        }
    }

    private void removeBranch(String nameOfBranch) {
        if (isInit()) {
            repositoryService.removeBranch(nameOfBranch);
        }
    }

    private void getBranch(String name) {
        if (isInit()) {
            repositoryService.getBranch(name);
//            Выбрать ветку:
//            m1ke get-branch someBranchName
//            Когда вы выбираете ветку - вы автоматически подтягиваете изменения которые вы там же и сохранили с помощью m1ke saveBranch
//            Если вы переходите на другую ветку, но вы уже сделали какие то изменения, то вы эти изменения теряете.
        }
    }

    private void save(String[] args) {
        if (isInit()) {
            if (args.length > 2 && args[1] != null && (args[1]).equals("-m")) {
                repositoryService.saveChangesToBranch(args[2]);
                System.out.println("saveBranch " + args[2]);
            } else {
                repositoryService.saveChangesToBranch("");
            }
        }
    }

    private void quit() {
        if (isInit()) {
            InitProperty.getInstance().writeInitStatus(false);
            System.out.println("Cya");
        }
    }

    private void help() {
        System.out.println("bla bla help: command, command");
    }

    private boolean isInit() {
        return InitProperty.getInstance().readInitStatus();
    }
}
