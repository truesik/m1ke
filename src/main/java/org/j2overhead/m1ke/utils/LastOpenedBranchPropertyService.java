package org.j2overhead.m1ke.utils;

import java.io.*;
import java.util.Properties;

public class LastOpenedBranchPropertyService {
    private static final String SYSTEM_PATH = ".m1ke" + File.separator + "system";
    private static final String LAST_OPENED_BRANCH = "last-opened-branch.property";

    private static LastOpenedBranchPropertyService instance;

    public static LastOpenedBranchPropertyService getInstance() {
        if (instance == null) {
            instance = new LastOpenedBranchPropertyService();
        }
        return instance;
    }

    private LastOpenedBranchPropertyService() {
    }

    public String readLastOpenedBranch(String pathOfIntegratedFolder) {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(pathOfIntegratedFolder + File.separator + SYSTEM_PATH + File.separator + LAST_OPENED_BRANCH)) {
            properties.load(inputStream);
            if (properties.containsKey("LAST_OPENED_BRANCH")) {
                return properties.getProperty("LAST_OPENED_BRANCH");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void writeLastOpenedBranch(String name, String pathOfIntegratedFolder) {
        Properties properties = new Properties();
        try (OutputStream outputStream = new FileOutputStream(pathOfIntegratedFolder + File.separator + SYSTEM_PATH + LAST_OPENED_BRANCH)) {
            properties.setProperty("LAST_OPENED_BRANCH", name);
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createLastOpenedBranchFile(String pathOfIntegratedFolder) {
        File file = new File(pathOfIntegratedFolder + File.separator + SYSTEM_PATH + LAST_OPENED_BRANCH);
        try {
            if (file.createNewFile()) {
                System.out.println("File is created");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
