package org.j2overhead.m1ke;

import org.j2overhead.m1ke.service.BranchServiceImpl;
import org.j2overhead.m1ke.service.RepositoryServiceImpl;
import org.j2overhead.m1ke.command.CommandsExecutor;

public class AppController {
    public static void main(String[] args) {
        CommandsExecutor commandsExecutor = new CommandsExecutor(new RepositoryServiceImpl(), new BranchServiceImpl());
        commandsExecutor.execute(args);
    }
}
