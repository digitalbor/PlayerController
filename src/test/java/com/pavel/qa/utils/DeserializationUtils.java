package com.pavel.qa.utils;

import io.restassured.response.Response;
import org.testng.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeserializationUtils {
    private static final Logger logger = LoggerFactory.getLogger(DeserializationUtils.class);

    public static <T> T safeDeserialize(Response response, Class<T> clazz) {
        try {
            return response.as(clazz);
        } catch (Exception e) {
            logger.error("Failed to deserialize response to " + clazz.getSimpleName(), e);
            Assert.fail("Deserialization failed: " + e.getMessage());
            return null; // Unreachable, but required for compilation
        }
    }
}
