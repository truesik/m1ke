package org.j2overhead.m1ke;

import org.j2overhead.m1ke.model.Branch;

public interface BranchService {
    default void save(String arg) {
    }

    default void remove(String arg) {
    }

    default Branch get(String arg) {
        return null;
    }
}
