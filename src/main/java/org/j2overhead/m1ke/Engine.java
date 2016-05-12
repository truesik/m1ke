package org.j2overhead.m1ke;

import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Engine {
    private final static String DEFAULT_BRANCH = "/.m1ke/branches/master/";
    private File path;

    public Engine(File path) {

        this.path = path;
    }

    public void createProgramFolder(File pathToFolder) {
        if (new File(pathToFolder + "/.m1ke/branches").mkdirs() && new File(pathToFolder + "./m1ke/system").mkdirs()) {
            System.out.println("Program folders is created");
        } else {
            System.out.println("Failed to create folders");
        }
    }

    public List<File> getFilesFromFolder(File pathToFolder) {
        List<File> fileList = new ArrayList<>();
        File[] files = pathToFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileList.add(file);
                }
            }
        }
        return fileList;
    }

    public void createMasterBranch() {
        for (File file : getFilesFromFolder(path)) {
            try {
                Files.copy(Paths.get(file.getPath()), Paths.get(path + DEFAULT_BRANCH), REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
