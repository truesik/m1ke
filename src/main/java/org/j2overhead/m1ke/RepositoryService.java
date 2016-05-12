package org.j2overhead.m1ke;

import org.j2overhead.m1ke.model.Branch;
import org.j2overhead.m1ke.model.Repository;

import java.io.File;
import java.util.ArrayList;

interface RepositoryService {

    // scan folder and find Repository of branches,
    // if Repository is not exist - create new m1ke Repository and create new master branch
    default Repository integrate(File file) {
        return null;
    }

    void save(File path, String targetPath);

    void createDefault(File path);

    ArrayList<Branch> scan(String path);
}
