package org.j2overhead.m1ke.service;

import org.j2overhead.m1ke.model.Branch;

import java.io.File;

public interface RepositoryService {

    // getBranchList folder and find Repository of branches,
    // if Repository is not exist - create new m1ke Repository and create new master branch


    void saveBranch(File path, String targetPath);

    void createDefaultBranch(File path);

    void branchRewritePerository(String path, Branch branch);

    void createBranch(String name);

    void removeBranch(String nameOfBranch);

    void saveChangesToBranch(String s);

    void integrate(String pathToFolder);
}
