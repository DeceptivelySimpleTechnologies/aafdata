//NOTE: Copyright © 2003-2024 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

package com.dsimpletech.aafdata.SystemDataService.controller;


import com.dsimpletech.aafdata.SystemDataService.database.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import org.springframework.http.HttpCookie;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;

import org.springframework.util.MultiValueMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ServerWebExchange;

import java.lang.Exception;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.Instant;
import java.util.*;


@Tag(name = "Adaptív Application Foundation (AAF) Data Layer (AAF Data)", description = "Locally-Sourced, Artisanal Data™")
@RestController
@RequestMapping(value = "/")
public class BusinessEntityController {
    @Autowired
    private Environment environment;

    private ResultSet resultSet = null;

    private DatabaseConnection databaseConnection = null;

    private String DB_DRIVER_CLASS = "";
    private String DB_URL = "";
    private String DB_USERNAME = "";
    private String DB_PASSWORD = "";

    private Connection connection = null;

    private CallableStatement statement = null;

    private ArrayList<EntityTypeDefinition> entityTypeDefinitions = null;
    private ArrayList<EntityTypeAttribute> entityTypeAttributes = null;
    private ArrayList<EntityTypeDefinitionEntityTypeAttributeAssociation> entityTypeDefinitionEntityTypeAttributeAssociations = null;
    private ArrayList<EntitySubtype> entitySubtypes = null;

    private String ENVIRONMENT_JWT_SHARED_SECRET = "";

    //NOTE: Spring Boot Logback default logging implemented per https://www.baeldung.com/spring-boot-logging
    Logger logger = LoggerFactory.getLogger(BusinessEntityController.class);

