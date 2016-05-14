package org.j2overhead.m1ke.utils;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static boolean compareTwoFiles(File oldFile, File newFile) {
        try {
            FileInputStream oldFileInputStream = new FileInputStream(oldFile);
            DataInputStream oldDataInputStream = new DataInputStream(oldFileInputStream);
            FileInputStream newFileInputStream = new FileInputStream(newFile);
            DataInputStream newDataInputStream = new DataInputStream(newFileInputStream);
            byte oldFileBytes = oldDataInputStream.readByte();
            byte newFilesBytes = newDataInputStream.readByte();
            if (oldFileBytes == newFilesBytes) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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

    public static List<File> getBranches(File file) {
        return null;
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
                    System.out.println("Matching file:" + path.getFileName());
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
}
