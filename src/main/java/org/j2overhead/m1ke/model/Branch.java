package org.j2overhead.m1ke.model;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Branch {
    private LocalDateTime lastUpdate;
    private ArrayList<File> files;
    private ArrayList<File> folders;

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }

    public ArrayList<File> getFolders() {
        return folders;
    }

    public void setFolders(ArrayList<File> folders) {
        this.folders = folders;
    }
}
