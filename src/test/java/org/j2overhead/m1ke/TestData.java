package org.j2overhead.m1ke;

import org.j2overhead.m1ke.command.Commands;
import org.j2overhead.m1ke.utils.FileSystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.j2overhead.m1ke.utils.FileSystemUtils.SYSTEM_LINE_SEPARATOR;

public class TestData {
   private static final Logger LOG = LoggerFactory.getLogger(TestData.class.getClass());

   public static String TEST_FILE_PATH = "D:" + SYSTEM_LINE_SEPARATOR +"test";
   public static String ANOTHER_TEST_FILE_PATH = "D:" + SYSTEM_LINE_SEPARATOR + "test2";
   public static String TEST_FILE_NAME = "test.txt";
   public static String ANOTHER_TEST_FILE_NAME = "test2.txt";
   public static String ABSOLUTE_TEST_FILE_PATH_AND_NAME = TEST_FILE_PATH + File.separator + TEST_FILE_NAME;
   public static String TEST_DATA = "abra cadabra \n abra cadabra \n + \n abra cadabra abra cadabra abra cadabra \n \n";
   public static String DIFF_TEST_DATA = "blabla bla different data";
   public static String INIT = Commands.INIT.toString();
   public static String INTEGRATE = Commands.INTEGRATE.toString();
   public static String CREATE_BRANCH = Commands.CREATE_BRANCH.toString();
   public static String SAVE = Commands.SAVE.toString();
   public static String REMOVE_BRANCH = Commands.REMOVE_BRANCH.toString();
   public static String GET_BRANCH = Commands.GET_BRANCH.toString();
   public static String QUIT = Commands.QUIT.toString();

   public static void createTestData(String folder, String fileName, String data) {
      if (Files.exists(Paths.get(folder))) {
         LOG.info(folder + " is exists");
      } else {
         FileSystemUtils.createFolder(folder);
      }
      if (Files.exists(Paths.get(folder + SYSTEM_LINE_SEPARATOR + fileName))) {
         LOG.info(folder + SYSTEM_LINE_SEPARATOR + fileName + "is exists");
      } else {
         FileSystemUtils.createFile(folder + SYSTEM_LINE_SEPARATOR + fileName);
      }
      FileSystemUtils.writeStringDataToFile(folder + SYSTEM_LINE_SEPARATOR + fileName, data);
   }

   public static File getTestFileWithTestData() {
      createTestData(TEST_FILE_PATH, TEST_FILE_NAME, TEST_DATA);
      return new File(TEST_FILE_PATH + SYSTEM_LINE_SEPARATOR + TEST_FILE_NAME);
   }

   public static void main(String[] args) {
      System.out.println(ABSOLUTE_TEST_FILE_PATH_AND_NAME);
   }
}
