package org.j2overhead.m1ke.utils;

import org.apache.commons.io.FileUtils;
import org.j2overhead.m1ke.TestData;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.j2overhead.m1ke.TestData.*;
import static org.j2overhead.m1ke.utils.FileSystemUtils.SYSTEM_LINE_SEPARATOR;
import static org.junit.Assert.*;


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

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
        private void logInfo(Description description, long nanos) {
            LOG.info("\n+++ Test {} spent {} microseconds", description.getMethodName(), TimeUnit.NANOSECONDS.toMicros(nanos));
        }

        @Override
        protected void finished(long nanos, Description description) {
            logInfo(description, nanos);
        }
    };

    @Test
    public void testCreateFolder() {
        assertTrue(FileSystemUtils.createFolder(TEST_FILE_PATH));
    }

    @Test
    public void testCreateFile() {
        FileSystemUtils.createFolder(TEST_FILE_PATH);
        assertTrue(FileSystemUtils.createFile(ABSOLUTE_TEST_FILE_PATH_AND_NAME));
    }

    @Test
    public void testWriteStringToFile() {
        createTestData(TEST_FILE_PATH, TEST_FILE_NAME, TEST_DATA);
        String readString = FileSystemUtils.readStringDataFromFile(TEST_FILE_PATH + SYSTEM_LINE_SEPARATOR + TEST_FILE_NAME);
        assertEquals(TEST_DATA, readString);
    }

    @Test
    public void testReadStringFromFile() {
        FileSystemUtils.createFolder(TEST_FILE_PATH);
        FileSystemUtils.createFile(ABSOLUTE_TEST_FILE_PATH_AND_NAME);
        FileSystemUtils.writeStringDataToFile(ABSOLUTE_TEST_FILE_PATH_AND_NAME, TEST_DATA);
        String readData = FileSystemUtils.readStringDataFromFile(ABSOLUTE_TEST_FILE_PATH_AND_NAME);
        assertEquals(TEST_DATA, readData);
        LOG.info("\n {} \n equals: \n {} \n", TEST_DATA, readData);
    }

    @Test
    public void testCompareTwoFiles() {
        File testFile = TestData.getTestFileWithTestData();
        createTestData(TEST_FILE_PATH, ANOTHER_TEST_FILE_NAME, TEST_DATA);
        assertTrue(FileSystemUtils.compareTwoFiles(testFile, new File(TEST_FILE_PATH + SYSTEM_LINE_SEPARATOR + ANOTHER_TEST_FILE_NAME)));
    }

    @Test
    public void testFailCompareTwoFiles() {
        File testFile = TestData.getTestFileWithTestData();
        createTestData(TEST_FILE_PATH, ANOTHER_TEST_FILE_NAME, DIFF_TEST_DATA);
        assertFalse(FileSystemUtils.compareTwoFiles(testFile, new File(TEST_FILE_PATH + SYSTEM_LINE_SEPARATOR + ANOTHER_TEST_FILE_NAME)));
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
        createTestData(TEST_FILE_PATH, TEST_FILE_NAME, TEST_DATA);
        createTestData(TEST_FILE_PATH, ANOTHER_TEST_FILE_NAME, DIFF_TEST_DATA);
        ArrayList<File> files = new ArrayList<File>();
        files.add(new File(TEST_FILE_PATH + SYSTEM_LINE_SEPARATOR + TEST_FILE_NAME));
        files.add(new File(TEST_FILE_PATH + SYSTEM_LINE_SEPARATOR + ANOTHER_TEST_FILE_NAME));
        List<File> filesFromFolder = FileSystemUtils.getFilesFromFolder(new File(TEST_FILE_PATH));
        assertArrayEquals(filesFromFolder.toArray(), files.toArray());
    }

    @Test
    public void testGetFolders() {
        FileSystemUtils.createFolder(TEST_FILE_PATH);
        FileSystemUtils.createFolder("D://test/test1");
        FileSystemUtils.createFolder("D://test/test2");
        FileSystemUtils.createFolder("D://test/test3");
        ArrayList<File> folders = new ArrayList<File>();
        folders.add(new File("D://test/test1"));
        folders.add(new File("D://test/test2"));
        folders.add(new File("D://test/test3"));
        List<File> folderFromPath = FileSystemUtils.getFolders(new File(TEST_FILE_PATH));
        assertArrayEquals(folderFromPath.toArray(), folders.toArray());
    }

    @Test
    public void testSaveFileTree() {

    }

    @Test
    public void testDeleteFiles() {
        createTestData(TEST_FILE_PATH, TEST_FILE_NAME, TEST_DATA);
        createTestData(TEST_FILE_PATH, ANOTHER_TEST_FILE_NAME, DIFF_TEST_DATA);
        FileSystemUtils.deleteFiles(TEST_FILE_PATH);
        try {
            assertTrue(FileSystemUtils.isDirEmpty(Paths.get(TEST_FILE_PATH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCopyFiles() {
        createTestData(TEST_FILE_PATH, TEST_FILE_NAME, TEST_DATA);
        FileSystemUtils.createFolder(ANOTHER_TEST_FILE_PATH);
        FileSystemUtils.copyFiles(new File(TEST_FILE_PATH + SYSTEM_LINE_SEPARATOR + TEST_FILE_NAME), ANOTHER_TEST_FILE_PATH);
        List<File> filesFromTest = FileSystemUtils.getFilesFromFolder(new File(TEST_FILE_PATH));
        List<File> filesFromTest2 = FileSystemUtils.getFilesFromFolder(new File(ANOTHER_TEST_FILE_PATH));
        assertTrue(FileSystemUtils.compareTwoFiles(filesFromTest2.get(0), filesFromTest.get(0)));
//        for (int i = 0; i < filesFromTest.size(); i++) {
//            Assert.assertTrue(FileSystemUtils.compareTwoFiles(filesFromTest.get(i), filesFromTest2.get(i)));
//        }
    }
}
