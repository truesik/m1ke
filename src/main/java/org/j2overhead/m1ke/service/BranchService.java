package org.j2overhead.m1ke.service;

import org.j2overhead.m1ke.model.Branch;

import java.util.List;

public interface BranchService {

    List<Branch> getBranches(String pathToFolder);

    Branch getBranchByName(String name, String pathToFolder);
}
