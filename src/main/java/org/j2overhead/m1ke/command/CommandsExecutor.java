package org.j2overhead.m1ke.command;

import org.j2overhead.m1ke.model.Branch;
import org.j2overhead.m1ke.service.BranchService;
import org.j2overhead.m1ke.service.RepositoryService;
import org.j2overhead.m1ke.utils.InitProperty;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class CommandsExecutor {
    private final RepositoryService repositoryService;
    private final BranchService branchService;

    public CommandsExecutor(RepositoryService repositoryService, BranchService branchService) {
        this.repositoryService = repositoryService;
        this.branchService = branchService;
    }

    public void execute(String[] args) {
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
            default:
                System.out.println("No such command");
                break;
        }
    }

    private void init() {
        //Для того чтобы запустить m1ke нужно выполнить команду m1ke 'init"
        InitProperty.getInstance().writeInitStatus(true);
        System.out.println("m1ke initiated");
    }

    private void integrate(String pathToFolder) {
        if (isInit()) {
//            К примеру у нас есть папка C:\project . Для того чтобы просканировать папку на файлы и уже сделанные изменения нужно выполнить:
//             m1ke integrate %FOLDER_NAME%
            List<Branch> branches = repositoryService.scan(pathToFolder);
//            После этой команды m1ke открывает конкретную юзер ветку (branch) которая там сохранилась
//             (если она там не одна, то открывается та ветка, в которой произошли последние изменения).
            if (!branches.isEmpty()) {
                //зарефакторить нахер эти лямбды, но потом :)
                final LocalDateTime[] lastUpdate = {LocalDateTime.MIN};
                branches.forEach(branch -> {
                    if (branch.getLastUpdate().isAfter(lastUpdate[0])) {
                        lastUpdate[0] = branch.getLastUpdate();
                    }
                });
                LocalDateTime lastUpdateTime = lastUpdate[0];

                final Branch[] lastUpdateBranch = new Branch[1];
                branches.forEach(branch -> {
                    if (branch.getLastUpdate().equals(lastUpdateTime)) {
                        lastUpdateBranch[0] = branch;
                    }
                });
            } else {
                //             Если в этой папке ничего никогда со стороны m1ke не запускалось то
                // запускается первичная ветка master. Юзер должен об этом предупрежден сообщением.
                System.out.println("Bla bla no branches, create new branch");
                repositoryService.createDefault(new File(pathToFolder));
                // todo: test and checkout logic
            }

        }
    }

    private void createBranch(String name) {
        if (isInit()) {
//            Создать ветку (чтобы был не master к примеру):
//            m1ke create-branch someBranchName
        }
    }

    private void removeBranch(String nameOfBranch) {
        if (isInit()) {
            branchService.remove(nameOfBranch);
        }
    }

    private void getBranch(String name) {
        if (isInit()) {
            branchService.get(name);
//            Выбрать ветку:
//            m1ke get-branch someBranchName
//            Когда вы выбираете ветку - вы автоматически подтягиваете изменения которые вы там же и сохранили с помощью m1ke save
//            Если вы переходите на другую ветку, но вы уже сделали какие то изменения, то вы эти изменения теряете.
        }
    }

    private void save(String[] args) {
        if (isInit()) {
            if (args[1] != null && args[1].equals("-m")) {
                //?????????
                branchService.save(args[2]);
                System.out.println("save " + args[2]);
            } else {
                branchService.save("");
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
