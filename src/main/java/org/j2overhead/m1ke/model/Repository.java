package org.j2overhead.m1ke.model;

import java.nio.file.Path;
import java.util.List;

public class Repository {
    // path to m1ke repository
    private Path path;
    private List<Branch> branches;
    private Branch lastOpenedBranch;


    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public Branch getLastOpenedBranch() {
        return lastOpenedBranch;
    }

    public void setLastOpenedBranch(Branch lastOpenedBranch) {
        this.lastOpenedBranch = lastOpenedBranch;
    }
}
