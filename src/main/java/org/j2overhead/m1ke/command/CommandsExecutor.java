package org.j2overhead.m1ke.command;

import org.j2overhead.m1ke.service.BranchService;
import org.j2overhead.m1ke.service.BranchServiceImpl;
import org.j2overhead.m1ke.service.RepositoryService;
import org.j2overhead.m1ke.service.RepositoryServiceImpl;
import org.j2overhead.m1ke.utils.AppProperties;
import org.j2overhead.m1ke.utils.FileSystemUtils;

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
                case "init": {
                    init();
                    break;
                }
                case "integrate": {
                    integrate();
                    break;
                }
                case "create-branch": {
                    createBranch(args[1]);
                    break;
                }
                case "remove-branch": {
                    removeBranch(args[1]);
                    break;
                }
                case "get-branch": {
                    if (args.length <= 1) {
                        System.out.println(" You must set name for you branch");
                        help();
                        break;
                    }
                    getBranch(args[1]);
                    break;
                }
                case "save": {
                    save(args);
                    break;
                }
                case "quit": {
                    quit();
                    break;
                }
                case "help": {
                    help();
                    break;
                }
                default: help();
            }
        } else {
            help();
        }
    }

    private void init() {
        AppProperties.getInstance().writeInitStatus(true);
    }

//            К примеру у нас есть папка C:\project . Для того чтобы просканировать папку на файлы
//            и уже сделанные изменения нужно выполнить:  m1ke integrate из командной строки конкретно в этой папке
//            После этой команды m1ke открывает конкретную юзер ветку (branch) которая там сохранилась
//            (если она там не одна, то открывается та ветка, в которой произошли последние изменения).
    private void integrate() {
        if (isInit()) {
            repositoryService.integrate(FileSystemUtils.CURRENT_RUNTIME_USER_DIR);
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
    private void getBranch(String name) {
        if (isInit()) {
            repositoryService.getFilesFromBranch(FileSystemUtils.CURRENT_RUNTIME_USER_DIR, branchService.getBranchByName(name, FileSystemUtils.CURRENT_RUNTIME_USER_DIR));
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
            AppProperties.getInstance().writeInitStatus(false);
            System.out.println("Cya");
        }
    }

    private void help() {
        System.out.println("commands: \n" +
                "    init,\n" +
                "    integrate,\n" +
                "    save,\n" +
                "    create-branch,\n" +
                "    remove-branch,\n" +
                "    get-branch,\n" +
                "    quit");
    }

    private boolean isInit() {
        return AppProperties.getInstance().readInitStatus();
    }
}
