package com.database.atypon.Node.utils.file_operations.fileDeleter;

import java.io.File;
import java.io.FileNotFoundException;

public class FileDeleter {

    private final String path;

    public FileDeleter(String path) {
        this.path = path;
    }

    public void delete() throws FileNotFoundException {
        File file = new File(path);
        if(!file.exists())
            throw new FileNotFoundException("File not found");
        file.delete();
    }

}
