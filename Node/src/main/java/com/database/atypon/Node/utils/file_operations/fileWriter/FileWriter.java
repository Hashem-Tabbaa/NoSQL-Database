package com.database.atypon.Node.utils.file_operations.fileWriter;

import com.database.atypon.Node.utils.response.Response;
import com.database.atypon.Node.utils.response.ResponseType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class FileWriter {

    private final File file;
    private final String content;

    public FileWriter(File file, String content) {
        this.file = file;
        this.content = content;
    }

    public synchronized Response write() throws Exception{
        if(!file.exists())
            throw new FileNotFoundException("File not found");
        if(content == null)
            throw new Exception("Content cannot be null");
        try(BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(file))){
            bufferedWriter.write(content);
            bufferedWriter.flush();
            return new Response(ResponseType.SUCCESS, "File written successfully");
        }catch (Exception e) {
            return new Response(ResponseType.ERROR, e.getMessage());
        }

    }
}
