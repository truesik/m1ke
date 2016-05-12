package org.j2overhead.m1ke.Utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    }
}
