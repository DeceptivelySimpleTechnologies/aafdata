package com.dsimpletech.aafdata.EntityDataMicroservice.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {

    private static final String DB_DRIVER_CLASS = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/AafCore";
    private static final String DB_USERNAME = "AafCoreClient";
    private static final String DB_PASSWORD = "R3adWr1t3Cl13nt!";

    public static Connection getConnection() {

        Connection connection = null;

        try
        {
            //NOTE: Load the driver class
            Class.forName(DB_DRIVER_CLASS);
            connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();    //TODO: Log this
        } catch (SQLException e) {
            e.printStackTrace();    //TODO: Log this
        }

        return connection;
    }
}