    @PostConstruct
    private void CacheEntityData() {

        try {
            logger.info("Attempting to CacheEntityData()");

            DB_DRIVER_CLASS = "postgresql";
            DB_URL = environment.getProperty("spring.jdbc.url");
            DB_USERNAME = environment.getProperty("spring.jdbc.username");
            DB_PASSWORD = environment.getProperty("spring.jdbc.password");

            databaseConnection = new DatabaseConnection();
            connection = databaseConnection.GetDatabaseConnection(DB_DRIVER_CLASS, DB_URL, DB_USERNAME, DB_PASSWORD);
            connection.setAutoCommit(false);

            if (connection == null) {
                throw new Exception("Unable to get database connection");
            }

            //NOTE: Get EntityTypeDefinitions
            statement = connection.prepareCall("{call \"GetEntityTypeDefinitions\"(?)}");
            //NOTE: Register the data OUT parameter before calling the stored procedure
            statement.registerOutParameter(1, Types.OTHER);
            statement.execute();

            //NOTE: Read the OUT parameter now
            resultSet = (ResultSet) statement.getObject(1);

            entityTypeDefinitions = new ArrayList<EntityTypeDefinition>();

            while (resultSet.next()) {
                entityTypeDefinitions.add(new EntityTypeDefinition(resultSet.getInt("Id"), resultSet.getInt("EntitySubtypeId"), resultSet.getString("TextKey"), resultSet.getString("LocalizedName"), resultSet.getInt("Ordinal"), resultSet.getBoolean("IsActive"), resultSet.getTimestamp("DeletedAtDateTimeUtc")));
            }

            logger.info(entityTypeDefinitions.size() + " EntityTypeDefinitions cached locally");

            //NOTE: Get EntityTypeAttributes
            statement = connection.prepareCall("{call \"GetEntityTypeAttributes\"(?)}");
            //NOTE: Register the data OUT parameter before calling the stored procedure
            statement.registerOutParameter(1, Types.OTHER);
            statement.execute();

            //NOTE: Read the OUT parameter now
            resultSet = (ResultSet) statement.getObject(1);

            entityTypeAttributes = new ArrayList<EntityTypeAttribute>();

            while (resultSet.next()) {
                entityTypeAttributes.add(new EntityTypeAttribute(resultSet.getInt("Id"), resultSet.getInt("EntitySubtypeId"), resultSet.getString("TextKey"), resultSet.getString("LocalizedName"), resultSet.getInt("GeneralizedDataTypeEntitySubtypeId"), resultSet.getInt("EntityTypeAttributeValueEntitySubtypeId"), resultSet.getString("DefaultValue"), resultSet.getInt("Ordinal"), resultSet.getBoolean("IsActive"), resultSet.getTimestamp("DeletedAtDateTimeUtc")));
            }

            logger.info(entityTypeAttributes.size() + " EntityTypeAttributes cached locally");

            //NOTE: Get EntityTypeDefinitionEntityTypeAttributeAssociations
            statement = connection.prepareCall("{call \"GetEntityTypeDefinitionEntityTypeAttributeAssociations\"(?)}");
            //NOTE: Register the data OUT parameter before calling the stored procedure
            statement.registerOutParameter(1, Types.OTHER);
            statement.execute();

            //NOTE: Read the OUT parameter now
            resultSet = (ResultSet) statement.getObject(1);

            entityTypeDefinitionEntityTypeAttributeAssociations = new ArrayList<EntityTypeDefinitionEntityTypeAttributeAssociation>();

            while (resultSet.next()) {
                entityTypeDefinitionEntityTypeAttributeAssociations.add(new EntityTypeDefinitionEntityTypeAttributeAssociation(resultSet.getInt("Id"), resultSet.getInt("EntitySubtypeId"), resultSet.getString("TextKey"), resultSet.getInt("EntityTypeDefinitionId"), resultSet.getInt("EntityTypeAttributeId"), resultSet.getInt("Ordinal"), resultSet.getBoolean("IsActive"), resultSet.getTimestamp("DeletedAtDateTimeUtc")));
            }

            logger.info(entityTypeDefinitionEntityTypeAttributeAssociations.size() + " EntityTypeDefinitionEntityTypeAttributeAssociations cached locally");

            //NOTE: Get EntitySubtypes
            statement = connection.prepareCall("{call \"GetEntitySubtypes\"(?)}");
            //NOTE: Register the data OUT parameter before calling the stored procedure
            statement.registerOutParameter(1, Types.OTHER);
            statement.execute();

            //NOTE: Read the OUT parameter now
            resultSet = (ResultSet) statement.getObject(1);

            entitySubtypes = new ArrayList<EntitySubtype>();

            while (resultSet.next()) {
                entitySubtypes.add(new EntitySubtype(resultSet.getInt("Id"), resultSet.getString("TextKey"), resultSet.getString("LocalizedName"), resultSet.getInt("Ordinal"), resultSet.getBoolean("IsActive"), resultSet.getTimestamp("DeletedAtDateTimeUtc")));
            }

            logger.info(entitySubtypes.size() + " EntitySubtypes cached locally");
        }
        catch (Exception e) {
            logger.error("CacheEntityData() failed due to: " + e);
        }
        finally {
            try
            {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (statement != null) {
                    statement.close();
                    statement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            }
            catch (SQLException e)
            {
                logger.error("Failed to close resultset and/or statement resource in CacheEntityData() due to: " + e);
            }
        }

        logger.info("CacheEntityData() succeeded");
    }

    @PreDestroy
    private void UncacheEntityData() {
        logger.info("Attempting to UncacheEntityData()");

        entityTypeDefinitions = null;
        entityTypeAttributes = null;
        entityTypeDefinitionEntityTypeAttributeAssociations = null;
        entitySubtypes = null;

        logger.info("UncacheEntityData() succeeded");
    }

    //NOTE: Suppress browser-based favicon.ico requests
//    @Controller
//    static class FaviconController {
//
//        @GetMapping("favicon.ico")
//        @ResponseBody
//        void returnNoFavicon() {
//        }
//    }
//

}