package org.j2overhead.m1ke.service;

import org.j2overhead.m1ke.model.Branch;

public interface BranchService {
    void save(String arg);

    void remove(String arg);

    Branch get(String arg);
}
