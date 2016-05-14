package org.j2overhead.m1ke.service;

import org.j2overhead.m1ke.model.Branch;

public class BranchServiceImpl implements BranchService {
    RepositoryService repository = new RepositoryServiceImpl();

    @Override
    public void save(String arg) {
        String userDir = System.getProperty("user.dir");
        System.out.println("Save " + userDir + " with comment: " + arg);
    }

    @Override
    public void remove(String arg) {

    }

    @Override
    public Branch get(String arg) {
        return null;
    }

    @Override
    public void saveWithComment(String path, String comment) {

    }
}
