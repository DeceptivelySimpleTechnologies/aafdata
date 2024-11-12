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

    private CallableStatement callableStatement = null;
    private PreparedStatement preparedStatement = null;

    private ArrayList<EntityTypeDefinition> entityTypeDefinitions = null;
    private ArrayList<EntityTypeAttribute> entityTypeAttributes = null;
    private ArrayList<EntityTypeDefinitionEntityTypeAttributeAssociation> entityTypeDefinitionEntityTypeAttributeAssociations = null;
    private ArrayList<EntitySubtype> entitySubtypes = null;

    private String ENVIRONMENT_JWT_SHARED_SECRET = "";

    //NOTE: Spring Boot Logback default logging implemented per https://www.baeldung.com/spring-boot-logging
    Logger logger = LoggerFactory.getLogger(BusinessEntityController.class);

    @PostConstruct
    private void CacheEntityData()
    {

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
            callableStatement = connection.prepareCall("{call \"GetEntityTypeDefinitions\"(?)}");
            //NOTE: Register the data OUT parameter before calling the stored procedure
            callableStatement.registerOutParameter(1, Types.OTHER);
            callableStatement.execute();

            //NOTE: Read the OUT parameter now
            resultSet = (ResultSet) callableStatement.getObject(1);

            entityTypeDefinitions = new ArrayList<EntityTypeDefinition>();

            while (resultSet.next()) {
                entityTypeDefinitions.add(new EntityTypeDefinition(resultSet.getInt("Id"), resultSet.getInt("EntitySubtypeId"), resultSet.getString("TextKey"), resultSet.getString("LocalizedName"), resultSet.getString("LocalizedDescription"), resultSet.getString("LocalizedAbbreviation"), resultSet.getTimestamp("PublishedAtDateTimeUtc"), resultSet.getInt("PublishedByInformationSystemUserId"), resultSet.getInt("Ordinal"), resultSet.getBoolean("IsActive"), resultSet.getTimestamp("CreatedAtDateTimeUtc"), resultSet.getInt("CreatedByInformationSystemUserId"), resultSet.getTimestamp("UpdatedAtDateTimeUtc"), resultSet.getInt("UpdatedByInformationSystemUserId"), resultSet.getTimestamp("DeletedAtDateTimeUtc"), resultSet.getInt("DeletedByInformationSystemUserId")));
            }

            logger.info(entityTypeDefinitions.size() + " EntityTypeDefinitions cached locally");

            //NOTE: Get EntityTypeAttributes
            callableStatement = connection.prepareCall("{call \"GetEntityTypeAttributes\"(?)}");
            //NOTE: Register the data OUT parameter before calling the stored procedure
            callableStatement.registerOutParameter(1, Types.OTHER);
            callableStatement.execute();

            //NOTE: Read the OUT parameter now
            resultSet = (ResultSet) callableStatement.getObject(1);

            entityTypeAttributes = new ArrayList<EntityTypeAttribute>();

            while (resultSet.next()) {
                entityTypeAttributes.add(new EntityTypeAttribute(resultSet.getInt("Id"), resultSet.getInt("EntitySubtypeId"), resultSet.getString("TextKey"), resultSet.getString("LocalizedName"), resultSet.getInt("GeneralizedDataTypeEntitySubtypeId"), resultSet.getInt("EntityTypeAttributeValueEntitySubtypeId"), resultSet.getString("DefaultValue"), resultSet.getInt("Ordinal"), resultSet.getBoolean("IsActive"), resultSet.getTimestamp("CreatedAtDateTimeUtc"), resultSet.getInt("CreatedByInformationSystemUserId"), resultSet.getTimestamp("UpdatedAtDateTimeUtc"), resultSet.getInt("UpdatedByInformationSystemUserId"), resultSet.getTimestamp("DeletedAtDateTimeUtc"), resultSet.getInt("DeletedByInformationSystemUserId")));
            }

            logger.info(entityTypeAttributes.size() + " EntityTypeAttributes cached locally");

            //NOTE: Get EntityTypeDefinitionEntityTypeAttributeAssociations
            callableStatement = connection.prepareCall("{call \"GetEntityTypeDefinitionEntityTypeAttributeAssociations\"(?)}");
            //NOTE: Register the data OUT parameter before calling the stored procedure
            callableStatement.registerOutParameter(1, Types.OTHER);
            callableStatement.execute();

            //NOTE: Read the OUT parameter now
            resultSet = (ResultSet) callableStatement.getObject(1);

            entityTypeDefinitionEntityTypeAttributeAssociations = new ArrayList<EntityTypeDefinitionEntityTypeAttributeAssociation>();

            while (resultSet.next()) {
                entityTypeDefinitionEntityTypeAttributeAssociations.add(new EntityTypeDefinitionEntityTypeAttributeAssociation(resultSet.getInt("Id"), resultSet.getInt("EntitySubtypeId"), resultSet.getString("TextKey"), resultSet.getInt("EntityTypeDefinitionId"), resultSet.getInt("EntityTypeAttributeId"), resultSet.getTimestamp("PublishedAtDateTimeUtc"), resultSet.getInt("PublishedByInformationSystemUserId"), resultSet.getInt("Ordinal"), resultSet.getBoolean("IsActive"), resultSet.getTimestamp("CreatedAtDateTimeUtc"), resultSet.getInt("CreatedByInformationSystemUserId"), resultSet.getTimestamp("UpdatedAtDateTimeUtc"), resultSet.getInt("UpdatedByInformationSystemUserId"), resultSet.getTimestamp("DeletedAtDateTimeUtc"), resultSet.getInt("DeletedByInformationSystemUserId")));
            }

            logger.info(entityTypeDefinitionEntityTypeAttributeAssociations.size() + " EntityTypeDefinitionEntityTypeAttributeAssociations cached locally");

            //NOTE: Get EntitySubtypes
            callableStatement = connection.prepareCall("{call \"GetEntitySubtypes\"(?)}");
            //NOTE: Register the data OUT parameter before calling the stored procedure
            callableStatement.registerOutParameter(1, Types.OTHER);
            callableStatement.execute();

            //NOTE: Read the OUT parameter now
            resultSet = (ResultSet) callableStatement.getObject(1);

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

                if (callableStatement != null) {
                    callableStatement.close();
                    callableStatement = null;
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
    private void UncacheEntityData()
    {
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

    @Operation(summary = "Create or alter the internal structure of the AafCore database, including its schemas, entity models (tables), functions, and system/lookup data, e.g. EntityTypeDefinition, EntityTypeAttribute, EntityType, EntitySubtype, etc, that has been defined/scripted by the AafCoreModeler role.", description = "Create or alter the internal structure of the database by providing the valid, required data and any valid, optional data as a JSON Web Token (JWT, please see https://jwt.io/) in the HTTP request body")
    @Parameter(in = ParameterIn.COOKIE, description = "JWT Authentication token", name = "Authentication", content = @Content(schema = @Schema(type = "string")))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping(value = "/databases/{databaseName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> PublishBusinessEntity(@PathVariable("databaseName") String databaseName, @RequestBody String requestBody, ServerWebExchange exchange) throws Exception
    {
        ServerHttpRequest request = null;

        int entityTypeId = -1;
        ArrayList<Integer> entityTypeAssociations = null;

        String[] sqlBlacklistValues = null;
        String errorValues = "";

        String apiKey = "";

        ObjectMapper objectMapper = null;

        HttpCookie authenticationJwt = null;
        Base64.Decoder decoderBase64 = Base64.getUrlDecoder();
        String[] authenticationJwtSections = null;
        JsonNode authenticationJwtHeader = null;
        JsonNode authenticationJwtPayload = null;
        int userId = -1;

        String[] bodyJwtSections = null;
        JsonNode bodyJwtHeader = null;
        JsonNode bodyJwtPayload = null;

        String[] entityTypeAttributesNeverToReturn = null;

        String insertClause = "";
        String insertValues = "";

        String selectClause = "";

        String entityData = "";

        //NOTE: Rather than validating the EntityTypeDefinition name, we're going to optimistically pass it through to the database if it returns attributes
        //NOTE: We don't request versions our business entity data structure explicitly in the base URL; instead our explicit (v1.2.3) versioning is maintained internally, based on/derived from the AsOfUtcDateTime query parameter
        //NOTE: Since we're not automatically parsing the resulting JSON into an object, we're returning a JSON String rather than a JSONObject
        try
        {
            logger.info("Attempting to PublishBusinessEntity() for " + databaseName);

            request = exchange.getRequest();

            DB_DRIVER_CLASS = "postgresql";
            DB_URL = environment.getProperty("spring.jdbc.url");
            DB_USERNAME = environment.getProperty("spring.jdbc.username");
            DB_PASSWORD = environment.getProperty("spring.jdbc.password");

            databaseConnection = new DatabaseConnection();
            connection = databaseConnection.GetDatabaseConnection(DB_DRIVER_CLASS, DB_URL, DB_USERNAME, DB_PASSWORD);

            if (connection == null)
            {
                throw new Exception("Unable to get database connection");
            }

            ENVIRONMENT_JWT_SHARED_SECRET = environment.getProperty("environmentJwtSharedSecret");

            apiKey = exchange.getRequest().getHeaders().getFirst("ApiKey");

            if ((apiKey == null) || (apiKey.length() < 1))
            {
                throw new Exception("No 'ApiKey' header included in the request");
            }
            else
            {
                //TODO: Validate API key by looking it up in the database, ensuring that it is not disabled, checking its associated permissions extent, and (later) checking that it is associated with the authenticated user's OrganizationalUnit
                logger.info(("ApiKey header '" + apiKey + "' included in the request"));
            }

            objectMapper = new ObjectMapper();

            //TODO: Validate JWT
            authenticationJwt = request.getCookies().getFirst("Authentication");
            authenticationJwtSections = authenticationJwt.getValue().split("\\.");
            authenticationJwtHeader = objectMapper.readTree(decoderBase64.decode(authenticationJwtSections[0]));
            authenticationJwtPayload = objectMapper.readTree(decoderBase64.decode(authenticationJwtSections[1]));
            //TODO: Validate JWT signature per https://www.baeldung.com/java-jwt-token-decode

            if ((authenticationJwtHeader != null) && (authenticationJwtPayload != null))
            {
                logger.info(("Requested by '" + authenticationJwtPayload.get("body").get("EmailAddress").asText() + "' using key '" + authenticationJwtHeader.get("kid").asText() + "'"));
            }
            else
            {
                throw new Exception("Missing or invalid 'Authentication' cookie included with the request");
            }

            //TODO: Look up InformationSystemUser.Id using the authenticated user's EmailAddress in the Authentication JWT payload, and assign it below
            userId = -100;

            //TODO: Check user's role(s) and permissions for this operation

            //NOTE: Example request http://localhost:8080/Person with "Authentication" JWT and JWT request body
            //NOTE: https://learning.postman.com/docs/sending-requests/response-data/cookies/
//            Authentication JWT: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0ZWQiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiJlZjRhZjRlMy1lNzM2LTQyNWEtYWFmZi1lY2EwM2I3YjliMjgiLCJib2R5Ijp7IkVtYWlsQWRkcmVzcyI6ImFteS5hbmRlcnNvbkBhbXlzYWNjb3VudGluZy5jb20ifX0.Djq5LYPEK1QFgBk9aN5Vei37K6Cb8TxNH3ADWDcUaHs
//            Request JWT: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IlBPU1QgL0VudGl0eVR5cGUiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiI4NzUyZjIzYi0xYTliLTQyMmEtOGIyNi0zNzQyNDM0ZGY0NzYiLCJib2R5Ijp7IkVudGl0eVN1YnR5cGVJZCI6LTEsIlRleHRLZXkiOiJwZXJzb24tbm9uZS1iaWxsLWJha2VyIiwiTGVnYWxHaXZlbk5hbWUiOiJCaWxsIiwiTGVnYWxTdXJuYW1lIjoiQmFrZXIiLCJCb3JuQXREYXRlVGltZVV0YyI6IjIwMDItMDItMDMgMTE6MTI6MTMuMTIzIiwiTGVnYWxDaXRpemVuT2ZDb3VudHJ5R2VvZ3JhcGhpY1VuaXRJZCI6MSwiTG9jYWxlSWQiOjEsIk9yZGluYWwiOi0xLCJJc0FjdGl2ZSI6dHJ1ZX19.rWNowmEoPkF8N0Q5KC5-W83g3hMqIf9TV8KHzLgNbio

            //TODO: Validate JWT
//            bodyJwtSections = requestBody.split("\\.");
//            bodyJwtHeader = objectMapper.readTree(decoderBase64.decode(bodyJwtSections[0]));
//            bodyJwtPayload = objectMapper.readTree(decoderBase64.decode(bodyJwtSections[1]));
            //TODO: Validate JWT signature per https://www.baeldung.com/java-jwt-token-decode
            bodyJwtPayload = objectMapper.readTree(requestBody);

            if (bodyJwtPayload != null)
            {
                if (bodyJwtPayload.has("body"))
                {
                    //NOTE: Contains a JWT request body
                    logger.info(("Request from '" + bodyJwtPayload.get("iss").asText() + "' for '" + bodyJwtPayload.get("aud").asText() + "' using key '" + authenticationJwtHeader.get("kid").asText() + "'"));
                }
                else
                {
                    //NOTE: Create a JWT request body from the simple JSON request body
                    bodyJwtPayload = objectMapper.createObjectNode();
                    ((ObjectNode) bodyJwtPayload).put("iss", "AAFData-TBD");
                    ((ObjectNode) bodyJwtPayload).put("sub", "TBD");
                    ((ObjectNode) bodyJwtPayload).put("aud", "AAFData-TBD");
                    ((ObjectNode) bodyJwtPayload).put("exp", "1723816920");
                    ((ObjectNode) bodyJwtPayload).put("iat", "1723816800");
                    ((ObjectNode) bodyJwtPayload).put("nbf", "1723816789");
                    ((ObjectNode) bodyJwtPayload).put("jti", UUID.randomUUID().toString());
                    ((ObjectNode) bodyJwtPayload).put("body", objectMapper.readTree(requestBody));

                    logger.info(("Request from '" + bodyJwtPayload.get("iss").asText() + "' for '" + bodyJwtPayload.get("aud").asText() + "' using key '" + authenticationJwtHeader.get("kid").asText() + "'"));
                }
            }
            else
            {
                throw new Exception("Missing or invalid request body");
            }

            sqlBlacklistValues = environment.getProperty("sqlNotToAllow").toLowerCase().split(",");
            //TODO: *** Only check request body for SQL injection
            errorValues = GuardAgainstSqlIssues(bodyJwtPayload.toString(), sqlBlacklistValues);

            //TODO: Validate structure
            //NOTE: Remove the Publisher role, i.e. publish becomes a privilege???
            //NOTE: Add columns to EntityTypeDefinition table
            //TODO: Add columns to EntityTypeAttribute table
            //NOTE: Add columns to EntityTypeDefinitionEntityTypeAttributeAssociation table
            //TODO: Add columns to EntitySubtype table???
            //TODO: How to handle modeling and publishing scripted data???

            //TODO: Get unpublished EntityTypeDefinitions
            //TODO: Get unpublished EntityTypeAttributes
            //TODO: Get unpublished EntityTypeDefinitionEntityTypeAttributeAssociations
            //TODO: Get unpublished EntitySubtypes
            //TODO: Generate proposed change list for approval

            //NOTE: Create new entity schema
            //TODO: Create new entity table
            //TODO: Create new entity scripted data
            //TODO: Update as published
            //TODO: Re-cache data

            //TODO: Remove publisher role from EDM and SDS README files

            if (errorValues.length() == 0)
            {
                //preparedStatement = connection.prepareStatement("insert into Emp values(?,?)");
                //stmt.setInt(1,101);//1 specifies the first parameter in the query
                //stmt.setString(2,"Ratan");

                preparedStatement = connection.prepareStatement("CREATE SCHEMA \"LegalEntity\"\n" +
                        "    AUTHORIZATION \"AafCoreModeler\";\n" +
                        "\n" +
//                        "GRANT USAGE ON SCHEMA \"LegalEntity\" TO \"AafCoreModeler\";\n" +
                        "GRANT USAGE ON SCHEMA \"LegalEntity\" TO \"AafCoreReadWrite\";\n" +
                        "GRANT USAGE ON SCHEMA \"LegalEntity\" TO \"AafCoreReadOnly\";");

                preparedStatement.executeUpdate();

                entityData = "{\n" +
                        "    \"EntityType\": \"LegalEntity\",\n" +
                        "    \"TotalRows\": 0,\n" +
                        "    \"EntityData\": []\n" +
                        "}";

                //TODO: Filter or mask unauthorized or sensitive attributes for this InformationSystemUserRole (as JSON)???
            }
        }
        catch (Exception e)
        {
            logger.error("PublishBusinessEntity() failed due to: " + e);
            //TODO: Improve this error output???
            return new ResponseEntity<String>("{[]}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        finally
        {
            try
            {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }

                if (callableStatement != null) {
                    callableStatement.close();
                    callableStatement = null;
                }

                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            }
            catch (SQLException e)
            {
                logger.error("Failed to close statement and/or connection resource in PublishBusinessEntity() due to: " + e);
            }
        }

        logger.info("PublishBusinessEntity() succeeded for " + databaseName);
        //result = new JSONObject("{\"EntityData\":" + entityData + "}");
        //return "{\"EntityData\":" + entityData + "}";
        //TODO: Echo input parameters in Postgres function return JSON
        return new ResponseEntity<String>(entityData, HttpStatus.CREATED);
    }

    public String GuardAgainstSqlIssues(String sqlFragment, String[] sqlBlacklistValues)
    {
        String issueValues = "";

        try
        {
            //NOTE: Based on Spring Tips: Configuration (https://spring.io/blog/2020/04/23/spring-tips-configuration)
            //filterIssueValues = new String[]{environment.getProperty("sqlToAllowInWhereClause").toLowerCase()};

            logger.info("Attempting to GuardAgainstSqlIssues() for '" + sqlFragment + "'");

            for (String sqlBlacklistValue : sqlBlacklistValues) {
                if (sqlFragment.contains(sqlBlacklistValue.toLowerCase())) {
                    issueValues = issueValues + sqlBlacklistValue + ",";
                }
            }
        }
        catch (Exception e)
        {
            logger.error("GuardAgainstSqlIssues failed due to: " + e);
            return issueValues;
        }

        logger.info("GuardAgainstSqlIssues succeeded when '" + sqlFragment + "' did not contain '" + Arrays.toString(sqlBlacklistValues) + "'");
        return issueValues;
    }
}