package com.dsimpletech.aafdata.EntityDataMicroservice.database;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    //NOTE: Spring Boot Logback default logging implemented per https://www.baeldung.com/spring-boot-logging
    Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    //NOTE: Implement this
    @PostConstruct
    private void GetEntityData() {
    }

    //NOTE: Implement this
    @PreDestroy
    private void DeleteEntityData() {
    }

    public Connection GetDatabaseConnection(String dbDriverClass, String dbUrl, String dbUsername, String dbPassword) {

        Connection connection = null;

        try
        {
            logger.info("Attempting to GetDatabaseConnection() for " + dbDriverClass + " at " + dbUrl + " with username " + dbUsername);
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            logger.error("GetDatabaseConnection() failed due to: " + e);
        }

        logger.info("GetDatabaseConnection() succeeded for " + dbDriverClass + " at " + dbUrl + " with username " + dbUsername);
        return connection;
    }
}
