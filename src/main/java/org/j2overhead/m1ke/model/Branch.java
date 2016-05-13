package org.j2overhead.m1ke.model;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Branch {
    private String commit;
    private LocalDateTime lastUpdate;
    private List<File> files;
    private List<File> folders;

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<File> getFolders() {
        return folders;
    }

    public void setFolders(List<File> folders) {
        this.folders = folders;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }
    
}
