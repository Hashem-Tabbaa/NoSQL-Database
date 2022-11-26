package com.database.atypon.Node.utils.cache;

import org.json.JSONObject;

public interface CacheInterface {

    void add(String key, JSONObject value);

    JSONObject get(String key);

    void remove(String key);

    void clear();
}
