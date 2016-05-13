package org.j2overhead.m1ke.utils;

import java.io.*;
import java.util.Properties;

public class InitProperty {
    private static final String INIT_PROPERTY = "init.property";

    private static InitProperty instance;

    public static InitProperty getInstance() {
        if (instance == null) {
            instance = new InitProperty();
        }
        return instance;
    }

    private InitProperty() {
        createInitStatusFile();
    }

    public boolean readInitStatus() {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(INIT_PROPERTY)) {
            properties.load(inputStream);
            if (properties.containsKey("INIT_STATUS")) {
                return isInit(properties.getProperty("INIT_STATUS"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void writeInitStatus(boolean init) {
        Properties properties = new Properties();
        try (OutputStream outputStream = new FileOutputStream(INIT_PROPERTY)) {
            properties.setProperty("INIT_STATUS", init ? "1" : "0");
            properties.store(outputStream, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createInitStatusFile() {
        File file = new File(INIT_PROPERTY);
        try {
            if (file.createNewFile()) {
                writeInitStatus(true);
                System.out.println("File is created");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isInit(String init_status) {
        return init_status.equals("1");
    }
}
