package com.database.atypon.Node.utils.directory;

import java.io.File;

public class DirectoryManager {

    private final File directory;

    public DirectoryManager(File directory) {
        this.directory = directory;
    }

    public void createDirectory() {
        if(directory.exists())
            throw new RuntimeException("Directory already exists");

        directory.mkdir();
    }
}
