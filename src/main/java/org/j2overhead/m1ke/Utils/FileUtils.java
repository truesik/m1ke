package org.j2overhead.m1ke.Utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
}
