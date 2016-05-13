package org.j2overhead.m1ke.model;

import java.nio.file.Path;
import java.util.ArrayList;

public class Repository {
    // path to m1ke repository
    Path path;
    ArrayList<Branch> branches;

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public ArrayList<Branch> getBranches() {
        return branches;
    }

    public void setBranches(ArrayList<Branch> branches) {
        this.branches = branches;
    }
}
