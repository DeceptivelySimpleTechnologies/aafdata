//NOTE: Copyright © 2003-2024 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

package com.dsimpletech.aafdata.SystemDataService.controller;


import com.dsimpletech.aafdata.SystemDataService.database.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ServerWebExchange;

import java.lang.Exception;

import java.sql.*;
import java.time.Instant;
import java.util.*;


@Tag(name = "Adaptív Application Foundation (AAF) Data Layer (AAF Data)", description = "Locally-Sourced, Artisanal Data™")
@RestController
@RequestMapping(value = "/")
public class BusinessEntityController
{
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

    @Autowired
    private RestTemplate restTemplate;

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
                entityTypeAttributes.add(new EntityTypeAttribute(resultSet.getInt("Id"), resultSet.getInt("EntitySubtypeId"), resultSet.getString("TextKey"), resultSet.getString("LocalizedName"), resultSet.getString("LocalizedDescription"), resultSet.getString("LocalizedAbbreviation"), resultSet.getString("LocalizedInformation"), resultSet.getString("LocalizedPlaceholder"), resultSet.getBoolean("IsLocalizable"), resultSet.getBoolean("IsToBeAssociatedWithEachEntityTypeDefinition"), resultSet.getInt("GeneralizedDataTypeEntitySubtypeId"), resultSet.getInt("DataSizeOrMaximumLengthInBytesOrCharacters"), resultSet.getInt("DataPrecision"), resultSet.getInt("DataScale"), resultSet.getInt("KeyTypeEntitySubtypeId"), resultSet.getInt("RelatedEntityTypeId"), resultSet.getInt("RelatedEntityTypeAttributeId"), resultSet.getInt("RelatedEntityTypeCardinalityEntitySubtypeId"), resultSet.getString("EntitySubtypeGroupKey"), resultSet.getInt("EntityTypeAttributeValueEntitySubtypeId"), resultSet.getString("DefaultValue"), resultSet.getString("MinimumValue"), resultSet.getString("MaximumValue"), resultSet.getString("RegExValidationPattern"), resultSet.getFloat("StepIncrementValue"), resultSet.getString("RemoteValidationMethodAsAjaxUri"), resultSet.getInt("IndexEntitySubtypeId"), resultSet.getInt("UniquenessEntitySubtypeId"), resultSet.getInt("SensitivityEntitySubtypeId"), resultSet.getTimestamp("PublishedAtDateTimeUtc"), resultSet.getInt("PublishedByInformationSystemUserId"), resultSet.getInt("Ordinal"), resultSet.getBoolean("IsActive"), resultSet.getTimestamp("CreatedAtDateTimeUtc"), resultSet.getInt("CreatedByInformationSystemUserId"), resultSet.getTimestamp("UpdatedAtDateTimeUtc"), resultSet.getInt("UpdatedByInformationSystemUserId"), resultSet.getTimestamp("DeletedAtDateTimeUtc"), resultSet.getInt("DeletedByInformationSystemUserId")));
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
                entitySubtypes.add(new EntitySubtype(resultSet.getInt("Id"), resultSet.getInt("EntitySubtypeId"), resultSet.getString("TextKey"), resultSet.getString("LocalizedName"), resultSet.getString("LocalizedDescription"), resultSet.getString("LocalizedAbbreviation"), resultSet.getInt("Ordinal"), resultSet.getBoolean("IsActive"), resultSet.getTimestamp("CreatedAtDateTimeUtc"), resultSet.getInt("CreatedByInformationSystemUserId"), resultSet.getTimestamp("UpdatedAtDateTimeUtc"), resultSet.getInt("UpdatedByInformationSystemUserId"), resultSet.getTimestamp("DeletedAtDateTimeUtc"), resultSet.getInt("DeletedByInformationSystemUserId")));
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

