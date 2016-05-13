package org.j2overhead.m1ke.utils;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
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
            e.printStackTrace();
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

    private String getProgramFolder() {
        URL url = InitProperty.class.getProtectionDomain().getCodeSource().getLocation();
        String parentPath = null;
        try {
            String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
            parentPath = new File(jarPath).getParentFile().getPath();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return parentPath;
    }
}
