package org.j2overhead.m1ke;

import org.j2overhead.m1ke.model.Branch;

public interface BranchService {
    default void save() {
    }

    default void remove() {
    }

    default Branch get() {
        return null;
    }
}
