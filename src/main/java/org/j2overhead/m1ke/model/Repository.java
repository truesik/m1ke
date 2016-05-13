package org.j2overhead.m1ke.model;

import java.nio.file.Path;
import java.util.List;

public class Repository {
    // path to m1ke repository
    Path path;
    List<Branch> branches;

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

    public Branch getBranchByName(String name) {
        for (Branch branch : branches) {
            if (branch.getName().equals(name)) {
                return branch;
            }
        }
        throw new RuntimeException("Branch doesn't exist.");
    }
}
