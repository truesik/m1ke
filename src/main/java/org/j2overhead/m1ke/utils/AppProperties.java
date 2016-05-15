package org.j2overhead.m1ke.utils;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

public class AppProperties {
    private static final String INIT_PROPERTY = "init.property";
    private static final String REPOSITORY_SETTINGS_PATH = ".m1ke" + File.separator + "system";
    private static final String LAST_OPENED_BRANCH = "last-opened-branch.property";

    private static AppProperties instance;

    public static AppProperties getInstance() {
        if (instance == null) {
            instance = new AppProperties();
        }
        return instance;
    }

    private AppProperties() {
    }

    /**
     * При m1ke <command> считываем состояние.
     *
     * @return boolean
     */
    public boolean readInitStatus() {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(getProgramFolder() + File.separator + INIT_PROPERTY)) {
            properties.load(inputStream);
            if (properties.containsKey("INIT_STATUS")) {
                return isInit(properties.getProperty("INIT_STATUS"));
            }
        } catch (IOException e) {
            System.out.println("m1ke must be init ");
            return false;
        }
        return false;
    }

    /**
     * При m1ke init записываем true, при m1ke quit - false.
     *
     * @param init boolean
     */
    public void writeInitStatus(boolean init) {
        Properties properties = new Properties();
        try (OutputStream outputStream = new FileOutputStream(getProgramFolder() + File.separator + INIT_PROPERTY)) {
            properties.setProperty("INIT_STATUS", init ? "1" : "0");
            properties.store(outputStream, null);
            if (init) {
                System.out.println("m1ke is init");
            } else {
                System.out.println("m1ke is deactivate");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Создаем файл в котором храним состояние программы.
     */
    private void createInitStatusFile() {
        File file = new File(getProgramFolder() + File.separator + INIT_PROPERTY);
        try {
            if (file.createNewFile()) {
                writeInitStatus(true);
                System.out.println("Init status file is created");
            } else {
                System.out.println("Init status file already exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isInit(String init_status) {
        return init_status.equals("1");
    }

    private String getProgramFolder() {
        URL url = AppProperties.class.getProtectionDomain().getCodeSource().getLocation();
        String parentPath = null;
        try {
            String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
            parentPath = new File(jarPath).getParentFile().getPath();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return parentPath;
    }

    public String readLastOpenedBranch(String pathOfIntegratedFolder) {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(pathOfIntegratedFolder + File.separator + REPOSITORY_SETTINGS_PATH + File.separator + LAST_OPENED_BRANCH)) {
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
        try (OutputStream outputStream = new FileOutputStream(pathOfIntegratedFolder + File.separator + REPOSITORY_SETTINGS_PATH + LAST_OPENED_BRANCH)) {
            properties.setProperty("LAST_OPENED_BRANCH", name);
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createLastOpenedBranchFile(String pathOfIntegratedFolder) {
        File file = new File(pathOfIntegratedFolder + File.separator + REPOSITORY_SETTINGS_PATH + LAST_OPENED_BRANCH);
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
