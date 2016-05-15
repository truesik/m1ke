package org.j2overhead.m1ke.utils;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileSystemUtils {
    public final static String DEFAULT_FOLDER = File.separator + ".m1ke";
    public final static String DEFAULT_BRANCH = File.separator + "master";
    public final static String DEFAULT_BRANCHES_FOLDER = File.separator + "branches";
    public final static String DEFAULT_SYSTEM_FOLDER = File.separator + "system";
    public final static String CURRENT_RUNTIME_USER_DIR = System.getProperty("user.dir");

    public static boolean compareTwoFiles(File oldFile, File newFile) {
        try (Scanner scannerFileOne = new Scanner(new FileInputStream(oldFile.getPath()));
             Scanner scannerFileTwo = new Scanner(new FileInputStream(newFile.getPath()))){
            while (scannerFileOne.hasNextLine()) {
                String fileTwoNext = null;
                String fileOneNext = null;
                try {
                    fileTwoNext = scannerFileTwo.nextLine();
                    fileOneNext = scannerFileOne.nextLine();
                } catch (NoSuchElementException e) {

                    return false;
                }

                if (fileOneNext == null || fileTwoNext == null) {
                    return false;
                }
                if (!(fileOneNext).equals(fileTwoNext)) {
                    return false;
                }
            }
            scannerFileOne.close();
            scannerFileTwo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean compareTwoFilesByHash(File oldFile, File newFile) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            InputStream oldFileInputStream = Files.newInputStream(Paths.get(oldFile.getPath()));
            InputStream newFileInputStream = Files.newInputStream(Paths.get(newFile.getPath()));
            DigestInputStream oldFileDigestInputStream = new DigestInputStream(oldFileInputStream, messageDigest);
            DigestInputStream newFileDigestInputStream = new DigestInputStream(newFileInputStream, messageDigest);
            messageDigest.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<File> getFilesFromFolder(File pathToFolder) {
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

    public static List<File> getFolders(File file) {
        List<File> folderList = new ArrayList<>();
        File[] folders = file.listFiles();
        if (folders != null) {
            for (File folder : folders) {
                if (folder.isDirectory()) {
                    folderList.add(folder);
                }
            }
        }
        return folderList;
    }

    public static void saveFileTree(String comment) {
        String userDir = System.getProperty("user.dir");
        class MyFileFindVisitor extends SimpleFileVisitor<Path> {

            public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttributes) {
                find(path);
                return FileVisitResult.CONTINUE;
            }

            public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes fileAttributes) {
                find(path);
                return FileVisitResult.CONTINUE;
            }

            private PathMatcher matcher;

            private MyFileFindVisitor(String pattern) {
                try {
                    matcher = FileSystems.getDefault().getPathMatcher(pattern);
                } catch (IllegalArgumentException iae) {
                    System.err.println("Invalid pattern; did you forget to prefix \"glob:\" or \"regex:\"?");
                    System.exit(1);
                }
            }

            private void find(Path path) {
                Path name = path.getFileName();
                if (matcher.matches(name)) {
                    if (Files.isDirectory(path)) {
                        System.out.println("Save directory:" + path.getFileName());
                    } else {
                        System.out.println("Save file:" + path.getFileName());
                    }
                }
            }
        }

        try {
            String pattern = "glob:*";
            System.out.println("File list: ");
            Files.walkFileTree(Paths.get(userDir), new MyFileFindVisitor(pattern));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Save " + userDir + (comment.equals("") ? "" : " with comment: " + comment));
    }

    public static void deleteFiles(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        if (files != null) {
            for (File file1 : files) {
                if (file1.isFile()) {
                    try {
                        Files.delete(Paths.get(file1.getPath()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void copyFiles(File file, String path) {
        try {
            Files.copy(Paths.get(file.getPath()), Paths.get(path + File.separator + file.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean createFolder(String path) {
        try {
            Files.createDirectory(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return Files.exists(Paths.get(path));
    }

    public static boolean createFile(String path) {
        try {
            Files.createFile(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return Files.exists(Paths.get(path));
    }
}
