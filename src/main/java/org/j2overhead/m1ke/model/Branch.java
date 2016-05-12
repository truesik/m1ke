package org.j2overhead.m1ke.model;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Branch {
    LocalDateTime lastUpdate;
    ArrayList<File> files;
    ArrayList<File> folders;
}