    @Operation(summary = "Create new, unpublished business entity data in the AafCore database, following all AAF rules and conventions.", description = "Create new, unpublished business entity data in the AafCore database, following all AAF rules and conventions, by providing the valid, required data and any valid, optional data as a JSON Web Token (JWT, please see https://jwt.io/) in the HTTP request body")
    @Parameter(in = ParameterIn.HEADER, description = "API key", name = "ApiKey", content = @Content(schema = @Schema(type = "string")))
    @Parameter(in = ParameterIn.COOKIE, description = "JWT Authentication token", name = "Authentication", content = @Content(schema = @Schema(type = "string")))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping(value = "/entityTypeDefinitions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> CreateNewBusinessEntity(@RequestParam(defaultValue = "#{T(java.time.Instant).now()}") Instant asOfDateTimeUtc, @RequestBody String requestBody, ServerWebExchange exchange) throws Exception
    {
        ServerHttpRequest request = null;

        String[] sqlBlacklistValues = null;
        String errorValues = "";

        String apiKey = "";

        ObjectMapper objectMapper = null;

        HttpCookie authenticationJwt = null;
        Base64.Decoder decoderBase64 = Base64.getUrlDecoder();
        String[] authenticationJwtSections = null;
        JsonNode authenticationJwtHeader = null;
        JsonNode authenticationJwtPayload = null;
        Long userId = -1L;

        String[] bodyJwtSections = null;
        JsonNode bodyJwtHeader = null;
        JsonNode bodyJwtPayload = null;

        String textKey = "";
        Long ordinal = -1L;
        Boolean isActive = false;

        HttpHeaders headers = null;
        EntityTypeDefinition cachedEntityTypeDefinition = null;
        String cloneActionRequestBody = "";
//        RestTemplateBuilder restTemplateBuilder = null;
//        RestTemplate restTemplate = null;
        HttpEntity<String> entity = null;
        ResponseEntity<String> cloneActionResponseBody = null;

        int entityIdStart = -1;
        int entityIdEnd = -1;
        Long entityId = -1L;

        JsonNode jsonResponseBody = null;
        String entityData = "";

        try
        {
            //NOTE: Essentially validate existingBusinessEntityId in local cache
            logger.info("Attempting to CreateNewBusinessEntity()");

            request = exchange.getRequest();

            //TODO: Add ApiKey input @Parameter to EDM methods

            //NOTE: No database structure changes are necessary for this operation, which calls the EDM PostBusinessEntity method, etc, so no database connection is required

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
            userId = -100L;

            //TODO: Check user's role(s) and permissions for this operation

            //NOTE: Example request http://localhost:8080/Person with "Authentication" JWT and JWT request body
            //NOTE: https://learning.postman.com/docs/sending-requests/response-data/cookies/
//            ApiKey: ???
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

//            sqlBlacklistValues = environment.getProperty("sqlNotToAllow").toLowerCase().split(",");
            //TODO: *** Only check request body for SQL injection
//            errorValues = GuardAgainstSqlIssues(bodyJwtPayload.toString(), sqlBlacklistValues);

            if (errorValues.length() == 0) {
                //NOTE: Validate newBusinessEntityName in request body
                if (!IsValidEntityOrAttributeName(bodyJwtPayload.get("body").get("LocalizedName").asText()))
                {
                    throw new Exception("Invalid EntityTypeDefinition name '" + bodyJwtPayload.get("body").get("LocalizedName").asText() + "'");
                }

                //NOTE: Optimistically (and consistently) not checking maximum length of newBusinessEntityName, newBusinessEntityDescription, or newBusinessEntityAbbreviation because the database constraints will catch it

//                cachedEntityTypeDefinition = GetCachedEntityTypeDefinitionById(id);

                //NOTE: Validate optional newBusinessEntityTextKey in request body or generate TextKey
                if (bodyJwtPayload.get("body").has("TextKey"))
                {
                    if (!IsValidTextKey(bodyJwtPayload.get("body").get("TextKey").asText()))
                    {
                        throw new Exception("Invalid TextKey value '" + bodyJwtPayload.get("body").get("TextKey").asText() + "'");
                    }
                    else
                    {
                        textKey = bodyJwtPayload.get("body").get("TextKey").asText();
                    }
                }
                else
                {
                    //TODO: Add a method to generate a valid TextKey value for any EntityTypeDefinition, and call it here
                    textKey = "entitytypedefinition-" + GetCachedEntitySubtypeById(bodyJwtPayload.get("body").get("EntitySubtypeId").asLong()).getLocalizedName().toLowerCase() + "-" + bodyJwtPayload.get("body").get("LocalizedName").asText().toLowerCase() + "-" + GenerateRandomLowercaseAlphanumericString(5);
                }

                //NOTE: Get optional Ordinal value in request body or supply default value
                if (bodyJwtPayload.get("body").has("Ordinal"))
                {
                    ordinal = bodyJwtPayload.get("body").get("Ordinal").asLong();
                }
                else
                {
                    ordinal = -1L;
                }

                //NOTE: Get optional IsActive value in request body or supply default value
                if (bodyJwtPayload.get("body").has("IsActive"))
                {
                    isActive = bodyJwtPayload.get("body").get("IsActive").asBoolean();
                }
                else
                {
                    isActive = true;
                }

                //NOTE: Insert new, unpublished EntityTypeDefinition, and get Id value
                headers = new HttpHeaders();
                headers.add("ApiKey", apiKey);
                headers.add("Cookie", authenticationJwt.toString());

                //TODO: Don't assume that optional Ordinal, IsActive, etc. are present in the request body
                //TODO: Figure out appropriate VersionTag, DataLocationEntitySubtypeId, and DataStructureEntitySubtypeId values
                cloneActionRequestBody = "{\n" +
                        "    \"EntitySubtypeId\": " + bodyJwtPayload.get("body").get("EntitySubtypeId").asLong() + ",\n" +
                        "    \"TextKey\": \"" + textKey + "\",\n" +
                        "    \"LocalizedName\": \"" + bodyJwtPayload.get("body").get("LocalizedName").asText() + "\",\n" +
                        "    \"LocalizedDescription\": \"" + bodyJwtPayload.get("body").get("LocalizedDescription").asText() + "\",\n" +
                        "    \"LocalizedAbbreviation\": \"" + bodyJwtPayload.get("body").get("LocalizedAbbreviation").asText() + "\",\n" +
                        "    \"VersionTag\": \"" + "000.000.000" + "\",\n" +
                        "    \"DataLocationEntitySubtypeId\": " + "4" + ",\n" +
                        "    \"DataStructureEntitySubtypeId\": " + "1" + ",\n" +
                        "    \"PublishedAtDateTimeUtc\": \"9999-12-31 23:59:59.999\",\n" +
                        "    \"PublishedByInformationSystemUserId\": " + "-1" + ",\n" +
                        "    \"Ordinal\": " + ordinal + ",\n" +
                        "    \"IsActive\": " + isActive + "\n" +
                        "  }";

                entity = new HttpEntity<String>(cloneActionRequestBody, headers);

                //TODO: Implement second fix for RestTemplateBuilder
//                restTemplateBuilder = new RestTemplateBuilder();
//                restTemplate = restTemplate;
                cloneActionResponseBody = restTemplate.postForEntity("http://localhost:8080/entityTypes/EntityTypeDefinition", entity, String.class);

                //TODO: Check response body for success and to get Id

                entityIdStart = cloneActionResponseBody.getBody().indexOf("\"Id\":") + 5;
                System.out.println("entityIdStart: " + entityIdStart);

                entityIdEnd = cloneActionResponseBody.getBody().indexOf(",", entityIdStart);
                System.out.println("entityIdEnd: " + entityIdEnd);

                entityId = Long.parseLong(cloneActionResponseBody.getBody().substring(entityIdStart, entityIdEnd));
                System.out.println("entityId: " + entityId);

                //TODO: Add AsOfDataTimeUtc and result text to standard response structure
                //TODO: Echo input parameters in Postgres function return JSON
                entityData = objectMapper.readTree(cloneActionResponseBody.getBody()).toString();

                //TODO: Insert new, unpublished, required EntityTypeDefinitionEntityTypeAttributeAssociations
                for (int i = 0 ; i < entityTypeAttributes.size() ; i++)
                {
                    if (GetCachedEntityTypeAttributeById(entityTypeAttributes.get(i).getId()).isIsToBeAssociatedWithEachEntityTypeDefinition())
                    {
                        cloneActionRequestBody = "{\n" +
                                "    \"EntitySubtypeId\": 0,\n" +
                                "    \"TextKey\": \"entitytypedefinitionentitytypeattributeassociation-" + bodyJwtPayload.get("body").get("LocalizedName").asText().toLowerCase() + "-" + GetCachedEntityTypeAttributeById(entityTypeAttributes.get(i).getId()).getLocalizedName().toLowerCase() + "\",\n" +
                                "    \"EntityTypeDefinitionId\": " + entityId + ",\n" +
                                "    \"EntityTypeAttributeId\": " + entityTypeAttributes.get(i).getId() + ",\n" +
                                "    \"PublishedAtDateTimeUtc\": \"9999-12-31 23:59:59.999\",\n" +
                                "    \"PublishedByInformationSystemUserId\": " + "-1" + "\n" +
                                "  }";

                        entity = new HttpEntity<String>(cloneActionRequestBody, headers);

                        cloneActionResponseBody = restTemplate.postForEntity("http://localhost:8080/entityTypes/EntityTypeDefinitionEntityTypeAttributeAssociation", entity, String.class);
                        //TODO: Check response body for success
                    }
                }

                //TODO: Confirm all required EntityTypeAttributes are associated with the new EntityTypeDefinition
                //TODO: Refresh local data cache, i.e. should unpublished entity data be cached???
                //NOTE: Return new, unpublished entity data

                //TODO: Consider converting this to a BPMN process when possible
            }
            //TODO: Else/exception here (and in EDM?) if dangerous SQL found???
        }
        catch (Exception e)
        {
            logger.error("CreateNewBusinessEntity() failed due to: " + e);
            //TODO: Improve this error output???
            return new ResponseEntity<String>("{[]}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("CreateNewBusinessEntity() succeeded");
        return new ResponseEntity<String>(entityData, HttpStatus.CREATED);
    }

    @Operation(summary = "Clone an existing business entity as the starting point for new, unpublished business entity data in the AafCore database, following all AAF rules and conventions.", description = "Clone existing business entity data in the AafCore database, following all AAF rules and conventions, by providing the valid, required data and any valid, optional data as a JSON Web Token (JWT, please see https://jwt.io/) in the HTTP request body")
    @Parameter(in = ParameterIn.HEADER, description = "API key", name = "ApiKey", content = @Content(schema = @Schema(type = "string")))
    @Parameter(in = ParameterIn.COOKIE, description = "JWT Authentication token", name = "Authentication", content = @Content(schema = @Schema(type = "string")))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping(value = "/entityTypeDefinitions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> CloneExistingBusinessEntity(@PathVariable("id") Long id, @RequestParam(defaultValue = "#{T(java.time.Instant).now()}") Instant asOfDateTimeUtc, @RequestBody String requestBody, ServerWebExchange exchange) throws Exception
    {
        ServerHttpRequest request = null;

        String[] sqlBlacklistValues = null;
        String errorValues = "";

        String apiKey = "";

        ObjectMapper objectMapper = null;

        HttpCookie authenticationJwt = null;
        Base64.Decoder decoderBase64 = Base64.getUrlDecoder();
        String[] authenticationJwtSections = null;
        JsonNode authenticationJwtHeader = null;
        JsonNode authenticationJwtPayload = null;
        Long userId = -1L;

        String[] bodyJwtSections = null;
        JsonNode bodyJwtHeader = null;
        JsonNode bodyJwtPayload = null;

        String textKey = "";

        HttpHeaders headers = null;
        EntityTypeDefinition cachedEntityTypeDefinition = null;
        String cloneActionRequestBody = "";
//        RestTemplateBuilder restTemplateBuilder = null;
//        RestTemplate restTemplate = null;
        HttpEntity<String> entity = null;
        ResponseEntity<String> cloneActionResponseBody = null;

        int entityIdStart = -1;
        int entityIdEnd = -1;
        Long entityId = -1L;

        JsonNode jsonResponseBody = null;
        String entityData = "";

        try
        {
            //NOTE: Essentially validate existingBusinessEntityId in local cache
            logger.info("Attempting to CloneExistingBusinessEntity() for " + id + " (" + GetCachedEntityTypeDefinitionById(id).getLocalizedName() + ")");

            request = exchange.getRequest();

            //TODO: Add ApiKey input @Parameter to EDM methods

            //NOTE: No database structure changes are necessary for this operation, which calls the EDM PostBusinessEntity method, etc, so no database connection is required

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
            userId = -100L;

            //TODO: Check user's role(s) and permissions for this operation

            //NOTE: Example request http://localhost:8080/Person with "Authentication" JWT and JWT request body
            //NOTE: https://learning.postman.com/docs/sending-requests/response-data/cookies/
//            ApiKey: ???
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

//            sqlBlacklistValues = environment.getProperty("sqlNotToAllow").toLowerCase().split(",");
            //TODO: *** Only check request body for SQL injection
//            errorValues = GuardAgainstSqlIssues(bodyJwtPayload.toString(), sqlBlacklistValues);

            if (errorValues.length() == 0) {
                //NOTE: Validate newBusinessEntityName in request body
                if (!IsValidEntityOrAttributeName(bodyJwtPayload.get("body").get("LocalizedName").asText()))
                {
                    throw new Exception("Invalid EntityTypeDefinition name '" + bodyJwtPayload.get("body").get("LocalizedName").asText() + "'");
                }

                //NOTE: Optimistically (and consistently) not checking maximum length of newBusinessEntityName, newBusinessEntityDescription, or newBusinessEntityAbbreviation because the database constraints will catch it

                cachedEntityTypeDefinition = GetCachedEntityTypeDefinitionById(id);

                //NOTE: Validate optional newBusinessEntityTextKey in request body or generate TextKey
                if (bodyJwtPayload.get("body").has("TextKey"))
                {
                    if (!IsValidTextKey(bodyJwtPayload.get("body").get("TextKey").asText()))
                    {
                        throw new Exception("Invalid TextKey value '" + bodyJwtPayload.get("body").get("TextKey").asText() + "'");
                    }
                    else
                    {
                        textKey = bodyJwtPayload.get("body").get("TextKey").asText();
                    }
                }
                else
                {
                    //TODO: Add a method to generate a valid TextKey value for any EntityTypeDefinition, and call it here
                    //TODO: Watch out for '-businessentity' in cloned entity vs '-none' in source entity
                    //TODO: Create Role subtype values, e.g EntityTypeId, Create, etc
                    textKey = "entitytypedefinition-" + GetCachedEntitySubtypeById(bodyJwtPayload.get("body").get("EntitySubtypeId").asLong()).getLocalizedName().toLowerCase() + "-" + bodyJwtPayload.get("body").get("LocalizedName").asText().toLowerCase() + "-" + GenerateRandomLowercaseAlphanumericString(5);
                }

                //NOTE: Insert new, unpublished EntityTypeDefinition, and get Id value
                headers = new HttpHeaders();
                headers.add("ApiKey", apiKey);
                headers.add("Cookie", authenticationJwt.toString());

                //TODO: Figure out appropriate VersionTag, DataLocationEntitySubtypeId, and DataStructureEntitySubtypeId values
                cloneActionRequestBody = "{\n" +
                        "    \"EntitySubtypeId\": " + cachedEntityTypeDefinition.getEntitySubtypeId() + ",\n" +
                        "    \"TextKey\": \"" + textKey + "\",\n" +
                        "    \"LocalizedName\": \"" + bodyJwtPayload.get("body").get("LocalizedName").asText() + "\",\n" +
                        "    \"LocalizedDescription\": \"" + bodyJwtPayload.get("body").get("LocalizedDescription").asText() + "\",\n" +
                        "    \"LocalizedAbbreviation\": \"" + bodyJwtPayload.get("body").get("LocalizedAbbreviation").asText() + "\",\n" +
                        "    \"VersionTag\": \"" + "000.000.000" + "\",\n" +
                        "    \"DataLocationEntitySubtypeId\": " + "4" + ",\n" +
                        "    \"DataStructureEntitySubtypeId\": " + "1" + ",\n" +
                        "    \"PublishedAtDateTimeUtc\": \"9999-12-31 23:59:59.999\",\n" +
                        "    \"PublishedByInformationSystemUserId\": " + "-1" + ",\n" +
                        "    \"Ordinal\": " + cachedEntityTypeDefinition.getOrdinal() + ",\n" +
                        "    \"IsActive\": " + cachedEntityTypeDefinition.isIsActive() + "\n" +
                        "  }";

                entity = new HttpEntity<String>(cloneActionRequestBody, headers);

                //TODO: Implement second fix for RestTemplateBuilder
//                restTemplateBuilder = new RestTemplateBuilder();
//                restTemplate = restTemplate;
                cloneActionResponseBody = restTemplate.postForEntity("http://localhost:8080/entityTypes/EntityTypeDefinition", entity, String.class);

                //TODO: Check response body for success and to get Id

                entityIdStart = cloneActionResponseBody.getBody().indexOf("\"Id\":") + 5;
                System.out.println("entityIdStart: " + entityIdStart);

                entityIdEnd = cloneActionResponseBody.getBody().indexOf(",", entityIdStart);
                System.out.println("entityIdEnd: " + entityIdEnd);

                entityId = Long.parseLong(cloneActionResponseBody.getBody().substring(entityIdStart, entityIdEnd));
                System.out.println("entityId: " + entityId);

                //TODO: Add AsOfDataTimeUtc and result text to standard response structure
                //TODO: Echo input parameters in Postgres function return JSON
                entityData = objectMapper.readTree(cloneActionResponseBody.getBody()).toString();

                //TODO: Insert new, unpublished EntityTypeDefinitionEntityTypeAttributeAssociations that match the specified EntityTypeDefinition's EntityTypeDefinitionEntityTypeAttributeAssociations
                for (int i = 0 ; i < entityTypeDefinitionEntityTypeAttributeAssociations.size() ; i++)
                {
                    if (entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getEntityTypeDefinitionId() == cachedEntityTypeDefinition.getId())
                    {
                        cloneActionRequestBody = "{\n" +
                                "    \"EntitySubtypeId\": 0,\n" +
                                "    \"TextKey\": \"entitytypedefinitionentitytypeattributeassociation-" + bodyJwtPayload.get("body").get("LocalizedName").asText().toLowerCase() + "-" + GetCachedEntityTypeAttributeById(entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getEntityTypeAttributeId()).getLocalizedName().toLowerCase() + "\",\n" +
                                "    \"EntityTypeDefinitionId\": " + entityId + ",\n" +
                                "    \"EntityTypeAttributeId\": " + entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getEntityTypeAttributeId() + ",\n" +
                                "    \"PublishedAtDateTimeUtc\": \"9999-12-31 23:59:59.999\",\n" +
                                "    \"PublishedByInformationSystemUserId\": " + "-1" + "\n" +
                                "  }";

                        entity = new HttpEntity<String>(cloneActionRequestBody, headers);

                        cloneActionResponseBody = restTemplate.postForEntity("http://localhost:8080/entityTypes/EntityTypeDefinitionEntityTypeAttributeAssociation", entity, String.class);
                        //TODO: Check response body for success
                    }
                }

                //TODO: Confirm all required EntityTypeAttributes are associated with the new EntityTypeDefinition
                //TODO: Refresh local data cache, i.e. should unpublished entity data be cached???
                //NOTE: Return new, unpublished entity data

                //TODO: Consider converting this to a BPMN process when possible
            }
            //TODO: Else/exception here (and in EDM?) if dangerous SQL found???
        }
        catch (Exception e)
        {
            logger.error("CloneExistingBusinessEntity() failed due to: " + e);
            //TODO: Improve this error output???
            return new ResponseEntity<String>("{[]}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("CloneExistingBusinessEntity() succeeded");
        return new ResponseEntity<String>(entityData, HttpStatus.CREATED);
    }

    @Operation(summary = "Create or alter the internal structure of the AafCore database, including its schemas, entity models (tables), functions, and system/lookup data, e.g. EntityTypeDefinition, EntityTypeAttribute, EntityType, EntitySubtype, etc, that has been defined/scripted by the AafCoreModeler role.", description = "Create or alter the internal structure of the database by providing the valid, required data and any valid, optional data as a JSON Web Token (JWT, please see https://jwt.io/) in the HTTP request body")
    @Parameter(in = ParameterIn.COOKIE, description = "JWT Authentication token", name = "Authentication", content = @Content(schema = @Schema(type = "string")))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping(value = "/databases/{databaseName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> PublishBusinessEntity(@PathVariable("databaseName") String databaseName, @RequestParam(defaultValue = "#{T(java.time.Instant).now()}") Instant asOfDateTimeUtc, @RequestBody String requestBody, ServerWebExchange exchange) throws Exception
    {
        ServerHttpRequest request = null;

//        long entityTypeId = -1;
        ArrayList<EntityTypeDefinition> unpublishedEntityTypeDefinitions = null;
        ArrayList<EntityTypeDefinitionEntityTypeAttributeAssociation> unpublishedEntityTypeAssociations = null;

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

        ObjectNode unpublishedEntityData = null;

//        String[] entityTypeAttributesNeverToReturn = null;

//        String insertClause = "";
//        String insertValues = "";

//        String selectClause = "";

        String preparedStatementSql = "";

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

            //TODO: Client Communication Service (CCS) to validate request, etc

            //TODO: Validate structure method
            //NOTE: Remove the Publisher role, i.e. publish becomes a privilege???
            //NOTE: Add publishing columns to EntityTypeDefinition table
            //NOTE: Add publishing columns to EntityTypeAttribute table
            //NOTE: Add publishing columns to EntityTypeDefinitionEntityTypeAttributeAssociation table
            //NOTE: Add publishing columns to EntitySubtype table???
            //NOTE: Add publishing columns to EntityTypeAttribute data
            //TODO: How to handle modeling and publishing scripted data???
            //TODO: Publish inactive entities???
            //TODO: Add application properties and defaults for loc and min environments and for EDM port for updates

            //NOTE: Get unpublished EntityTypeDefinitions
            //TODO: Get unpublished EntityTypeAttributes
            //TODO: Get unpublished EntityTypeDefinitionEntityTypeAttributeAssociations
            //TODO: Get unpublished EntitySubtypes
            //TODO: Generate pending (new but unpublished as of) change list for approval, and pass it to PublishBusinessEntity() as approval

            //NOTE: Create new entity schema
            //TODO: Create new entity table
            //TODO: Create new entity scripted data
            //TODO: Update as published
            //TODO: Re-cache SDS and EDM data

            //TODO: Refactor int, ArrayLists, etc to long, etc and other SDS changes in EDM

            //TODO: Remove publisher role from EDM and SDS README files
            //TODO: Consider converting this to a BPMN process when possible
            //TODO: When to publish EntityTypeAttributes???

            //TODO: Replace this if with if errors found and outdent all below
            if (errorValues.length() == 0)
            {
                //NOTE: Create schemas, based on unpublished EntityTypeDefinitions
                unpublishedEntityData = GetUnpublishedEntityData(1, asOfDateTimeUtc);

                //TODO: Better/more meaningful test here???
                if (unpublishedEntityData != null)
                {
                    unpublishedEntityTypeDefinitions = new ArrayList<EntityTypeDefinition>();

                    for (int i = 0; i < unpublishedEntityData.get("EntityData").size(); i++)
                    {
                        //preparedStatement = connection.prepareStatement("insert into Emp values(?,?)");
                        //stmt.setInt(1,101);//1 specifies the first parameter in the query
                        //stmt.setString(2,"Ratan");

                        unpublishedEntityTypeDefinitions.add(new EntityTypeDefinition(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("Id").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("EntitySubtypeId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("TextKey").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedName").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedDescription").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedAbbreviation").asText(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("PublishedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("PublishedByInformationSystemUserId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("Ordinal").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("IsActive").asBoolean(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("CreatedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("CreatedByInformationSystemUserId").asLong(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("UpdatedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("UpdatedByInformationSystemUserId").asLong(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("DeletedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("DeletedByInformationSystemUserId").asLong()));

                        preparedStatementSql = "CREATE SCHEMA " + unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedName") + "\n" +
                                "    AUTHORIZATION \"AafCoreModeler\";\n" +
                                "GRANT USAGE ON SCHEMA " + unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedName") + " TO \"AafCoreReadWrite\";\n" +
                                "GRANT USAGE ON SCHEMA " + unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedName") + " TO \"AafCoreReadOnly\";";

//                        preparedStatement = connection.prepareStatement(preparedStatementSql);
//                        preparedStatement.executeUpdate();

                        //TODO: Update schema as published
                    }
                }
                else
                {
                    //TODO: Better/more meaningful message here, or no exception necessary/appropriate???
                    throw new Exception("Failed to publish unpublished schemas, based on EntityTypeDefinitions");
                }

                //NOTE: Publish unpublished EntityTypeAttributeAssociations
                //TODO: Update attribute as published

                //NOTE: Create tables, based on unpublished EntityTypeDefinitionEntityTypeAttributeAssociations
                unpublishedEntityData = GetUnpublishedEntityData(3, asOfDateTimeUtc);

                //TODO: Better/more meaningful test here???
                if (unpublishedEntityData != null) {
                    unpublishedEntityTypeAssociations = new ArrayList<EntityTypeDefinitionEntityTypeAttributeAssociation>();

                    for (int i = 0; i < unpublishedEntityData.get("EntityData").size(); i++) {
                        if (i == 0) {
                            preparedStatementSql = "CREATE TABLE " + unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedName") + "." + unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedName") + "\n";
                            preparedStatementSql = preparedStatementSql + "(" + "\n";
                        }

                        //NOTE: Get the Ids of any associated EntityTypeAttributes
                        for (int j = 0; j < entityTypeDefinitionEntityTypeAttributeAssociations.size(); j++) {
                            if ((entityTypeDefinitionEntityTypeAttributeAssociations.get(j).getEntityTypeDefinitionId() == unpublishedEntityTypeDefinitions.get(i).getId()) && (entityTypeDefinitionEntityTypeAttributeAssociations.get(j).getEntityTypeDefinitionId() == unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("Id").asLong())) {
                                unpublishedEntityTypeAssociations.add(new EntityTypeDefinitionEntityTypeAttributeAssociation(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("Id").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("EntitySubtypeId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("TextKey").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("EntityTypeDefinitionId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("EntityTypeAttributeId").asLong(), Timestamp.from(Instant.parse(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("PublishedAtDateTimeUtc").asText())), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("PublishedByInformationSystemUserId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("Ordinal").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("IsActive").asBoolean(), Timestamp.from(Instant.parse(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("CreatedAtDateTimeUtc").asText())), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("CreatedByInformationSystemUserId").asLong(), Timestamp.from(Instant.parse(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("UpdatedAtDateTimeUtc").asText())), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("UpdatedByInformationSystemUserId").asLong(), Timestamp.from(Instant.parse(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("DeletedAtDateTimeUtc").asText())), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("DeletedByInformationSystemUserId").asLong()));

                                for (int k = 0; k < entityTypeAttributes.size(); k++) {
                                    if (entityTypeAttributes.get(k).getId() == unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("Id").asLong()) {
                                        switch (unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("EntitySubtypeId").asInt()) {
                                            //NOTE: Boolean
                                            case 10:
                                                preparedStatementSql = preparedStatementSql + "    \"" + entityTypeAttributes.get(k).getLocalizedName() + "\" boolean NOT NULL,\n";
                                                break;
                                            //NOTE: Integer
                                            case 11:
                                                preparedStatementSql = preparedStatementSql + "    \"" + entityTypeAttributes.get(k).getLocalizedName() + "\" bigint NOT NULL,\n";
                                                break;
                                            //NOTE: UnicodeCharacter
                                            case 12:
                                                preparedStatementSql = preparedStatementSql + "    \"" + entityTypeAttributes.get(k).getLocalizedName() + "\" character NOT NULL,\n";
                                                break;
                                            //NOTE: UnicodeString
                                            case 13:
                                                preparedStatementSql = preparedStatementSql + "    \"" + entityTypeAttributes.get(k).getLocalizedName() + "\" character varying(" + entityTypeAttributes.get(k).getDataSizeOrMaximumLengthInBytesOrCharacters() + ") COLLATE pg_catalog.\"default\" NOT NULL,\n";
                                                break;
                                            //NOTE: DateTime
                                            case 14:
                                                preparedStatementSql = preparedStatementSql + "    \"" + entityTypeAttributes.get(k).getLocalizedName() + "\" timestamp without time zone NOT NULL,\n";
                                                break;
                                            //NOTE: Decimal
//                                            case 16:
//                                                preparedStatementSql = preparedStatementSql + "    \"" + entityTypeAttributes.get(k).getLocalizedName() + "\" " + entityTypeAttributes.get(k).getGeneralizedDataTypeEntitySubtypeId() + " NOT NULL,\n";
//                                                break;
                                            //TODO: Add UUID data type subtype???
                                            //TODO: Add DataSizeOrMaximumLengthInBytesOrCharacters values for TextKey, LocalizedName, LocalizedDescription, LocalizedAbbreviation, ResourceName, Digest, etc
                                            //TODO: Add Currency type or use Decimal???
                                            //TODO: Unpublish LegalEntity attributes and associations (or add new entity type)
                                        }
                                    }

                                    //CONSTRAINT
                                    //INDEX
                                }
                            }

//                        preparedStatement = connection.prepareStatement(preparedStatementSql);
//                        preparedStatement.executeUpdate();

                            //TODO: Update table as published
                        }
                    }
                }
                else
                {
                    //TODO: Better/more meaningful message here, or no exception necessary/appropriate???
                    throw new Exception("Failed to publish unpublished tables, based on EntityTypeDefinitionEntityTypeAttributeAssociations");
                }

                entityData = "{\n" +
                        "    \"EntityType\": \"Not Applicable (N/A)\",\n" +
                        "    \"TotalRows\": -1,\n" +
                        "    \"AsOfDataTimeUtc\": " + unpublishedEntityData.get("AsOfDataTimeUtc") + ",\n" +
                        "    \"EntityData\": []\n" +
                        "}";

                //TODO: Publish unpublished EntitySubtypes
                //TODO: Publish unpublished scripted entity data
                //TODO: Filter or mask unauthorized or sensitive attributes for this InformationSystemUserRole (as JSON)???
            }
            //TODO: Else/exception here (and in EDM?) if dangerous SQL found???
        }
        catch (Exception e)
        {
            logger.error("PublishBusinessEntity() for " + databaseName + " failed due to: " + e);
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

    private ObjectNode GetUnpublishedEntityData(long entityTypeDefinitionId, @RequestParam(defaultValue = "#{T(java.time.Instant).now()}") Instant asOfDateTimeUtc)
    {
        ObjectMapper objectMapper = null;
        ObjectNode unpublishedEntityData = null;

        ArrayNode entityTypeDefinitionData = null;
        ObjectNode entityTypeDefinition = null;

        try
        {
            logger.info("Attempting to GetUnpublishedEntityData() for EntityTypeDefinitionId = " + entityTypeDefinitionId + " as of '" + asOfDateTimeUtc.toString() + "'");

            //NOTE: Validate entityTypeDefinitionId
            if ((entityTypeDefinitionId < 1) || (entityTypeDefinitionId > 4))
            {
                throw new Exception("'entityTypeDefinitionId' query parameter must from 1 to 4, i.e. EntityTypeDefinition, EntityTypeAttribute, EntityTypeDefinitionEntityTypeAttributeAssociation, or EntitySubtype.");
            }

            //NOTE: Validate and sanitize asOfDateTimeUtc
            if (asOfDateTimeUtc.isBefore(Instant.parse("1900-01-01T00:00:00.000Z")))
            {
                throw new Exception("'asOfDateTimeUtc' query parameter must be greater than or equal to '1900-01-01'.");
            }

            //TODO: Create "end of time" constant and replace all "9999-12-31" values
            if (asOfDateTimeUtc.isAfter(Instant.parse("9999-12-31T23:59:59.999Z")))
            {
                throw new Exception("'asOfDateTimeUtc' query parameter must be less than or equal to '9999-12-31'.");
            }

            objectMapper = new ObjectMapper();
            unpublishedEntityData = objectMapper.createObjectNode();

            entityTypeDefinition= objectMapper.createObjectNode();
            entityTypeDefinitionData = objectMapper.createArrayNode();

            //NOTE: Only pending (new but unpublished as of asOfDateTimeUtc ) changes in this first version.  Non-breaking changes next.  Etc, etc, etc.  Crawl, walk, run.
            switch ((int) entityTypeDefinitionId)
            {
                //NOTE: EntityTypeDefinition
                case 1:
                    unpublishedEntityData.put("EntityType", "EntityTypeDefinition");

                    for (int i = 0 ; i < entityTypeDefinitions.size() ; i++)
                    {
                        //TODO: May want to do this with live, not cached data???
                        //TODO: Capture and check cached data timestamp???
                        //TODO: Also check if updated since asOfDateTimeUtc???
                        if (entityTypeDefinitions.get(i).getPublishedAtDateTimeUtc().equals(Timestamp.valueOf("9999-12-31 23:59:59.999")))
                        {
                            entityTypeDefinition.put("Id", entityTypeDefinitions.get(i).getId());
                            entityTypeDefinition.put("EntitySubtypeId", entityTypeDefinitions.get(i).getEntitySubtypeId());
                            entityTypeDefinition.put("TextKey", entityTypeDefinitions.get(i).getTextKey());
                            entityTypeDefinition.put("LocalizedName", entityTypeDefinitions.get(i).getLocalizedName());
                            entityTypeDefinition.put("LocalizedDescription", entityTypeDefinitions.get(i).getLocalizedDescription());
                            entityTypeDefinition.put("LocalizedAbbreviation", entityTypeDefinitions.get(i).getLocalizedAbbreviation());
                            entityTypeDefinition.put("PublishedAtDateTimeUtc", entityTypeDefinitions.get(i).getPublishedAtDateTimeUtc().toString());
                            entityTypeDefinition.put("PublishedByInformationSystemUserId", entityTypeDefinitions.get(i).getPublishedByInformationSystemUserId());
                            entityTypeDefinition.put("Ordinal", entityTypeDefinitions.get(i).getOrdinal());
                            entityTypeDefinition.put("IsActive", entityTypeDefinitions.get(i).isIsActive());    //NOTE: See https://stackoverflow.com/questions/42619986/lombok-annotation-getter-for-boolean-field
                            entityTypeDefinition.put("CreatedAtDateTimeUtc", entityTypeDefinitions.get(i).getCreatedAtDateTimeUtc().toString());
                            entityTypeDefinition.put("CreatedByInformationSystemUserId", entityTypeDefinitions.get(i).getCreatedByInformationSystemUserId());
                            entityTypeDefinition.put("UpdatedAtDateTimeUtc", entityTypeDefinitions.get(i).getUpdatedAtDateTimeUtc().toString());
                            entityTypeDefinition.put("UpdatedByInformationSystemUserId", entityTypeDefinitions.get(i).getUpdatedByInformationSystemUserId());
                            entityTypeDefinition.put("DeletedAtDateTimeUtc", entityTypeDefinitions.get(i).getDeletedAtDateTimeUtc().toString());
                            entityTypeDefinition.put("DeletedByInformationSystemUserId", entityTypeDefinitions.get(i).getDeletedByInformationSystemUserId());

                            entityTypeDefinitionData.addObject().put("EntityTypeDefinition", entityTypeDefinition);
                        }
                    }

                    unpublishedEntityData.put("TotalRows", entityTypeDefinitionData.size());
                    unpublishedEntityData.put("AsOfDateTimeUtc", asOfDateTimeUtc.toString());
                    unpublishedEntityData.set("EntityData", entityTypeDefinitionData);
                    break;

                //NOTE: EntityTypeAttribute
                case 2:
                    unpublishedEntityData.put("EntityType", "EntityTypeAttribute");

                    for (int i = 0 ; i < entityTypeAttributes.size() ; i++)
                    {
                        //TODO: May want to do this with live, not cached data???
                        //TODO: Capture and check cached data timestamp???
                        //TODO: Also check if updated since asOfDateTimeUtc???
                        if (entityTypeAttributes.get(i).getPublishedAtDateTimeUtc().equals(Timestamp.valueOf("9999-12-31 23:59:59.999")))
                        {
                            entityTypeDefinition.put("Id", entityTypeAttributes.get(i).getId());
                            entityTypeDefinition.put("LocalizedName", entityTypeAttributes.get(i).getLocalizedName());
                            entityTypeDefinition.put("LocalizedDescription", entityTypeAttributes.get(i).getLocalizedDescription());
                            entityTypeDefinition.put("LocalizedAbbreviation", entityTypeAttributes.get(i).getLocalizedAbbreviation());
                            entityTypeDefinition.put("LocalizedInformation", entityTypeAttributes.get(i).getLocalizedInformation());
                            entityTypeDefinition.put("LocalizedPlaceholder", entityTypeAttributes.get(i).getLocalizedPlaceholder());
                            entityTypeDefinition.put("IsLocalizable", entityTypeAttributes.get(i).isIsLocalizable());
                            entityTypeDefinition.put("GeneralizedDataTypeEntitySubtypeId", entityTypeAttributes.get(i).getGeneralizedDataTypeEntitySubtypeId());
                            entityTypeDefinition.put("DataSizeOrMaximumLengthInBytesOrCharacters", entityTypeAttributes.get(i).getDataSizeOrMaximumLengthInBytesOrCharacters());
                            entityTypeDefinition.put("DataPrecision", entityTypeAttributes.get(i).getDataPrecision());
                            entityTypeDefinition.put("DataScale", entityTypeAttributes.get(i).getDataScale());
                            entityTypeDefinition.put("KeyTypeEntitySubtypeId", entityTypeAttributes.get(i).getKeyTypeEntitySubtypeId());
                            entityTypeDefinition.put("RelatedEntityTypeId", entityTypeAttributes.get(i).getRelatedEntityTypeId());
                            entityTypeDefinition.put("RelatedEntityTypeAttributeId", entityTypeAttributes.get(i).getRelatedEntityTypeAttributeId());
                            entityTypeDefinition.put("RelatedEntityTypeCardinalityEntitySubtypeId", entityTypeAttributes.get(i).getRelatedEntityTypeCardinalityEntitySubtypeId());
                            entityTypeDefinition.put("EntitySubtypeGroupKey", entityTypeAttributes.get(i).getEntitySubtypeGroupKey());
                            entityTypeDefinition.put("EntityTypeAttributeValueEntitySubtypeId", entityTypeAttributes.get(i).getEntityTypeAttributeValueEntitySubtypeId());
                            entityTypeDefinition.put("DefaultValue", entityTypeAttributes.get(i).getDefaultValue());
                            entityTypeDefinition.put("MinimumValue", entityTypeAttributes.get(i).getMinimumValue());
                            entityTypeDefinition.put("MaximumValue", entityTypeAttributes.get(i).getMaximumValue());
                            entityTypeDefinition.put("RegExValidationPattern", entityTypeAttributes.get(i).getRegExValidationPattern());
                            entityTypeDefinition.put("StepIncrementValue", entityTypeAttributes.get(i).getStepIncrementValue());
                            entityTypeDefinition.put("RemoteValidationMethodAsAjaxUri", entityTypeAttributes.get(i).getRemoteValidationMethodAsAjaxUri());
                            entityTypeDefinition.put("IndexEntitySubtypeId", entityTypeAttributes.get(i).getIndexEntitySubtypeId());
                            entityTypeDefinition.put("UniquenessEntitySubtypeId", entityTypeAttributes.get(i).getUniquenessEntitySubtypeId());
                            entityTypeDefinition.put("SensitivityEntitySubtypeId", entityTypeAttributes.get(i).getSensitivityEntitySubtypeId());
                            entityTypeDefinition.put("PublishedAtDateTimeUtc", entityTypeAttributes.get(i).getPublishedAtDateTimeUtc().toString());
                            entityTypeDefinition.put("PublishedByInformationSystemUserId", entityTypeAttributes.get(i).getPublishedByInformationSystemUserId());
                            entityTypeDefinition.put("Ordinal", entityTypeAttributes.get(i).getOrdinal());
                            entityTypeDefinition.put("IsActive", entityTypeAttributes.get(i).isIsActive());    //NOTE: See https://stackoverflow.com/questions/42619986/lombok-annotation-getter-for-boolean-field
                            entityTypeDefinition.put("CreatedAtDateTimeUtc", entityTypeAttributes.get(i).getCreatedAtDateTimeUtc().toString());
                            entityTypeDefinition.put("CreatedByInformationSystemUserId", entityTypeAttributes.get(i).getCreatedByInformationSystemUserId());
                            entityTypeDefinition.put("UpdatedAtDateTimeUtc", entityTypeAttributes.get(i).getUpdatedAtDateTimeUtc().toString());
                            entityTypeDefinition.put("UpdatedByInformationSystemUserId", entityTypeAttributes.get(i).getUpdatedByInformationSystemUserId());
                            entityTypeDefinition.put("DeletedAtDateTimeUtc", entityTypeAttributes.get(i).getDeletedAtDateTimeUtc().toString());
                            entityTypeDefinition.put("DeletedByInformationSystemUserId", entityTypeAttributes.get(i).getDeletedByInformationSystemUserId());

                            entityTypeDefinitionData.addObject().put("EntityTypeDefinition", entityTypeDefinition);
                        }
                    }

                    unpublishedEntityData.put("TotalRows", entityTypeDefinitionData.size());
                    unpublishedEntityData.put("AsOfDateTimeUtc", asOfDateTimeUtc.toString());
                    unpublishedEntityData.set("EntityData", entityTypeDefinitionData);
                    break;

                //NOTE: EntityTypeDefinitionEntityTypeAttributeAssociation
                case 3:
                    unpublishedEntityData.put("EntityType", "EntityTypeDefinitionEntityTypeAttributeAssociation");

                    for (int i = 0 ; i < entityTypeDefinitionEntityTypeAttributeAssociations.size() ; i++)
                    {
                        //TODO: May want to do this with live, not cached data???
                        //TODO: Capture and check cached data timestamp???
                        //TODO: Also check if updated since asOfDateTimeUtc???
                        if (entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getPublishedAtDateTimeUtc().equals(Timestamp.valueOf("9999-12-31 23:59:59.999")))
                        {
                            entityTypeDefinition.put("Id", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getId());
                            entityTypeDefinition.put("EntitySubtypeId", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getEntitySubtypeId());
                            entityTypeDefinition.put("TextKey", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getTextKey());
                            entityTypeDefinition.put("EntityTypeDefinitionId", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getEntityTypeDefinitionId());
                            entityTypeDefinition.put("EntityTypeAttributeId", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getEntityTypeAttributeId());
                            entityTypeDefinition.put("PublishedAtDateTimeUtc", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getPublishedAtDateTimeUtc().toString());
                            entityTypeDefinition.put("PublishedByInformationSystemUserId", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getPublishedByInformationSystemUserId());
                            entityTypeDefinition.put("Ordinal", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getOrdinal());
                            entityTypeDefinition.put("IsActive", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).isIsActive());    //NOTE: See https://stackoverflow.com/questions/42619986/lombok-annotation-getter-for-boolean-field
                            entityTypeDefinition.put("CreatedAtDateTimeUtc", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getCreatedAtDateTimeUtc().toString());
                            entityTypeDefinition.put("CreatedByInformationSystemUserId", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getCreatedByInformationSystemUserId());
                            entityTypeDefinition.put("UpdatedAtDateTimeUtc", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getUpdatedAtDateTimeUtc().toString());
                            entityTypeDefinition.put("UpdatedByInformationSystemUserId", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getUpdatedByInformationSystemUserId());
                            entityTypeDefinition.put("DeletedAtDateTimeUtc", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getDeletedAtDateTimeUtc().toString());
                            entityTypeDefinition.put("DeletedByInformationSystemUserId", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getDeletedByInformationSystemUserId());

                            entityTypeDefinitionData.addObject().put("EntityTypeDefinition", entityTypeDefinition);
                        }
                    }

                    unpublishedEntityData.put("TotalRows", entityTypeDefinitionData.size());
                    unpublishedEntityData.put("AsOfDateTimeUtc", asOfDateTimeUtc.toString());
                    unpublishedEntityData.set("EntityData", entityTypeDefinitionData);
                    break;

                //NOTE: EntitySubtype
                case 4:
                    unpublishedEntityData.put("EntityType", "EntitySubtype");

                    for (int i = 0 ; i < entitySubtypes.size() ; i++)
                    {
                        //TODO: May want to do this with live, not cached data???
                        //TODO: Capture and check cached data timestamp???
                        //TODO: Also check if updated since asOfDateTimeUtc???
//                        if (entitySubtypes.get(i).getPublishedAtDateTimeUtc().equals(Timestamp.valueOf("9999-12-31 23:59:59.999")))
//                        {
//                            entityTypeDefinition.put("Id", entitySubtypes.get(i).getId());
//                            entityTypeDefinition.put("EntitySubtypeId", entitySubtypes.get(i).getEntitySubtypeId());
//                            entityTypeDefinition.put("TextKey", entitySubtypes.get(i).getTextKey());
//                            entityTypeDefinition.put("LocalizedName", entitySubtypes.get(i).getLocalizedName());
//                            entityTypeDefinition.put("LocalizedDescription", entitySubtypes.get(i).getLocalizedDescription());
//                            entityTypeDefinition.put("LocalizedAbbreviation", entitySubtypes.get(i).getLocalizedAbbreviation());
//                            entityTypeDefinition.put("PublishedAtDateTimeUtc", entitySubtypes.get(i).getPublishedAtDateTimeUtc().toString());
//                            entityTypeDefinition.put("PublishedByInformationSystemUserId", entitySubtypes.get(i).getPublishedByInformationSystemUserId());
//                            entityTypeDefinition.put("Ordinal", entitySubtypes.get(i).getOrdinal());
//                            entityTypeDefinition.put("IsActive", entitySubtypes.get(i).isIsActive());    //NOTE: See https://stackoverflow.com/questions/42619986/lombok-annotation-getter-for-boolean-field
//                            entityTypeDefinition.put("CreatedAtDateTimeUtc", entitySubtypes.get(i).getCreatedAtDateTimeUtc().toString());
//                            entityTypeDefinition.put("CreatedByInformationSystemUserId", entitySubtypes.get(i).getCreatedByInformationSystemUserId());
//                            entityTypeDefinition.put("UpdatedAtDateTimeUtc", entitySubtypes.get(i).getUpdatedAtDateTimeUtc().toString());
//                            entityTypeDefinition.put("UpdatedByInformationSystemUserId", entitySubtypes.get(i).getUpdatedByInformationSystemUserId());
//                            entityTypeDefinition.put("DeletedAtDateTimeUtc", entitySubtypes.get(i).getDeletedAtDateTimeUtc().toString());
//                            entityTypeDefinition.put("DeletedByInformationSystemUserId", entitySubtypes.get(i).getDeletedByInformationSystemUserId());
//
//                            entityTypeDefinitionData.addObject().put("EntityTypeDefinition", entityTypeDefinition);
//                        }
                    }

                    unpublishedEntityData.put("TotalRows", entityTypeDefinitionData.size());
                    unpublishedEntityData.put("AsOfDateTimeUtc", asOfDateTimeUtc.toString());
                    unpublishedEntityData.set("EntityData", entityTypeDefinitionData);
                    break;

                default:
                    throw new Exception("'entityTypeDefinitionId' query parameter must from 1 to 4, i.e. EntityTypeDefinition, EntityTypeAttribute, EntityTypeDefinitionEntityTypeAttributeAssociation, or EntitySubtype.");
            }

        }
        catch (Exception e)
        {
            logger.error("GetUnpublishedEntityData failed due to: " + e);
            return unpublishedEntityData;
        }

        logger.info("GetUnpublishedEntityData succeeded");
        return unpublishedEntityData;
    }

    private String GuardAgainstSqlIssues(String sqlFragment, String[] sqlBlacklistValues)
    {
        String issueValues = "";

        try
        {
            //NOTE: Based on Spring Tips: Configuration (https://spring.io/blog/2020/04/23/spring-tips-configuration)
            //filterIssueValues = new String[]{environment.getProperty("sqlToAllowInWhereClause").toLowerCase()};

            logger.info("Attempting to GuardAgainstSqlIssues() by checking that '" + sqlFragment + "' does not contain '" + Arrays.toString(sqlBlacklistValues) + "'");

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

        logger.info("GuardAgainstSqlIssues succeeded");
        return issueValues;
    }

    private boolean IsValidEntityOrAttributeName(String localizedName)
    {
        boolean isValid = false;

        try
        {
            logger.info("Attempting to test IsValidEntityOrAttributeName()");

            //NOTE: Check for valid Pascal case (tested at https://regex101.com/)
            if (localizedName.matches("^[A-Z][a-z]+(?:[A-Z][a-z]+)*$"))
            {
                isValid = true;
            }
            else
            {
                isValid = false;
            }

            //NOTE: Optimistically (and consistently) not checking maximum length of EntityTypeDefinition or EntityTypeAttribute LocalizedName because the database constraints will catch it
        }
        catch (Exception e)
        {
            logger.error("IsValidEntityOrAttributeName failed due to: " + e);
            return isValid;
        }

        logger.info("IsValidEntityOrAttributeName succeeded");
        return isValid;
    }

    private boolean IsValidTextKey(String textKey)
    {
        boolean isValid = false;

        try
        {
            logger.info("Attempting to test IsValidTextKey()");

            //NOTE: Check for all lowercase with dashes (tested at https://regex101.com/)
            if (textKey.matches("^[a-z-]*-[a-z0-9]{1,5}$"))
            {
                isValid = true;
            }
            else
            {
                isValid = false;
            }

            //NOTE: Optimistically (and consistently) not checking maximum length of TextKey because the database constraints will catch it
        }
        catch (Exception e)
        {
            logger.error("IsValidTextKey failed due to: " + e);
            return isValid;
        }

        logger.info("IsValidTextKey succeeded");
        return isValid;
    }

    private EntityTypeDefinition GetCachedEntityTypeDefinitionById(long id)
    {
        EntityTypeDefinition entityTypeDefinition = null;

        try
        {
            logger.info("Attempting to GetCachedEntityTypeDefinitionById() for Id = " + id);

            for (int i = 0 ; i < entityTypeDefinitions.size() ; i++)
            {
                if (entityTypeDefinitions.get(i).getId() == id)
                {
                    entityTypeDefinition = entityTypeDefinitions.get(i);
                    break;
                }
            }

            if (entityTypeDefinition == null)
            {
                throw new Exception("EntityTypeDefinition not found");
            }
        }
        catch (Exception e)
        {
            logger.error("GetCachedEntityTypeDefinitionById failed due to: " + e);
            return entityTypeDefinition;
        }

        logger.info("GetCachedEntityTypeDefinitionById succeeded");
        return entityTypeDefinition;
    }

    private EntityTypeAttribute GetCachedEntityTypeAttributeById(long id)
    {
        EntityTypeAttribute entityTypeAttribute = null;

        try
        {
            logger.info("Attempting to GetCachedEntityTypeAttributeById() for Id = " + id);

            for (int i = 0 ; i < entityTypeAttributes.size() ; i++)
            {
                if (entityTypeAttributes.get(i).getId() == id)
                {
                    entityTypeAttribute = entityTypeAttributes.get(i);
                    break;
                }
            }

            if (entityTypeAttribute == null)
            {
                throw new Exception("EntityTypeAttribute not found");
            }
        }
        catch (Exception e)
        {
            logger.error("GetCachedEntityTypeAttributeById failed due to: " + e);
            return entityTypeAttribute;
        }

        logger.info("GetCachedEntityTypeAttributeById succeeded");
        return entityTypeAttribute;
    }

    private EntitySubtype GetCachedEntitySubtypeById(long id)
    {
        EntitySubtype entitySubtype = null;

        try
        {
            logger.info("Attempting to GetCachedEntitySubtypeById() for Id = " + id);

            for (int i = 0 ; i < entitySubtypes.size() ; i++)
            {
                if (entitySubtypes.get(i).getId() == id)
                {
                    entitySubtype = entitySubtypes.get(i);
                    break;
                }
            }

            if (entitySubtype == null)
            {
                throw new Exception("EntitySubtype not found");
            }
        }
        catch (Exception e)
        {
            logger.error("GetCachedEntitySubtypeById failed due to: " + e);
            return entitySubtype;
        }

        logger.info("GetCachedEntitySubtypeById succeeded");
        return entitySubtype;
    }

    private String GenerateRandomLowercaseAlphanumericString(int length)
    {
        int leftLimit = 48;             //NOTE: Number 0
        int rightLimit = 122;           //NOTE: Letter z
        Random random = null;
        StringBuilder buffer = null;

        String randomLowercaseAlphanumericString = "";

        try
        {
            logger.info("Attempting to GenerateRandomLowercaseAlphanumericString() for length = " + length);

            random = new Random();
            buffer = new StringBuilder(length);

            randomLowercaseAlphanumericString = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(length)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        } catch (Exception e)
        {
            logger.error("GenerateRandomAlphanumericString failed due to: " + e);
            return randomLowercaseAlphanumericString;
        }

        logger.info("GenerateRandomAlphanumericString succeeded");
        return randomLowercaseAlphanumericString;
    }
}