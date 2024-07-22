package com.dsimpletech.aafdata.EntityDataMicroservice.database;

import com.dsimpletech.aafdata.EntityDataMicroservice.controller.BusinessEntityController;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    private static Environment environment;

    //NOTE: Spring Boot Logback default logging implemented per https://www.baeldung.com/spring-boot-logging
    private static Logger logger = LoggerFactory.getLogger(BusinessEntityController.class);

    private static final String DB_DRIVER_CLASS = "org.postgresql.Driver";
    private static final String DB_URL = environment.getProperty("url");
    private static final String DB_USERNAME = environment.getProperty("username");
    private static final String DB_PASSWORD = environment.getProperty("password");

    public static Connection GetDatabaseConnection() {

        Connection connection = null;

        try
        {
            logger.info("Attempting to GetDatabaseConnection() for " + DB_DRIVER_CLASS + " at " + DB_URL + " with username " + DB_USERNAME);

            //NOTE: Load the driver class
            Class.forName(DB_DRIVER_CLASS);
            connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            logger.error("GetDatabaseConnection() failed due to: " + e);
        } catch (SQLException e) {
            logger.error("GetDatabaseConnection() failed due to: " + e);
        }
        finally
        {
            try
            {
                if (connection != null) {
                    connection.close();
                }
            }
            catch (SQLException e)
            {
                logger.error("Failed to close connection resource in GetDatabaseConnection() due to: " + e);
            }
        }

        logger.info("GetDatabaseConnection() succeeded for " + DB_DRIVER_CLASS + " at " + DB_URL + " with username " + DB_USERNAME);
        return connection;
    }
}
