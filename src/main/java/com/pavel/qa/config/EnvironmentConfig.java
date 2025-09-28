package com.pavel.qa.config;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentConfig {
    private static final Map<String, String> ENV_URLS = new HashMap<>();

    static {
        ENV_URLS.put("qa", "http://3.68.165.45");
        ENV_URLS.put("stage", "https://stage.api.example.com");
        ENV_URLS.put("prod", "https://api.example.com");
    }

    public static String getBaseUrl(String env) {
        return ENV_URLS.getOrDefault(env, "http://localhost");
    }
}

