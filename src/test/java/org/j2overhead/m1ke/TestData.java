package org.j2overhead.m1ke;

import org.j2overhead.m1ke.command.Commands;
import org.j2overhead.m1ke.utils.FileSystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestData {
   private static final Logger LOG = LoggerFactory.getLogger(TestData.class.getClass());

   public static String SYSTEM_LINE_SEPARATOR = File.separator;
   public static String TEST_FILE_PATH = "D:" + SYSTEM_LINE_SEPARATOR +"test";
   public static String TEST_FILE_NAME = "test.txt";
   public static String ABSOLUTE_TEST_FILE_PATH_AND_NAME = TEST_FILE_PATH + File.separator + TEST_FILE_NAME;
   public static String TEST_DATA = "abra cadabra \n abra cadabra \n + \n abra cadabra abra cadabra abra cadabra \n \n";
   public static String INIT = Commands.INIT.toString();
   public static String INTEGRATE = Commands.INTEGRATE.toString();
   public static String CREATE_BRANCH = Commands.CREATE_BRANCH.toString();
   public static String SAVE = Commands.SAVE.toString();
   public static String REMOVE_BRANCH = Commands.REMOVE_BRANCH.toString();
   public static String GET_BRANCH = Commands.GET_BRANCH.toString();
   public static String QUIT = Commands.QUIT.toString();

   public static void createTestData(String folder, String fileName, String data) {
      if (Files.exists(Paths.get(folder))) {
         LOG.info(folder + "is exists");
      } else {
         FileSystemUtils.createFolder(folder);
      }
      FileSystemUtils.createFile(folder + SYSTEM_LINE_SEPARATOR + fileName);
      FileSystemUtils.writeStringToFile(folder + SYSTEM_LINE_SEPARATOR + fileName, data);
   }

   public static File getTestFile() {
      createTestData(TEST_FILE_PATH, TEST_FILE_NAME, TEST_DATA);
      return new File(ABSOLUTE_TEST_FILE_PATH_AND_NAME);
   }

   public static void main(String[] args) {
      System.out.println(ABSOLUTE_TEST_FILE_PATH_AND_NAME);
   }
}
