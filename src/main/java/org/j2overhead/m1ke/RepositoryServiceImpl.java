package org.j2overhead.m1ke;

import org.j2overhead.m1ke.Utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class RepositoryServiceImpl implements RepositoryService {
    private final static String DEFAULT_BRANCH = "/.m1ke/branches/master/";

    public RepositoryServiceImpl(File path) {
    }

    public void createFolder(File pathToFolder) {
        if (new File(pathToFolder + "/.m1ke/branches").mkdirs() && new File(pathToFolder + "./m1ke/system").mkdirs()) {
            System.out.println("Program folders is created");
        } else {
            System.out.println("Failed to create folders");
        }
    }

    public void createMasterBranch(File path) {
        for (File file : FileUtils.getFilesFromFolder(path)) {
            try {
                Files.copy(Paths.get(file.getPath()), Paths.get(path + DEFAULT_BRANCH), REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
