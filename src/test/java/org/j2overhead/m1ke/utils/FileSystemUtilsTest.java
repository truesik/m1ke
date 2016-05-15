package org.j2overhead.m1ke.utils;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static org.j2overhead.m1ke.TestData.TEST_FILE_NAME;
import static org.j2overhead.m1ke.TestData.TEST_FILE_PATH;


public class FileSystemUtilsTest {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Before
    public void removeAllTestDataFromTestDirectory() {
        try {
            FileUtils.deleteDirectory(new File(TEST_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateFolder() {
        Assert.assertTrue(FileSystemUtils.createFolder(TEST_FILE_PATH));
    }

    @Test
    public void testCreateFile(){
        FileSystemUtils.createFolder(TEST_FILE_PATH);
        Assert.assertTrue(FileSystemUtils.createFile(TEST_FILE_PATH + File.separator + TEST_FILE_NAME));
    }

    @Test
    public void writeTestDataToTestFile() {

    }

    @Test
    public void readTestDataFromTestFile() {

    }

    @Test
    public void testCompareTwoFiles() {

    }

    @Test
    public void testCompareTwoFilesByHash() {

    }

    @Test
    public void testGetFilesFromFolder() {

    }

    @Test
    public void testGetFolders() {

    }

    @Test
    public void testSaveFileTree() {

    }

    @Test
    public void testDeleteFiles() {

    }

    @Test
    public void testCopyFiles() {

    }


}
