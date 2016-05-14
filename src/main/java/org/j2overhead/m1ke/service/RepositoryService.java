package org.j2overhead.m1ke.service;

import org.j2overhead.m1ke.model.Branch;
import org.j2overhead.m1ke.model.Repository;

import java.io.File;
import java.util.List;

public interface RepositoryService {

    // getBranchList folder and find Repository of branches,
    // if Repository is not exist - create new m1ke Repository and create new master branch
    default Repository integrate(File file) {
        return null;
    }

    void saveBranch(File path, String targetPath);

    void createDefaultBranch(File path);

    List<Branch> getBranchList(String path);

    void getBranch(String path, Branch branch);

    void createBranch(String name);

    void removeBranch(String nameOfBranch);

    void saveChangesToBranch(String s);
}
