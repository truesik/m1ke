package org.j2overhead.m1ke.service;

import org.j2overhead.m1ke.model.Branch;
import org.j2overhead.m1ke.utils.FileUtils;

public class BranchServiceImpl implements BranchService {
    RepositoryService repository = new RepositoryServiceImpl();

    @Override
    public void save(String comment) {
        FileUtils.saveFileTree(comment);
    }

    @Override
    public void remove(String arg) {

    }

    @Override
    public Branch get(String arg) {
        return null;
    }
}
