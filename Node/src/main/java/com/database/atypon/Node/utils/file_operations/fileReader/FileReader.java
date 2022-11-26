package com.database.atypon.Node.utils.file_operations.fileReader;

import java.io.BufferedReader;
import java.io.File;

public class FileReader {

    private final File file;
    private String content;

    public FileReader(File file) {
        this.file = file;
        this.content = "";
    }

    public String getContent() {
        return content;
    }

    public void read(){
        if(!file.exists())
            throw new RuntimeException("File doesn't exist");
        try(BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(file))){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content += line;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
