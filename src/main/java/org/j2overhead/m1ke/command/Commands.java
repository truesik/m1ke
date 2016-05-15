package org.j2overhead.m1ke.command;

public enum Commands {
    INIT("init"),
    INTEGRATE("integrate"),
    SAVE("save"),
    CREATE_BRANCH("create-branch"),
    REMOVE_BRANCH("remove-branch"),
    GET_BRANCH("get-branch"),
    QUIT("quit");

    private final String name;
    Commands(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }
}
