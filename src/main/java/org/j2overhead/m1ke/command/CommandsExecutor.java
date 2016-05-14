package org.j2overhead.m1ke.command;

import org.j2overhead.m1ke.service.BranchService;
import org.j2overhead.m1ke.service.BranchServiceImpl;
import org.j2overhead.m1ke.service.RepositoryService;
import org.j2overhead.m1ke.service.RepositoryServiceImpl;
import org.j2overhead.m1ke.utils.FileUtils;
import org.j2overhead.m1ke.utils.InitProperty;

public class CommandsExecutor {
    private static CommandsExecutor instance;
    private final RepositoryService repositoryService;
    private final BranchService branchService;

    private CommandsExecutor() {
        this.repositoryService = RepositoryServiceImpl.getInstance();
        this.branchService = BranchServiceImpl.getInstance();
    }

    public static CommandsExecutor getInstance() {
        if (instance == null) {
            instance = new CommandsExecutor();
        }
        return instance;
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
                    branchRewritePerository(args[1]);
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

//            К примеру у нас есть папка C:\project . Для того чтобы просканировать папку на файлы
//            и уже сделанные изменения нужно выполнить:  m1ke integrate %FOLDER_NAME%
//            После этой команды m1ke открывает конкретную юзер ветку (branch) которая там сохранилась
//            (если она там не одна, то открывается та ветка, в которой произошли последние изменения).
    private void integrate(String pathToUserFolder) {
        if (isInit()) {
            repositoryService.integrate(pathToUserFolder);
        }
    }

//            Создать ветку (чтобы был не master к примеру):
//            m1ke create-branch someBranchName
    private void createBranch(String name) {
        if (isInit()) {
            repositoryService.createBranch(name);
        }
    }

    private void removeBranch(String nameOfBranch) {
        if (isInit()) {
            repositoryService.removeBranch(nameOfBranch);
        }
    }
//            Выбрать ветку:
//            m1ke get-branch someBranchName
//            Когда вы выбираете ветку - вы автоматически подтягиваете изменения которые вы там же и сохранили с помощью m1ke saveBranch
//            Если вы переходите на другую ветку, но вы уже сделали какие то изменения, то вы эти изменения теряете.
    private void branchRewritePerository(String name) {
        if (isInit()) {
            repositoryService.branchRewritePerository(FileUtils.CURRENT_RUNTIME_USER_DIR, branchService.getBranchByName(name, FileUtils.CURRENT_RUNTIME_USER_DIR));
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
