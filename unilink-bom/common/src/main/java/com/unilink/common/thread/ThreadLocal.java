package com.unilink.common.thread;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocal {
    private static java.lang.ThreadLocal<Map<String, Object>> threadLocal = java.lang.ThreadLocal.withInitial(HashMap::new);

    private static final String TOKEN = "token";
    private static final String USER_ID = "";


    private static HashMap<String, Object> get() {
        return (HashMap<String, Object>) threadLocal.get();
    }

    private static Object getValue(String key) {
        return get().get(key);
    }

    private static void setValue(String key, Object obj) {
        get().put(key, obj);
    }

    public static void clear() {
        get().clear();
    }

    public static String getUserId() {
        return (String) getValue(USER_ID);
    }

    public static void setUserId(String userId) {
        setValue(USER_ID, userId);
    }
}
