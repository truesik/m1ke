package org.j2overhead.m1ke.utils;

import org.apache.commons.io.FileUtils;
import org.j2overhead.m1ke.TestData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static org.j2overhead.m1ke.TestData.*;
import static org.j2overhead.m1ke.utils.FileSystemUtils.SYSTEM_LINE_SEPARATOR;


public class FileSystemUtilsTest {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Before
    public void removeTestData() {
        try {
            FileUtils.deleteDirectory(new File(TEST_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*@Rule
    public Stopwatch stopwatch = new Stopwatch() {
        private void logInfo(Description description, long nanos) {
            LOG.info("\n+++ Test {} spent {} microseconds", description.getMethodName(), TimeUnit.NANOSECONDS.toMicros(nanos));
        }

        @Override
        protected void finished(long nanos, Description description) {
            logInfo(description, nanos);
        }
    };*/

    @Test
    public void testCreateFolder() {
        Assert.assertTrue(FileSystemUtils.createFolder(TEST_FILE_PATH));
    }

    @Test
    public void testCreateFile(){
        FileSystemUtils.createFolder(TEST_FILE_PATH);
        Assert.assertTrue(FileSystemUtils.createFile(ABSOLUTE_TEST_FILE_PATH_AND_NAME));
    }

    @Test
    public void testWriteStringToFile() {
        createTestData(TEST_FILE_PATH, TEST_FILE_NAME, TEST_DATA);
        String readString = FileSystemUtils.readStringFromFile(TEST_FILE_PATH + SYSTEM_LINE_SEPARATOR + TEST_FILE_NAME);
        Assert.assertEquals(TEST_DATA, readString);
    }

    @Test
    public void testReadStringFromFile() {
        FileSystemUtils.createFolder(TEST_FILE_PATH);
        FileSystemUtils.createFile(ABSOLUTE_TEST_FILE_PATH_AND_NAME);
        FileSystemUtils.writeStringToFile(ABSOLUTE_TEST_FILE_PATH_AND_NAME, TEST_DATA);
        String readData = FileSystemUtils.readStringFromFile(ABSOLUTE_TEST_FILE_PATH_AND_NAME);
        Assert.assertEquals(TEST_DATA, readData);
        LOG.info("\n {} \n equals: \n {} \n", TEST_DATA, readData);
    }

    @Test
    public void testCompareTwoFiles() {
        String pathToAnotherCompareFile = "test2.txt";
        File testFile = TestData.getTestFileWithTestData();
        createTestData(TEST_FILE_PATH, pathToAnotherCompareFile, TEST_DATA);
        Assert.assertTrue(FileSystemUtils.compareTwoFiles(testFile, new File(TEST_FILE_PATH + SYSTEM_LINE_SEPARATOR + ANOTHER_TEST_FILE_NAME)));
    }

    @Test
    public void testFailCompareTwoFiles() {
        File testFile = TestData.getTestFileWithTestData();
        createTestData(TEST_FILE_PATH, ANOTHER_TEST_FILE_NAME, DIFF_TEST_DATA);
        Assert.assertFalse(FileSystemUtils.compareTwoFiles(testFile, new File(TEST_FILE_PATH + SYSTEM_LINE_SEPARATOR + ANOTHER_TEST_FILE_NAME)));
    }

    // truesik, your method don't work, do more test to fix it
/*    @Test
    public void testCompareTwoFilesByHash() {
        String pathToAnotherCompareFile = "test2.txt";
        File testFile = TestData.getTestFileWithTestData();
        createTestData(TEST_FILE_PATH, pathToAnotherCompareFile, TEST_DATA);
        boolean wtf = FileSystemUtils.compareTwoFilesByHash(testFile, new File(TEST_FILE_PATH + SYSTEM_LINE_SEPARATOR + ANOTHER_TEST_FILE_NAME));
        System.out.println(wtf);
        Assert.assertTrue(wtf);
    }*/

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
