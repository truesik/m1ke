package org.j2overhead.m1ke;

import org.j2overhead.m1ke.model.Branch;
import org.j2overhead.m1ke.service.BranchService;
import org.j2overhead.m1ke.service.BranchServiceImpl;
import org.j2overhead.m1ke.service.RepositoryService;
import org.j2overhead.m1ke.service.RepositoryServiceImpl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class AppController {
    private static RepositoryService repositoryService;
    private static BranchService branchService;

    private static void doCommand(String[] args) {
        if (args == null || args.length < 1 || args[0] == null || args[0].isEmpty() || Objects.equals(args[0], "")) {
            System.out.println("bla bla bla wrong help");
        } else if ("init".equals(args[0])) {
            init();
            //Для того чтобы запустить m1ke нужно выполнить команду m1ke 'init"
        } else if ("integrate".equals(args[0])) {

//            К примеру у нас есть папка C:\project . Для того чтобы просканировать папку на файлы и уже сделанные изменения нужно выполнить:
//             m1ke integrate %FOLDER_NAME%
            ArrayList<Branch> branches = repositoryService.scan(args[1]);
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
                repositoryService.createDefault(new File(args[1]));
                // todo: test and checkout logic
            }

        } else if ("-m".equals(args[0])) {

//            Допустим у нас в этой папке есть текстовый файл, который вы только что создали. К примеру, он называется 1.txt
//            1.txt
//            Hello!
//                    Мы хотим закоммитить эти изменения - используем специальную команду
//            >C:\project m1ke save -m "Saving hello message"
//                    -m команда сохранить с комментарием

        } else if ("create-branch".equals(args[0])) {
                init();
                branchService.save(args[1]);
                System.out.println("save " + args[1]);
//            Создать ветку (чтобы был не master к примеру):
//            m1ke create-branch someBranchName

        } else if ("get-branch".equals(args[0])) {
                branchService.get(args[1]);
//            Выбрать ветку:
//            m1ke get-branch someBranchName
//            Когда вы выбираете ветку - вы автоматически подтягиваете изменения которые вы там же и сохранили с помощью m1ke save
//            Если вы переходите на другую ветку, но вы уже сделали какие то изменения, то вы эти изменения теряете.
        } else if ("remove-branch".equals(args[0])) {
                branchService.remove(args[1]);
        } else if ("quit".equals(args[0])) {
            System.out.println("Cya");
            System.exit(0);
        } else if ("help".equals(args[0])) {
            System.out.println("bla bla help: command, command");
        }
    }

    public static void main(String[] args) {
        repositoryService = new RepositoryServiceImpl();
        branchService = new BranchServiceImpl();
        doCommand(args);
    }

    private static void init() {
        System.out.println("startup M1ke");
    }
}
