package com.database.atypon.Node.services.read;

import com.database.atypon.Node.utils.PathBuilder;
import com.database.atypon.Node.utils.cache.Cache;
import com.database.atypon.Node.utils.file_operations.fileReader.FileReader;
import com.database.atypon.Node.utils.response.Response;
import com.database.atypon.Node.utils.response.ResponseType;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Vector;

@Service
public class ReadService {

    private final Cache cache;
    private List<Thread> threads;
    private final int NUMBER_OF_THREADS = 4;

    public ReadService(Cache cache) {
        this.cache = cache;
        threads = new Vector<>();
    }

    public Response fetchById(String id, String databaseName, String schemaName) {
        try{
            String documentPath = PathBuilder.getPathToDocument(databaseName, schemaName, id);
            FileReader fileReader = new FileReader
                    (new File(documentPath));
            fileReader.read();
            return new Response(ResponseType.SUCCESS, "Document fetched successfully",
                    fileReader.getContent());
        }catch (Exception e){
            return new Response(ResponseType.ERROR, "Document not found");
        }
    }

    public Response fetchAll(String databaseName, String schemaName) {
        try {
            String documentsPath = PathBuilder.getPathToAllDocuments(databaseName, schemaName);
            return readAllDocuments(documentsPath);
        }catch (Exception e){
            return null;
        }

    }

    private Response readAllDocuments(String documentsPath) {
        // Check if the threads are busy
        if(isThreadsBusy())
            return new Response(ResponseType.ERROR, "Threads are busy");
//        try{
            JSONObject jsonObject = new JSONObject();
            File folder = new File(documentsPath);
            File[] listOfFiles = folder.listFiles();
            for(File file : listOfFiles){
                System.out.println(file.getName());
            }
            System.out.println("Number of files: " + listOfFiles.length);
            initializeReadingThreads(listOfFiles, jsonObject);
            startReadingThreads();
            try{
                joinReadingThreads();
            }catch (Exception e){
                e.printStackTrace();
            }

            return new Response(ResponseType.SUCCESS, "Documents fetched successfully",
                    jsonObject.toString());
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            return new Response(ResponseType.ERROR, "Error while fetching documents", e.getMessage());
//        }
    }

    private boolean isThreadsBusy() {
       return threads != null && threads.stream().anyMatch(Thread::isAlive);
    }
    private void startReadingThreads() {
        for(Thread thread : threads){
            thread.start();
        }
    }
    private void joinReadingThreads() throws InterruptedException {
        for(Thread thread : threads){
            thread.join();
        }
    }
    private Thread createThread(File[] listOfFiles, int start, int end, JSONObject jsonObject){
        return new Thread(() -> {
            for (int i = start; i < end; i++) {
                if (listOfFiles[i].isFile()) {
                    FileReader fileReader = new FileReader(listOfFiles[i]);
                    fileReader.read();
                    System.out.println("File " + listOfFiles[i].getName() + " From Thread: " +
                            Thread.currentThread().getName());
                    synchronized (jsonObject){
                        jsonObject.put(listOfFiles[i].getName(), fileReader.getContent());
                    }
                }
            }
        });
    }
    private void initializeReadingThreads(File[] listOfFiles, JSONObject jsonObject){
        threads.clear();
        threads.add(createThread(listOfFiles, 0, listOfFiles.length/4, jsonObject));
        threads.add(createThread(listOfFiles, listOfFiles.length/4, listOfFiles.length/2, jsonObject));
        threads.add(createThread(listOfFiles, listOfFiles.length/2, 3*listOfFiles.length/4, jsonObject));
        threads.add(createThread(listOfFiles, 3*listOfFiles.length/4, listOfFiles.length, jsonObject));
    }
}
