# PlayerController API Tests

## Description

This project contains automated API tests for the PlayerController service. It verifies core functionalities such as player creation, update, deletion, and retrieval. The tests are written in Java using TestNG and RestAssured, and include both positive and negative scenarios examples.

## How to Run

You can run the tests using Maven from the command line. The base URL can be configured either directly or via environment name.

### Run with default command (default test suite and env will be used):

mvn clean test

### Run with environment name:

mvn clean test -Denv=qa

### Run with custom base URL:

mvn clean test -DbaseUrl=http://localhost:8080

### Run with both:

mvn clean test -Denv=stage -DbaseUrl=https://custom.api.com

## Project Structure

com.pavel.qa.tests — Test classes (positive and negative scenarios)

com.pavel.qa.models — Request and response models

com.pavel.qa.utils — API wrappers and utilities

com.pavel.qa.config — Environment configuration

com.pavel.qa.base.BaseTest — Base test setup

testng.xml — Manage thread-count, tests and test suites

## Dependencies

Java 11+
Maven
TestNG
RestAssured
Allure

## Allure Report

### To generate and view the Allure report after running tests:

allure generate target/allure-results --clean -o target/allure-report

## Author

Pavel B