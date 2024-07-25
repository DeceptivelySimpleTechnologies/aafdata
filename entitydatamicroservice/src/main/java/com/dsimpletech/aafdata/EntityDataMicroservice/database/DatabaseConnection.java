package com.dsimpletech.aafdata.EntityDataMicroservice.database;

//import com.dsimpletech.aafdata.EntityDataMicroservice.controller.BusinessEntityController;
//import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
//import io.r2dbc.postgresql.api.PostgresqlConnection;
//import io.r2dbc.spi.ConnectionFactories;
//import io.r2dbc.postgresql.PostgresqlConnectionFactory;
//import io.r2dbc.spi.Connection;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
//import reactor.core.publisher.Mono;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;


public class DatabaseConnection {
    //NOTE: Spring Boot Logback default logging implemented per https://www.baeldung.com/spring-boot-logging
    Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

//    Map<String, String> options = null;
//    PostgresqlConnectionFactory connectionFactory = null;
//    Mono<PostgresqlConnection> connection = null;

//    String DB_DRIVER_CLASS = "";
//    String DB_URL = "";
//    String DB_USERNAME = "";
//    String DB_PASSWORD = "";
//
    //NOTE: Implement this
    @PostConstruct
    private void GetEntityData() {
    }

    //NOTE: Implement this
    @PreDestroy
    private void DeleteEntityData() {
    }

//    public Mono<PostgresqlConnection> GetDatabaseConnection(String dbDriverClass, String dbUrl, String dbUsername, String dbPassword) {
//
//        try
//        {
//            logger.info("Attempting to GetDatabaseConnection() for " + dbDriverClass + " at " + dbUrl + " with username " + dbUsername);
//
//            options = new HashMap<>();
//            options.put("lock_timeout", "10s");
//
//            connectionFactory = new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
//                    .host("localhost")
//                    .port(5432)  // optional, defaults to 5432
//                    .username(dbUsername)
//                    .password(dbPassword)
//                    .database("AafCore")  // optional
//                    .options(options) // optional
//                    .build());
//
//            connection = connectionFactory.create();
//            connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
//
//            if (connection == null) {
//                throw new Exception("Failed to get connection for " + dbDriverClass + " at " + dbUrl + " with username " + dbUsername);
//            }
//        } catch (Exception e) {
//            logger.error("GetDatabaseConnection() failed due to: " + e);
//        }
//        finally
//        {
//            try
//            {
//                if (connection != null) {
//                    connection.
//                }
//            }
//            catch (SQLException e)
//            {
//                logger.error("Failed to close connection resource in GetDatabaseConnection() due to: " + e);
//            }
//        }
//
//        logger.info("GetDatabaseConnection() succeeded for " + dbDriverClass + " at " + dbUrl + " with username " + dbUsername);
//        return connection;
//    }
//
    public Connection GetDatabaseConnection(String dbDriverClass, String dbUrl, String dbUsername, String dbPassword) {

        Connection connection = null;

        try
        {
            logger.info("Attempting to GetDatabaseConnection() for " + dbDriverClass + " at " + dbUrl + " with username " + dbUsername);

            //NOTE: Load the driver class
//            Class.forName(dbDriverClass);
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
//        } catch (ClassNotFoundException e) {
//            logger.error("GetDatabaseConnection() failed due to: " + e);
        } catch (SQLException e) {
            logger.error("GetDatabaseConnection() failed due to: " + e);
        }
//        finally
//        {
//            try
//            {
//                if (connection != null) {
//                    connection.close();
//                }
//            }
//            catch (SQLException e)
//            {
//                logger.error("Failed to close connection resource in GetDatabaseConnection() due to: " + e);
//            }
//        }

        logger.info("GetDatabaseConnection() succeeded for " + dbDriverClass + " at " + dbUrl + " with username " + dbUsername);
        return connection;
    }
}
