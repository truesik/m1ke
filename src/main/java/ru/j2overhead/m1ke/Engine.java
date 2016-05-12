package ru.j2overhead.m1ke;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Engine {
    public Engine() {

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
}
