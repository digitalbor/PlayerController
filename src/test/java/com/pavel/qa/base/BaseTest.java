package com.pavel.qa.base;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import com.pavel.qa.config.EnvironmentConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;

public class BaseTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Parameters({"env"})
    @BeforeClass
    public void setup(String env) {
        // Base URI can be configured via system property or config file
        String baseUrl = System.getProperty("baseUrl", EnvironmentConfig.getBaseUrl(env));
        RestAssured.baseURI = baseUrl;
        RestAssured.defaultParser = Parser.JSON;
        logger.info("Base URI set to: {}", baseUrl);


        try (FileWriter writer = new FileWriter("target/allure-results/environment.properties")) {
            writer.write("Environment=" + env + "\n");
            writer.write("BaseURL=" + baseUrl + "\n");
        } catch (IOException e) {
            logger.error("Failed to write environment.properties", e);
        }

    }


}

