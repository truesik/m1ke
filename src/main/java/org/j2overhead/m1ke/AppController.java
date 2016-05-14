package org.j2overhead.m1ke;

import org.j2overhead.m1ke.command.CommandsExecutor;

public class AppController {
    public static void main(String[] args) {
        CommandsExecutor commandsExecutor = CommandsExecutor.getInstance();
        commandsExecutor.execute(args);
    }
}
