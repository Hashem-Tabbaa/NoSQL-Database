package com.database.atypon.Node.utils.file_operations.fileUpdater;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUpdater {

    private final File oldFile, newFile;
    private final String content;

    public FileUpdater(String oldFilePath, String oldFileName, String content, String type) throws IOException {
        this.oldFile = new File(oldFilePath + oldFileName + type);
        this.newFile = new File(oldFilePath + oldFileName + "temp" + type);
        newFile.createNewFile();
        this.content = content;
    }

    public void updateFile() throws Exception {
        if(!oldFile.exists())
            throw new FileNotFoundException("File not found");

        try(BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(newFile))){
            bufferedWriter.write(content);
            bufferedWriter.flush();
            if(!oldFile.delete())
                throw new Exception("File not deleted");
            if(!newFile.renameTo(oldFile))
                throw new Exception("File not renamed");
        }catch (Exception e) {
            newFile.delete();
            throw new RuntimeException(e);
        }
    }
}
