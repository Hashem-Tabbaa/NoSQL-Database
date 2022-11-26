package com.database.atypon.Node.utils.cache;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class Cache implements CacheInterface{

    private final ConcurrentHashMap<String, DoubleLinkedList.Node> map;
    private final ConcurrentHashMap<String, DoubleLinkedList> cacheQueue;
    private final int MAXIMUM_SIZE = 50;
    private int size = 0;
    private static volatile Cache instance;

    private Cache(){
        map = new ConcurrentHashMap<>();
        cacheQueue = new ConcurrentHashMap<>();
    }

    public static Cache getInstance(){
        if(instance == null)
            synchronized (Cache.class){
                if(instance == null)
                    instance = new Cache();
            }
        return instance;
    }

    @Override
    public synchronized void add(String key, JSONObject value) {
        if(key == null || value == null)
            throw new NullPointerException("Key or value cannot be null");
        return;
    }

    @Override
    public JSONObject get(String key) {
        return null;
    }

    @Override
    public void remove(String key) {

    }

    @Override
    public void clear() {

    }
}
