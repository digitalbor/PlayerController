package com.pavel.qa.base;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.testng.annotations.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BaseTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public void setup() {
        // Base URI can be configured via system property or config file
        String baseUrl = System.getProperty("baseUrl", "http://3.68.165.45");
        RestAssured.baseURI = baseUrl;
        RestAssured.defaultParser = Parser.JSON;
        logger.info("Base URI set to: {}", baseUrl);
    }


}

