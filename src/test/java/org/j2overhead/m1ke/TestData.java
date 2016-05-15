package org.j2overhead.m1ke;

import org.j2overhead.m1ke.command.Commands;

import java.io.File;

public class TestData {
   public static String TEST_FILE_PATH = "D:" + File.separator +"test";
   public static String TEST_FILE_NAME = "test.txt";
   public static String INIT = Commands.INIT.toString();
   public static String INTEGRATE = Commands.INTEGRATE.toString();
   public static String CREATE_BRANCH = Commands.CREATE_BRANCH.toString();
   public static String SAVE = Commands.SAVE.toString();
   public static String REMOVE_BRANCH = Commands.REMOVE_BRANCH.toString();
   public static String GET_BRANCH = Commands.GET_BRANCH.toString();
   public static String QUIT = Commands.QUIT.toString();

   public static void main(String[] args) {
   }
}
