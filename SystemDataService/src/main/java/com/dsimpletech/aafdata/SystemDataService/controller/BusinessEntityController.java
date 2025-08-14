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
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ServerWebExchange;

import java.lang.Exception;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.rmi.ConnectException;
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

    private String DB_DRIVER_CLASS = "";
    private String DB_URL = "";
    private String DB_USERNAME = "";
    private String DB_PASSWORD = "";

    //NOTE: https://medium.com/@AlexanderObregon/how-to-use-connection-pooling-for-faster-database-access-in-spring-boot-a352f672dfe3

    private ArrayList<EntityTypeDefinition> entityTypeDefinitions = null;
    private ArrayList<EntityTypeAttribute> entityTypeAttributes = null;
    private ArrayList<EntityTypeDefinitionEntityTypeAttributeAssociation> entityTypeDefinitionEntityTypeAttributeAssociations = null;
    private ArrayList<EntitySubtype> entitySubtypes = null;

    private String ENVIRONMENT_JWT_SHARED_SECRET = "";

    //NOTE: Matches the values returned by the Postgres functions public."GetMinimumDateTimeUtc"() and public."GetMaximumDateTimeUtc"(), defined in 001-GetMinimumDateTimeUtc.sql and 002-GetMaximumDateTimeUtc.sql
//    private final String minimumDateTimeUtc = "1900-01-01T00:00:00.000Z";
//    private final String maximumDateTimeUtc = "9999-12-31T23:59:59.999Z";

    //NOTE: Spring Boot Logback default logging implemented per https://www.baeldung.com/spring-boot-logging
    Logger logger = LoggerFactory.getLogger(BusinessEntityController.class);

//    @Autowired
//    private RestTemplate restTemplate;

    @PostConstruct
    private void CacheEntityData()
    {
        DatabaseConnection databaseConnection = null;

        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            logger.info("Attempting to CacheEntityData()");

            DB_DRIVER_CLASS = "postgresql";
            DB_URL = environment.getProperty("spring.datasource.url");
            DB_USERNAME = environment.getProperty("spring.datasource.username");
            DB_PASSWORD = environment.getProperty("spring.datasource.password");

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
                entityTypeAttributes.add(new EntityTypeAttribute(resultSet.getInt("Id"), resultSet.getInt("EntitySubtypeId"), resultSet.getString("TextKey"), resultSet.getString("LocalizedName"), resultSet.getString("LocalizedDescription"), resultSet.getString("LocalizedAbbreviation"), resultSet.getString("LocalizedInformation"), resultSet.getString("LocalizedPlaceholder"), resultSet.getBoolean("IsLocalizable"), resultSet.getBoolean("IsToBeAssociatedWithEachEntityTypeDefinition"), resultSet.getInt("GeneralizedDataTypeEntitySubtypeId"), resultSet.getInt("DataSizeOrMaximumLengthInBytesOrCharacters"), resultSet.getInt("DataPrecision"), resultSet.getInt("DataScale"), resultSet.getInt("KeyTypeEntitySubtypeId"), resultSet.getInt("RelatedEntityTypeId"), resultSet.getInt("RelatedEntityTypeAttributeId"), resultSet.getInt("RelatedEntityTypeCardinalityEntitySubtypeId"), resultSet.getString("EntitySubtypeGroupKey"), resultSet.getInt("ValueEntitySubtypeId"), resultSet.getString("DefaultValue"), resultSet.getString("MinimumValue"), resultSet.getString("MaximumValue"), resultSet.getString("RegExValidationPattern"), resultSet.getFloat("StepIncrementValue"), resultSet.getString("RemoteValidationMethodAsAjaxUri"), resultSet.getInt("IndexEntitySubtypeId"), resultSet.getInt("UniquenessEntitySubtypeId"), resultSet.getInt("SensitivityEntitySubtypeId"), resultSet.getTimestamp("PublishedAtDateTimeUtc"), resultSet.getInt("PublishedByInformationSystemUserId"), resultSet.getInt("Ordinal"), resultSet.getBoolean("IsActive"), resultSet.getTimestamp("CreatedAtDateTimeUtc"), resultSet.getInt("CreatedByInformationSystemUserId"), resultSet.getTimestamp("UpdatedAtDateTimeUtc"), resultSet.getInt("UpdatedByInformationSystemUserId"), resultSet.getTimestamp("DeletedAtDateTimeUtc"), resultSet.getInt("DeletedByInformationSystemUserId")));
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

    @Operation(summary = "Create a new, unpublished business entity definition in the AafCore database, following all AAF rules and conventions.", description = "Create a new, unpublished business entity definition in the AafCore database, following all AAF rules and conventions, by providing the valid, required data and any valid, optional data as a JSON Web Token (JWT, please see https://jwt.io/) in the HTTP request body")
    @Parameter(in = ParameterIn.HEADER, description = "API key", name = "ApiKey", content = @Content(schema = @Schema(type = "string")))
    @Parameter(in = ParameterIn.HEADER, description = "Correlation UUID", name = "CorrelationUuid", content = @Content(schema = @Schema(type = "string")), required = false)
    @Parameter(in = ParameterIn.COOKIE, description = "JWT Authentication token", name = "Authentication", content = @Content(schema = @Schema(type = "string")))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @CrossOrigin(originPatterns = "http://*:[*],https://*:[*]", methods = RequestMethod.POST, allowCredentials = "true")
    @PostMapping(value = "/entityTypeDefinitions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> CreateNewBusinessEntity(@RequestParam(defaultValue = "#{T(java.time.Instant).now()}") Instant asOfDateTimeUtc, @RequestBody String requestBody, ServerWebExchange exchange) throws Exception
    {
        ServerHttpRequest request = null;

        String[] sqlBlacklistValues = null;
        String errorValues = "";

        String apiKey = "";
        String correlationUuid = "";

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
        String createActionRequestBody = "";
        HttpComponentsClientHttpRequestFactory requestFactory = null;
//        RestTemplateBuilder restTemplateBuilder = null;
        RestTemplate restTemplate = null;
        HttpEntity<String> entity = null;
        ResponseEntity<String> createActionResponseBody = null;

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

            //NOTE: No direct database structure changes are necessary for this operation, which calls the EDM PostBusinessEntity method, etc, so no database connection is required

            ENVIRONMENT_JWT_SHARED_SECRET = environment.getProperty("environmentJwtSharedSecret");

            apiKey = exchange.getRequest().getHeaders().getFirst("ApiKey");

            if ((apiKey == null) || (apiKey.length() < 1))
            {
                throw new Exception("No 'ApiKey' header included in the request");
            }
            else
            {
                //TODO: AAF-66 Validate API key by looking it up in the database, ensuring that it is not disabled, checking its associated permissions extent, and (later) checking that it is associated with the authenticated user's OrganizationalUnit
                logger.info(("ApiKey header '" + apiKey + "' included in the request"));
            }

            correlationUuid = exchange.getRequest().getHeaders().getFirst("CorrelationUuid");

            if ((correlationUuid == null) || (correlationUuid.length() < 1))
            {
                correlationUuid = UUID.randomUUID().toString();
                logger.info(("CorrelationUuid '" + correlationUuid + "' generated for the request"));
            }
            else
            {
                //TODO: AAF-66 Validate API key by looking it up in the database, ensuring that it is not disabled, checking its associated permissions extent, and (later) checking that it is associated with the authenticated user's OrganizationalUnit
                logger.info(("CorrelationUuid '" + correlationUuid + "' included in the request"));
            }

            objectMapper = new ObjectMapper();

            //TODO: AAF-67 Validate JWT
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

            //TODO: AAF-68 Look up InformationSystemUser.Id using the authenticated user's EmailAddress in the Authentication JWT payload, and assign it below
            userId = -100L;

            //TODO: AAF-69 Check user's role(s) and permissions for this operation

            //NOTE: Example request http://localhost:8080/Person with "Authentication" JWT and JWT request body
            //NOTE: https://learning.postman.com/docs/sending-requests/response-data/cookies/
//            ApiKey: ???
//            Authentication JWT: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0ZWQiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiJlZjRhZjRlMy1lNzM2LTQyNWEtYWFmZi1lY2EwM2I3YjliMjgiLCJib2R5Ijp7IkVtYWlsQWRkcmVzcyI6ImFteS5hbmRlcnNvbkBhbXlzYWNjb3VudGluZy5jb20ifX0.Djq5LYPEK1QFgBk9aN5Vei37K6Cb8TxNH3ADWDcUaHs
//            Request JWT: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IlBPU1QgL0VudGl0eVR5cGUiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiI4NzUyZjIzYi0xYTliLTQyMmEtOGIyNi0zNzQyNDM0ZGY0NzYiLCJib2R5Ijp7IkVudGl0eVN1YnR5cGVJZCI6LTEsIlRleHRLZXkiOiJwZXJzb24tbm9uZS1iaWxsLWJha2VyIiwiTGVnYWxHaXZlbk5hbWUiOiJCaWxsIiwiTGVnYWxTdXJuYW1lIjoiQmFrZXIiLCJCb3JuQXREYXRlVGltZVV0YyI6IjIwMDItMDItMDMgMTE6MTI6MTMuMTIzIiwiTGVnYWxDaXRpemVuT2ZDb3VudHJ5R2VvZ3JhcGhpY1VuaXRJZCI6MSwiTG9jYWxlSWQiOjEsIk9yZGluYWwiOi0xLCJJc0FjdGl2ZSI6dHJ1ZX19.rWNowmEoPkF8N0Q5KC5-W83g3hMqIf9TV8KHzLgNbio

            //TODO: AAF-67 Validate JWT
//            bodyJwtSections = requestBody.split("\\.");
//            bodyJwtHeader = objectMapper.readTree(decoderBase64.decode(bodyJwtSections[0]));
//            bodyJwtPayload = objectMapper.readTree(decoderBase64.decode(bodyJwtSections[1]));
            //TODO: Validate JWT signature per https://www.baeldung.com/java-jwt-token-decode
            bodyJwtPayload = objectMapper.readTree(requestBody);

            if (bodyJwtPayload != null)
            {
                //NOTE: If both the CorrelationUuid header value and the JWT jti claim are present, warn.  One or the other should be used.
                if ((exchange.getRequest().getHeaders().getFirst("CorrelationUuid") != null) && (bodyJwtPayload.has("body")))
                {
                    logger.warn("Request contains both both the CorrelationUuid header value and the JWT 'jti' claim.  Only one should be used.");
                }

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
                    ((ObjectNode) bodyJwtPayload).put("jti", correlationUuid);
                    ((ObjectNode) bodyJwtPayload).put("body", objectMapper.readTree(requestBody));

                    logger.info(("Request from '" + bodyJwtPayload.get("iss").asText() + "' for '" + bodyJwtPayload.get("aud").asText() + "' using key '" + authenticationJwtHeader.get("kid").asText() + "'"));
                }
            }
            else
            {
                throw new Exception("Missing or invalid request body");
            }

//            sqlBlacklistValues = environment.getProperty("sqlNotToAllow").toLowerCase().split(",");
            //TODO: AAF-84 Only check request body for SQL injection
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
                    //TODO: AAF-78 Add a method to generate a valid TextKey value for any EntityTypeDefinition, and call it here
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
                headers.add("CorrelationUuid", correlationUuid);
                headers.add("Cookie", authenticationJwt.toString());

                //TODO: AAF-86 Figure out appropriate VersionTag, DataLocationEntitySubtypeId, and DataStructureEntitySubtypeId values
                createActionRequestBody = "{\n" +
                        "    \"EntitySubtypeId\": " + bodyJwtPayload.get("body").get("EntitySubtypeId").asLong() + ",\n" +
                        "    \"TextKey\": \"" + textKey + "\",\n" +
                        "    \"LocalizedName\": \"" + bodyJwtPayload.get("body").get("LocalizedName").asText() + "\",\n" +
                        "    \"LocalizedDescription\": \"" + bodyJwtPayload.get("body").get("LocalizedDescription").asText() + "\",\n" +
                        "    \"LocalizedAbbreviation\": \"" + bodyJwtPayload.get("body").get("LocalizedAbbreviation").asText() + "\",\n" +
                        "    \"VersionTag\": \"" + "000.000.000" + "\",\n" +
                        "    \"DataLocationEntitySubtypeId\": " + "4" + ",\n" +
                        "    \"DataStructureEntitySubtypeId\": " + "1" + ",\n" +
                        "    \"PublishedAtDateTimeUtc\": \"9999-12-31T23:59:59.999Z\",\n" +
                        "    \"PublishedByInformationSystemUserId\": " + "-1" + ",\n" +
                        "    \"Ordinal\": " + ordinal + ",\n" +
                        "    \"IsActive\": " + isActive + ",\n" +
                        "    \"CorrelationUuid\": \"" + correlationUuid + "\"\n" +
                        "  }";

                entity = new HttpEntity<String>(createActionRequestBody, headers);

                //TODO: AAF-87 Implement fixes for RestTemplateBuilder
//                restTemplateBuilder = new RestTemplateBuilder();
//                restTemplate = restTemplate;

                restTemplate = new RestTemplate();
                requestFactory = new HttpComponentsClientHttpRequestFactory();
//                requestFactory.setConnectTimeout(TIMEOUT);
//                requestFactory.setReadTimeout(TIMEOUT);
                restTemplate.setRequestFactory(requestFactory);

                createActionResponseBody = restTemplate.postForEntity("http://localhost:8080/entityTypes/EntityTypeDefinition", entity, String.class);

                //TODO: AAF-88 Check response body for success and to get Id instead of all the parsing below

                entityIdStart = createActionResponseBody.getBody().indexOf("\"Id\":") + 5;
                System.out.println("entityIdStart: " + entityIdStart);

                entityIdEnd = createActionResponseBody.getBody().indexOf(",", entityIdStart);
                System.out.println("entityIdEnd: " + entityIdEnd);

                entityId = Long.parseLong(createActionResponseBody.getBody().substring(entityIdStart, entityIdEnd));
                System.out.println("entityId: " + entityId);

                //TODO: AAF-89 Add AsOfDataTimeUtc and result text to standard response structure here and in EDM
                //TODO: AAF-82 Echo input parameters in Postgres function return JSON
                entityData = objectMapper.readTree(createActionResponseBody.getBody()).toString();

                for (int i = 0 ; i < entityTypeAttributes.size() ; i++)
                {
                    if (GetCachedEntityTypeAttributeById(entityTypeAttributes.get(i).getId()).isIsToBeAssociatedWithEachEntityTypeDefinition())
                    {
                        createActionRequestBody = "{\n" +
                                "    \"EntitySubtypeId\": 0,\n" +
                                "    \"TextKey\": \"entitytypedefinitionentitytypeattributeassociation-" + bodyJwtPayload.get("body").get("LocalizedName").asText().toLowerCase() + "-" + GetCachedEntityTypeAttributeById(entityTypeAttributes.get(i).getId()).getLocalizedName().toLowerCase() + "\",\n" +
                                "    \"EntityTypeDefinitionId\": " + entityId + ",\n" +
                                "    \"EntityTypeAttributeId\": " + entityTypeAttributes.get(i).getId() + ",\n" +
                                "    \"PublishedAtDateTimeUtc\": \"9999-12-31T23:59:59.999Z\",\n" +
                                "    \"PublishedByInformationSystemUserId\": " + "-1" + ",\n" +
                                "    \"Ordinal\": " + entityTypeAttributes.get(i).getOrdinal() + ",\n" +
                                "    \"CorrelationUuid\": \"" + correlationUuid + "\"\n" +
                                "  }";

                        entity = new HttpEntity<String>(createActionRequestBody, headers);

                        createActionResponseBody = restTemplate.postForEntity("http://localhost:8080/entityTypes/EntityTypeDefinitionEntityTypeAttributeAssociation", entity, String.class);
                        //TODO: AAF-90 Check response body for success
                    }
                }

                //TODO: AAF-91 Confirm all required EntityTypeAttributes are associated with the new EntityTypeDefinition
                UncacheEntityData();
                CacheEntityData();
                //TODO: AAF-92 Re-cache EDM data

                //TODO: AAF-93 Consider converting this to a BPMN process when possible
            }
            //TODO: Else/exception here (and in EDM?) if dangerous SQL found???
        }
        catch (Exception e)
        {
            logger.error("CreateNewBusinessEntity() failed due to: " + e);
            return new ResponseEntity<String>("{\"EntityType\" : \"" + bodyJwtPayload.get("body").get("LocalizedName").asText() + "\", \"TotalRows\": -1, \"EntityData\": [], \"Code\": 500, \"Message\": \"" + e.toString() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("CreateNewBusinessEntity() succeeded");
        return new ResponseEntity<String>(entityData, HttpStatus.CREATED);
    }

    @Operation(summary = "Clone an existing business entity definition as the starting point for a new, unpublished business entity definition in the AafCore database, following all AAF rules and conventions.", description = "Clone an existing business entity definition in the AafCore database, following all AAF rules and conventions, by providing the valid, required data and any valid, optional data as a JSON Web Token (JWT, please see https://jwt.io/) in the HTTP request body")
    @Parameter(in = ParameterIn.HEADER, description = "API key", name = "ApiKey", content = @Content(schema = @Schema(type = "string")))
    @Parameter(in = ParameterIn.HEADER, description = "Correlation UUID", name = "CorrelationUuid", content = @Content(schema = @Schema(type = "string")), required = false)
    @Parameter(in = ParameterIn.COOKIE, description = "JWT Authentication token", name = "Authentication", content = @Content(schema = @Schema(type = "string")))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @CrossOrigin(originPatterns = "http://*:[*],https://*:[*]", methods = RequestMethod.POST, allowCredentials = "true")
    @PostMapping(value = "/entityTypeDefinitions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> CloneExistingBusinessEntity(@PathVariable("id") Long id, @RequestParam(defaultValue = "#{T(java.time.Instant).now()}") Instant asOfDateTimeUtc, @RequestBody String requestBody, ServerWebExchange exchange) throws Exception
    {
        ServerHttpRequest request = null;

        String[] sqlBlacklistValues = null;
        String errorValues = "";

        String apiKey = "";
        String correlationUuid = "";

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
        HttpComponentsClientHttpRequestFactory requestFactory = null;
//        RestTemplateBuilder restTemplateBuilder = null;
        RestTemplate restTemplate = null;
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

            //NOTE: No direct database structure changes are necessary for this operation, which calls the EDM PostBusinessEntity method, etc, so no database connection is required

            ENVIRONMENT_JWT_SHARED_SECRET = environment.getProperty("environmentJwtSharedSecret");

            apiKey = exchange.getRequest().getHeaders().getFirst("ApiKey");

            if ((apiKey == null) || (apiKey.length() < 1))
            {
                throw new Exception("No 'ApiKey' header included in the request");
            }
            else
            {
                //TODO: AAF-66 Validate API key by looking it up in the database, ensuring that it is not disabled, checking its associated permissions extent, and (later) checking that it is associated with the authenticated user's OrganizationalUnit
                logger.info(("ApiKey header '" + apiKey + "' included in the request"));
            }

            correlationUuid = exchange.getRequest().getHeaders().getFirst("CorrelationUuid");

            if ((correlationUuid == null) || (correlationUuid.length() < 1))
            {
                correlationUuid = UUID.randomUUID().toString();
                logger.info(("CorrelationUuid '" + correlationUuid + "' generated for the request"));
            }
            else
            {
                //TODO: AAF-66 Validate API key by looking it up in the database, ensuring that it is not disabled, checking its associated permissions extent, and (later) checking that it is associated with the authenticated user's OrganizationalUnit
                logger.info(("CorrelationUuid '" + correlationUuid + "' included in the request"));
            }

            objectMapper = new ObjectMapper();

            //TODO: AAF-67 Validate JWT
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

            //TODO: AAF-68 Look up InformationSystemUser.Id using the authenticated user's EmailAddress in the Authentication JWT payload, and assign it below
            userId = -100L;

            //TODO: AAF-69 Check user's role(s) and permissions for this operation

            //NOTE: Example request http://localhost:8080/Person with "Authentication" JWT and JWT request body
            //NOTE: https://learning.postman.com/docs/sending-requests/response-data/cookies/
//            ApiKey: ???
//            Authentication JWT: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0ZWQiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiJlZjRhZjRlMy1lNzM2LTQyNWEtYWFmZi1lY2EwM2I3YjliMjgiLCJib2R5Ijp7IkVtYWlsQWRkcmVzcyI6ImFteS5hbmRlcnNvbkBhbXlzYWNjb3VudGluZy5jb20ifX0.Djq5LYPEK1QFgBk9aN5Vei37K6Cb8TxNH3ADWDcUaHs
//            Request JWT: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IlBPU1QgL0VudGl0eVR5cGUiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiI4NzUyZjIzYi0xYTliLTQyMmEtOGIyNi0zNzQyNDM0ZGY0NzYiLCJib2R5Ijp7IkVudGl0eVN1YnR5cGVJZCI6LTEsIlRleHRLZXkiOiJwZXJzb24tbm9uZS1iaWxsLWJha2VyIiwiTGVnYWxHaXZlbk5hbWUiOiJCaWxsIiwiTGVnYWxTdXJuYW1lIjoiQmFrZXIiLCJCb3JuQXREYXRlVGltZVV0YyI6IjIwMDItMDItMDMgMTE6MTI6MTMuMTIzIiwiTGVnYWxDaXRpemVuT2ZDb3VudHJ5R2VvZ3JhcGhpY1VuaXRJZCI6MSwiTG9jYWxlSWQiOjEsIk9yZGluYWwiOi0xLCJJc0FjdGl2ZSI6dHJ1ZX19.rWNowmEoPkF8N0Q5KC5-W83g3hMqIf9TV8KHzLgNbio

            //TODO: AAF-67 Validate JWT
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
                    ((ObjectNode) bodyJwtPayload).put("jti", correlationUuid);
                    ((ObjectNode) bodyJwtPayload).put("body", objectMapper.readTree(requestBody));

                    logger.info(("Request from '" + bodyJwtPayload.get("iss").asText() + "' for '" + bodyJwtPayload.get("aud").asText() + "' using key '" + authenticationJwtHeader.get("kid").asText() + "'"));
                }
            }
            else
            {
                throw new Exception("Missing or invalid request body");
            }

//            sqlBlacklistValues = environment.getProperty("sqlNotToAllow").toLowerCase().split(",");
            //TODO: AAF-84 Only check request body for SQL injection
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
                    //TODO: AAF-78 Add a method to generate a valid TextKey value for any EntityTypeDefinition, and call it here
                    textKey = "entitytypedefinition-" + GetCachedEntitySubtypeById(bodyJwtPayload.get("body").get("EntitySubtypeId").asLong()).getLocalizedName().toLowerCase() + "-" + bodyJwtPayload.get("body").get("LocalizedName").asText().toLowerCase() + "-" + GenerateRandomLowercaseAlphanumericString(5);
                }

                //NOTE: Get optional Ordinal value in request body or supply default value
                if (bodyJwtPayload.get("body").has("Ordinal"))
                {
                    ordinal = bodyJwtPayload.get("body").get("Ordinal").asLong();
                }
                else
                {
                    //TODO: Determine a safe default value for Ordinal that places it last and that will not conflict with existing values
                    ordinal = 1000L;
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
                headers.add("CorrelationUuid", correlationUuid);
                headers.add("Cookie", authenticationJwt.toString());

                //TODO: AAF-86  VersionTag, DataLocationEntitySubtypeId, and DataStructureEntitySubtypeId values
                cloneActionRequestBody = "{\n" +
                        "    \"EntitySubtypeId\": " + cachedEntityTypeDefinition.getEntitySubtypeId() + ",\n" +
                        "    \"TextKey\": \"" + textKey + "\",\n" +
                        "    \"LocalizedName\": \"" + bodyJwtPayload.get("body").get("LocalizedName").asText() + "\",\n" +
                        "    \"LocalizedDescription\": \"" + bodyJwtPayload.get("body").get("LocalizedDescription").asText() + "\",\n" +
                        "    \"LocalizedAbbreviation\": \"" + bodyJwtPayload.get("body").get("LocalizedAbbreviation").asText() + "\",\n" +
                        "    \"VersionTag\": \"" + "000.000.000" + "\",\n" +
                        "    \"DataLocationEntitySubtypeId\": " + "4" + ",\n" +
                        "    \"DataStructureEntitySubtypeId\": " + "1" + ",\n" +
                        "    \"PublishedAtDateTimeUtc\": \"9999-12-31T23:59:59.999Z\",\n" +
                        "    \"PublishedByInformationSystemUserId\": " + "-1" + ",\n" +
                        //NOTE: Cloning existing Ordinal and IsActive values could lead to confusion since existing sort order might not be appropriate for the newly cloned entity and entities cloned from inactive entities would not be visible by default, so they are specified or defaulted, not copied.
                        "    \"Ordinal\": " + ordinal + ",\n" +
                        "    \"IsActive\": " + isActive + ",\n" +
                        "    \"CorrelationUuid\": \"" + correlationUuid + "\"\n" +
                        "  }";

                entity = new HttpEntity<String>(cloneActionRequestBody, headers);

                //TODO: AAF-87 Implement fixes for RestTemplateBuilder
//                restTemplateBuilder = new RestTemplateBuilder();
//                restTemplate = restTemplate;

                restTemplate = new RestTemplate();
                requestFactory = new HttpComponentsClientHttpRequestFactory();
//                requestFactory.setConnectTimeout(TIMEOUT);
//                requestFactory.setReadTimeout(TIMEOUT);
                restTemplate.setRequestFactory(requestFactory);

                cloneActionResponseBody = restTemplate.postForEntity("http://localhost:8080/entityTypes/EntityTypeDefinition", entity, String.class);

                //TODO: AAF-88 Check response body for success and to get Id instead of all the parsing below

                entityIdStart = cloneActionResponseBody.getBody().indexOf("\"Id\":") + 5;
                System.out.println("entityIdStart: " + entityIdStart);

                entityIdEnd = cloneActionResponseBody.getBody().indexOf(",", entityIdStart);
                System.out.println("entityIdEnd: " + entityIdEnd);

                entityId = Long.parseLong(cloneActionResponseBody.getBody().substring(entityIdStart, entityIdEnd));
                System.out.println("entityId: " + entityId);

                //TODO: AAF-89 Add AsOfDataTimeUtc and result text to standard response structure here and in EDM
                //TODO: AAF-82 Echo input parameters in Postgres function return JSON
                entityData = objectMapper.readTree(cloneActionResponseBody.getBody()).toString();

                //NOTE: Insert new, unpublished EntityTypeDefinitionEntityTypeAttributeAssociations that match the specified EntityTypeDefinition's EntityTypeDefinitionEntityTypeAttributeAssociations
                for (int i = 0 ; i < entityTypeDefinitionEntityTypeAttributeAssociations.size() ; i++)
                {
                    if (entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getEntityTypeDefinitionId() == cachedEntityTypeDefinition.getId())
                    {
                        cloneActionRequestBody = "{\n" +
                                "    \"EntitySubtypeId\": 0,\n" +
                                "    \"TextKey\": \"entitytypedefinitionentitytypeattributeassociation-" + bodyJwtPayload.get("body").get("LocalizedName").asText().toLowerCase() + "-" + GetCachedEntityTypeAttributeById(entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getEntityTypeAttributeId()).getLocalizedName().toLowerCase() + "\",\n" +
                                "    \"EntityTypeDefinitionId\": " + entityId + ",\n" +
                                "    \"EntityTypeAttributeId\": " + entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getEntityTypeAttributeId() + ",\n" +
                                "    \"PublishedAtDateTimeUtc\": \"9999-12-31T23:59:59.999Z\",\n" +
                                "    \"PublishedByInformationSystemUserId\": " + "-1" + ",\n" +
                                "    \"Ordinal\": " + entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getOrdinal() + ",\n" +
                                "    \"CorrelationUuid\": \"" + correlationUuid + "\"\n" +
                                "  }";

                        entity = new HttpEntity<String>(cloneActionRequestBody, headers);

                        cloneActionResponseBody = restTemplate.postForEntity("http://localhost:8080/entityTypes/EntityTypeDefinitionEntityTypeAttributeAssociation", entity, String.class);
                        //TODO: * AAF-90 Check response body for success
                    }
                }

                //TODO: AAF-91 Confirm all required EntityTypeAttributes are associated with the new EntityTypeDefinition
                UncacheEntityData();
                CacheEntityData();
                //TODO: AAF-92 Re-cache EDM data

                //TODO: AAF-93 Consider converting this to a BPMN process when possible
            }
            //TODO: Else/exception here (and in EDM?) if dangerous SQL found???
        }
        catch (Exception e)
        {
            logger.error("CloneExistingBusinessEntity() failed due to: " + e);
            return new ResponseEntity<String>("{\"EntityType\" : \"" + bodyJwtPayload.get("body").get("LocalizedName").asText() + "\", \"TotalRows\": -1, \"EntityData\": [], \"Code\": 500, \"Message\": \"" + e.toString() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("CloneExistingBusinessEntity() succeeded");
        return new ResponseEntity<String>(entityData, HttpStatus.CREATED);
    }

    @Operation(summary = "Clone an existing business entity attribute as the starting point for a new, unpublished business entity attribute in the AafCore database, following all AAF rules and conventions.", description = "Clone an existing business entity attribute in the AafCore database, following all AAF rules and conventions, by providing the valid, required data and any valid, optional data as a JSON Web Token (JWT, please see https://jwt.io/) in the HTTP request body")
    @Parameter(in = ParameterIn.HEADER, description = "API key", name = "ApiKey", content = @Content(schema = @Schema(type = "string")))
    @Parameter(in = ParameterIn.HEADER, description = "Correlation UUID", name = "CorrelationUuid", content = @Content(schema = @Schema(type = "string")), required = false)
    @Parameter(in = ParameterIn.COOKIE, description = "JWT Authentication token", name = "Authentication", content = @Content(schema = @Schema(type = "string")))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @CrossOrigin(originPatterns = "http://*:[*],https://*:[*]", methods = RequestMethod.POST, allowCredentials = "true")
    @PostMapping(value = "/entityTypeAttributes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> CloneExistingEntityAttribute(@PathVariable("id") Long id, @RequestParam(defaultValue = "#{T(java.time.Instant).now()}") Instant asOfDateTimeUtc, @RequestBody String requestBody, ServerWebExchange exchange) throws Exception
    {
        ServerHttpRequest request = null;

        String[] sqlBlacklistValues = null;
        String errorValues = "";

        String apiKey = "";
        String correlationUuid = "";

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
        EntityTypeAttribute cachedEntityTypeAttribute = null;
        String cloneActionRequestBody = "";
        HttpComponentsClientHttpRequestFactory requestFactory = null;
//        RestTemplateBuilder restTemplateBuilder = null;
        RestTemplate restTemplate = null;
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
            logger.info("Attempting to CloneExistingEntityAttribute() for " + id + " (" + GetCachedEntityTypeAttributeById(id).getLocalizedName() + ")");

            request = exchange.getRequest();

            //NOTE: No direct database structure changes are necessary for this operation, which calls the EDM PostBusinessEntity method, etc, so no database connection is required

            ENVIRONMENT_JWT_SHARED_SECRET = environment.getProperty("environmentJwtSharedSecret");

            apiKey = exchange.getRequest().getHeaders().getFirst("ApiKey");

            if ((apiKey == null) || (apiKey.length() < 1))
            {
                throw new Exception("No 'ApiKey' header included in the request");
            }
            else
            {
                //TODO: AAF-66 Validate API key by looking it up in the database, ensuring that it is not disabled, checking its associated permissions extent, and (later) checking that it is associated with the authenticated user's OrganizationalUnit
                logger.info(("ApiKey header '" + apiKey + "' included in the request"));
            }

            correlationUuid = exchange.getRequest().getHeaders().getFirst("CorrelationUuid");

            if ((correlationUuid == null) || (correlationUuid.length() < 1))
            {
                correlationUuid = UUID.randomUUID().toString();
                logger.info(("CorrelationUuid '" + correlationUuid + "' generated for the request"));
            }
            else
            {
                //TODO: AAF-66 Validate API key by looking it up in the database, ensuring that it is not disabled, checking its associated permissions extent, and (later) checking that it is associated with the authenticated user's OrganizationalUnit
                logger.info(("CorrelationUuid '" + correlationUuid + "' included in the request"));
            }

            objectMapper = new ObjectMapper();

            //TODO: AAF-67 Validate JWT
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

            //TODO: AAF-68 Look up InformationSystemUser.Id using the authenticated user's EmailAddress in the Authentication JWT payload, and assign it below
            userId = -100L;

            //TODO: AAF-69 Check user's role(s) and permissions for this operation

            //NOTE: Example request http://localhost:8080/Person with "Authentication" JWT and JWT request body
            //NOTE: https://learning.postman.com/docs/sending-requests/response-data/cookies/
//            ApiKey: ???
//            Authentication JWT: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0ZWQiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiJlZjRhZjRlMy1lNzM2LTQyNWEtYWFmZi1lY2EwM2I3YjliMjgiLCJib2R5Ijp7IkVtYWlsQWRkcmVzcyI6ImFteS5hbmRlcnNvbkBhbXlzYWNjb3VudGluZy5jb20ifX0.Djq5LYPEK1QFgBk9aN5Vei37K6Cb8TxNH3ADWDcUaHs
//            Request JWT: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IlBPU1QgL0VudGl0eVR5cGUiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiI4NzUyZjIzYi0xYTliLTQyMmEtOGIyNi0zNzQyNDM0ZGY0NzYiLCJib2R5Ijp7IkVudGl0eVN1YnR5cGVJZCI6LTEsIlRleHRLZXkiOiJwZXJzb24tbm9uZS1iaWxsLWJha2VyIiwiTGVnYWxHaXZlbk5hbWUiOiJCaWxsIiwiTGVnYWxTdXJuYW1lIjoiQmFrZXIiLCJCb3JuQXREYXRlVGltZVV0YyI6IjIwMDItMDItMDMgMTE6MTI6MTMuMTIzIiwiTGVnYWxDaXRpemVuT2ZDb3VudHJ5R2VvZ3JhcGhpY1VuaXRJZCI6MSwiTG9jYWxlSWQiOjEsIk9yZGluYWwiOi0xLCJJc0FjdGl2ZSI6dHJ1ZX19.rWNowmEoPkF8N0Q5KC5-W83g3hMqIf9TV8KHzLgNbio

            //TODO: AAF-67 Validate JWT
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
                    ((ObjectNode) bodyJwtPayload).put("jti", correlationUuid);
                    ((ObjectNode) bodyJwtPayload).put("body", objectMapper.readTree(requestBody));

                    logger.info(("Request from '" + bodyJwtPayload.get("iss").asText() + "' for '" + bodyJwtPayload.get("aud").asText() + "' using key '" + authenticationJwtHeader.get("kid").asText() + "'"));
                }
            }
            else
            {
                throw new Exception("Missing or invalid request body");
            }

//            sqlBlacklistValues = environment.getProperty("sqlNotToAllow").toLowerCase().split(",");
            //TODO: AAF-84 Only check request body for SQL injection
//            errorValues = GuardAgainstSqlIssues(bodyJwtPayload.toString(), sqlBlacklistValues);

            if (errorValues.length() == 0) {
                //NOTE: Validate newBusinessEntityName in request body
                if (!IsValidEntityOrAttributeName(bodyJwtPayload.get("body").get("LocalizedName").asText()))
                {
                    throw new Exception("Invalid EntityTypeDefinition name '" + bodyJwtPayload.get("body").get("LocalizedName").asText() + "'");
                }

                //NOTE: Optimistically (and consistently) not checking maximum length of newEntityAttributeName, newEntityAttributeDescription, or newEntityAttributeAbbreviation because the database constraints will catch it

                cachedEntityTypeAttribute = GetCachedEntityTypeAttributeById(id);

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
                    //TODO: AAF-78 Add a method to generate a valid TextKey value for any EntityTypeDefinition, and call it here
                    textKey = "entitytypeattribute-" + GetCachedEntitySubtypeById(bodyJwtPayload.get("body").get("EntitySubtypeId").asLong()).getLocalizedName().toLowerCase() + "-" + bodyJwtPayload.get("body").get("LocalizedName").asText().toLowerCase() + "-" + GenerateRandomLowercaseAlphanumericString(5);
                }

                //NOTE: Get optional Ordinal value in request body or supply default value
                if (bodyJwtPayload.get("body").has("Ordinal"))
                {
                    ordinal = bodyJwtPayload.get("body").get("Ordinal").asLong();
                }
                else
                {
                    //TODO: Determine a safe default value for Ordinal that places it last and that will not conflict with existing values
                    ordinal = 1000L;
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
                headers.add("CorrelationUuid", correlationUuid);
                headers.add("Cookie", authenticationJwt.toString());

                cloneActionRequestBody = "{\n" +
                        "    \"EntitySubtypeId\": " + cachedEntityTypeAttribute.getEntitySubtypeId() + ",\n" +
                        "    \"TextKey\": \"" + textKey + "\",\n" +
                        "    \"LocalizedName\": \"" + bodyJwtPayload.get("body").get("LocalizedName").asText() + "\",\n" +
                        "    \"LocalizedDescription\": \"" + bodyJwtPayload.get("body").get("LocalizedDescription").asText() + "\",\n" +
                        "    \"LocalizedAbbreviation\": \"" + bodyJwtPayload.get("body").get("LocalizedAbbreviation").asText() + "\",\n" +
                        "    \"LocalizedInformation\": \"" + bodyJwtPayload.get("body").get("LocalizedInformation").asText() + "\",\n" +
                        "    \"LocalizedPlaceholder\": \"" + bodyJwtPayload.get("body").get("LocalizedPlaceholder").asText() + "\",\n" +
                        "    \"IsLocalizable\": " + cachedEntityTypeAttribute.isIsLocalizable() + ",\n" +
                        "    \"IsToBeAssociatedWithEachEntityTypeDefinition\": " + cachedEntityTypeAttribute.isIsToBeAssociatedWithEachEntityTypeDefinition() + ",\n" +
                        "    \"GeneralizedDataTypeEntitySubtypeId\": " + cachedEntityTypeAttribute.getGeneralizedDataTypeEntitySubtypeId() + ",\n" +
                        "    \"DataSizeOrMaximumLengthInBytesOrCharacters\": " + cachedEntityTypeAttribute.getDataSizeOrMaximumLengthInBytesOrCharacters() + ",\n" +
                        "    \"DataPrecision\": " + cachedEntityTypeAttribute.getDataPrecision() + ",\n" +
                        "    \"DataScale\": " + cachedEntityTypeAttribute.getDataScale() + ",\n" +
                        "    \"KeyTypeEntitySubtypeId\": " + cachedEntityTypeAttribute.getKeyTypeEntitySubtypeId() + ",\n" +
                        "    \"RelatedEntityTypeId\": " + cachedEntityTypeAttribute.getRelatedEntityTypeId() + ",\n" +
                        "    \"RelatedEntityTypeAttributeId\": " + cachedEntityTypeAttribute.getRelatedEntityTypeAttributeId() + ",\n" +
                        "    \"RelatedEntityTypeCardinalityEntitySubtypeId\": " + cachedEntityTypeAttribute.getRelatedEntityTypeCardinalityEntitySubtypeId() + ",\n" +
                        "    \"EntitySubtypeGroupKey\": \"" + cachedEntityTypeAttribute.getEntitySubtypeGroupKey() + "\",\n" +
                        "    \"ValueEntitySubtypeId\": " + cachedEntityTypeAttribute.getValueEntitySubtypeId() + ",\n" +
                        "    \"DefaultValue\": \"" + cachedEntityTypeAttribute.getDefaultValue() + "\",\n" +
                        "    \"MinimumValue\": \"" + cachedEntityTypeAttribute.getMinimumValue() + "\",\n" +
                        "    \"MaximumValue\": \"" + cachedEntityTypeAttribute.getMaximumValue() + "\",\n" +
                        "    \"RegExValidationPattern\": \"" + cachedEntityTypeAttribute.getRegExValidationPattern() + "\",\n" +
                        "    \"StepIncrementValue\": " + cachedEntityTypeAttribute.getStepIncrementValue() + ",\n" +
                        "    \"RemoteValidationMethodAsAjaxUri\": \"" + cachedEntityTypeAttribute.getRemoteValidationMethodAsAjaxUri() + "\",\n" +
                        "    \"IndexEntitySubtypeId\": " + cachedEntityTypeAttribute.getIndexEntitySubtypeId() + ",\n" +
                        "    \"UniquenessEntitySubtypeId\": " + cachedEntityTypeAttribute.getUniquenessEntitySubtypeId() + ",\n" +
                        "    \"SensitivityEntitySubtypeId\": " + cachedEntityTypeAttribute.getSensitivityEntitySubtypeId() + ",\n" +
                        "    \"PublishedAtDateTimeUtc\": \"9999-12-31T23:59:59.999Z\",\n" +
                        "    \"PublishedByInformationSystemUserId\": " + "-1" + ",\n" +
                        //NOTE: Cloning existing Ordinal and IsActive values could lead to confusion since existing sort order might not be appropriate for the newly cloned entity and entities cloned from inactive entities would not be visible by default, so they are specified or defaulted, not copied.
                        "    \"Ordinal\": " + ordinal + ",\n" +
                        "    \"IsActive\": " + isActive + ",\n" +
                        "    \"CorrelationUuid\": \"" + correlationUuid + "\"\n" +
                        "  }";

                entity = new HttpEntity<String>(cloneActionRequestBody, headers);

                //TODO: AAF-87 Implement fixes for RestTemplateBuilder
//                restTemplateBuilder = new RestTemplateBuilder();
//                restTemplate = restTemplate;

                restTemplate = new RestTemplate();
                requestFactory = new HttpComponentsClientHttpRequestFactory();
//                requestFactory.setConnectTimeout(TIMEOUT);
//                requestFactory.setReadTimeout(TIMEOUT);
                restTemplate.setRequestFactory(requestFactory);

                cloneActionResponseBody = restTemplate.postForEntity("http://localhost:8080/entityTypes/EntityTypeAttribute", entity, String.class);

                //TODO: AAF-88 Check response body for success and to get Id instead of all the parsing below

                entityIdStart = cloneActionResponseBody.getBody().indexOf("\"Id\":") + 5;
                System.out.println("entityIdStart: " + entityIdStart);

                entityIdEnd = cloneActionResponseBody.getBody().indexOf(",", entityIdStart);
                System.out.println("entityIdEnd: " + entityIdEnd);

                entityId = Long.parseLong(cloneActionResponseBody.getBody().substring(entityIdStart, entityIdEnd));
                System.out.println("entityId: " + entityId);

                //TODO: AAF-89 Add AsOfDataTimeUtc and result text to standard response structure here and in EDM
                //TODO: AAF-82 Echo input parameters in Postgres function return JSON
                entityData = objectMapper.readTree(cloneActionResponseBody.getBody()).toString();

                //TODO: AAF-91 Confirm all required EntityTypeAttributes are associated with the new EntityTypeDefinition
                UncacheEntityData();
                CacheEntityData();
                //TODO: AAF-92 Re-cache EDM data

                //TODO: AAF-93 Consider converting this to a BPMN process when possible
            }
            //TODO: Else/exception here (and in EDM?) if dangerous SQL found???
        }
        catch (Exception e)
        {
            logger.error("CloneExistingEntityAttribute() failed due to: " + e);
            return new ResponseEntity<String>("{\"EntityType\" : \"" + bodyJwtPayload.get("body").get("LocalizedName").asText() + "\", \"TotalRows\": -1, \"EntityData\": [], \"Code\": 500, \"Message\": \"" + e.toString() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("CloneExistingEntityAttribute() succeeded");
        return new ResponseEntity<String>(entityData, HttpStatus.CREATED);
    }

    @Operation(summary = "Create or alter the structure of the AafCore database, including its schemas, entity models (tables), functions, and scripted system/lookup data, e.g. EntityTypeDefinition, EntityTypeAttribute, EntityType, EntitySubtype, etc, that has been defined/scripted by the AafCoreModeler role.", description = "Create or alter the structure of the database by providing the valid, required data and any valid, optional data as a JSON Web Token (JWT, please see https://jwt.io/) in the HTTP request body")
    @Parameter(in = ParameterIn.HEADER, description = "API key", name = "ApiKey", content = @Content(schema = @Schema(type = "string")))
    @Parameter(in = ParameterIn.HEADER, description = "Correlation UUID", name = "CorrelationUuid", content = @Content(schema = @Schema(type = "string")), required = false)
    @Parameter(in = ParameterIn.COOKIE, description = "JWT Authentication token", name = "Authentication", content = @Content(schema = @Schema(type = "string")))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @CrossOrigin(originPatterns = "http://*:[*],https://*:[*]", methods = RequestMethod.POST, allowCredentials = "true")
    @PostMapping(value = "/databases/{databaseName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> PublishBusinessEntitys(@PathVariable("databaseName") String databaseName, @RequestParam(defaultValue = "#{T(java.time.Instant).now()}") Instant asOfDateTimeUtc, @RequestBody String requestBody, ServerWebExchange exchange) throws Exception
    {
        ServerHttpRequest request = null;

//        long entityTypeId = -1;
        ArrayList<EntityTypeDefinition> unpublishedEntityTypeDefinitions = null;
        ArrayList<EntityTypeAttribute> unpublishedEntityTypeAttributes = null;
        ArrayList<EntityTypeDefinitionEntityTypeAttributeAssociation> unpublishedEntityTypeAssociations = null;

        String[] sqlBlacklistValues = null;
        String errorValues = "";

        HttpHeaders headers = null;

        String apiKey = "";
        String correlationUuid = "";

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

        ObjectNode unpublishedEntityData = null;

//        String[] entityTypeAttributesNeverToReturn = null;

        String preparedStatementSql = "";

        DatabaseConnection databaseConnection = null;

        Connection connection = null;
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        EntityTypeDefinitionEntityTypeAttributeAssociation cachedEntityAssociation = null;
        EntityTypeAttribute cachedEntityTypeAttribute = null;

        String publishActionRequestBody = "";
        HttpComponentsClientHttpRequestFactory requestFactory = null;
//        RestTemplateBuilder restTemplateBuilder = null;
        RestTemplate restTemplate = null;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = null;
        HttpEntity<String> entity = null;
        ResponseEntity<String> publishActionResponseBody = null;

        String entityData = "";

        //NOTE: Rather than validating the EntityTypeDefinition name, we're going to optimistically pass it through to the database if it returns attributes
        //NOTE: We don't request versions our business entity data structure explicitly in the base URL; instead our explicit (v1.2.3) versioning is maintained internally, based on/derived from the AsOfUtcDateTime query parameter
        //NOTE: Since we're not automatically parsing the resulting JSON into an object, we're returning a JSON String rather than a JSONObject
        try
        {
            logger.info("Attempting to PublishBusinessEntitys() in " + databaseName);

            request = exchange.getRequest();

            UncacheEntityData();
            CacheEntityData();

            DB_DRIVER_CLASS = "postgresql";
            DB_URL = environment.getProperty("spring.datasource.url");
            DB_USERNAME = environment.getProperty("spring.datasource.username");
            DB_PASSWORD = environment.getProperty("spring.datasource.password");

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
                //TODO: AAF-66 Validate API key by looking it up in the database, ensuring that it is not disabled, checking its associated permissions extent, and (later) checking that it is associated with the authenticated user's OrganizationalUnit
                logger.info(("ApiKey header '" + apiKey + "' included in the request"));
            }

            correlationUuid = exchange.getRequest().getHeaders().getFirst("CorrelationUuid");

            if ((correlationUuid == null) || (correlationUuid.length() < 1))
            {
                correlationUuid = UUID.randomUUID().toString();
                logger.info(("CorrelationUuid '" + correlationUuid + "' generated for the request"));
            }
            else
            {
                //TODO: AAF-66 Validate API key by looking it up in the database, ensuring that it is not disabled, checking its associated permissions extent, and (later) checking that it is associated with the authenticated user's OrganizationalUnit
                logger.info(("CorrelationUuid '" + correlationUuid + "' included in the request"));
            }

            objectMapper = new ObjectMapper();

            //TODO: AAF-67 Validate JWT
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

            //TODO: AAF-68 Look up InformationSystemUser.Id using the authenticated user's EmailAddress in the Authentication JWT payload, and assign it below
            userId = -100L;

            //TODO: AAF-69 Check user's role(s) and permissions for this operation

            //NOTE: Example request http://localhost:8080/Person with "Authentication" JWT and JWT request body
            //NOTE: https://learning.postman.com/docs/sending-requests/response-data/cookies/
//            Authentication JWT: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0ZWQiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiJlZjRhZjRlMy1lNzM2LTQyNWEtYWFmZi1lY2EwM2I3YjliMjgiLCJib2R5Ijp7IkVtYWlsQWRkcmVzcyI6ImFteS5hbmRlcnNvbkBhbXlzYWNjb3VudGluZy5jb20ifX0.Djq5LYPEK1QFgBk9aN5Vei37K6Cb8TxNH3ADWDcUaHs
//            Request JWT: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IlBPU1QgL0VudGl0eVR5cGUiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiI4NzUyZjIzYi0xYTliLTQyMmEtOGIyNi0zNzQyNDM0ZGY0NzYiLCJib2R5Ijp7IkVudGl0eVN1YnR5cGVJZCI6LTEsIlRleHRLZXkiOiJwZXJzb24tbm9uZS1iaWxsLWJha2VyIiwiTGVnYWxHaXZlbk5hbWUiOiJCaWxsIiwiTGVnYWxTdXJuYW1lIjoiQmFrZXIiLCJCb3JuQXREYXRlVGltZVV0YyI6IjIwMDItMDItMDMgMTE6MTI6MTMuMTIzIiwiTGVnYWxDaXRpemVuT2ZDb3VudHJ5R2VvZ3JhcGhpY1VuaXRJZCI6MSwiTG9jYWxlSWQiOjEsIk9yZGluYWwiOi0xLCJJc0FjdGl2ZSI6dHJ1ZX19.rWNowmEoPkF8N0Q5KC5-W83g3hMqIf9TV8KHzLgNbio

            //TODO: AAF-67 Validate JWT
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
                    ((ObjectNode) bodyJwtPayload).put("jti", correlationUuid);
                    ((ObjectNode) bodyJwtPayload).put("body", objectMapper.readTree(requestBody));

                    logger.info(("Request from '" + bodyJwtPayload.get("iss").asText() + "' for '" + bodyJwtPayload.get("aud").asText() + "' using key '" + authenticationJwtHeader.get("kid").asText() + "'"));
                }
            }
            else
            {
                throw new Exception("Missing or invalid request body");
            }

            sqlBlacklistValues = environment.getProperty("sqlNotToAllow").toLowerCase().split(",");
            //TODO: AAF-84 Only check request body for SQL injection???
            errorValues = GuardAgainstSqlIssues(bodyJwtPayload.toString(), sqlBlacklistValues);

            //TODO: AAF-95 Validate entity structure method
            //NOTE: Remove the Publisher role, i.e. publish becomes a privilege???
            //NOTE: Add publishing columns to EntityTypeDefinition table
            //NOTE: Add publishing columns to EntityTypeAttribute table
            //NOTE: Add publishing columns to EntityTypeDefinitionEntityTypeAttributeAssociation table
            //NOTE: Add publishing columns to EntitySubtype table???
            //NOTE: Add publishing columns to EntityTypeAttribute data
            //NOTE: How to handle modeling and publishing scripted data???
            //TODO: AAF-120 Publish inactive entities???
            //TODO: AAF-96 Add application properties and defaults for loc and min environments and for EDM port for updates

            //NOTE: Get unpublished EntityTypeDefinitions
            //NOTE: Get unpublished EntityTypeAttributes
            //NOTE: Get unpublished EntityTypeDefinitionEntityTypeAttributeAssociations
            //TODO: AAF-97 Get unpublished EntitySubtypes/scripted data
            //TODO: AAF-98 Generate pending (new but unpublished as of) change list for approval, and pass it to PublishBusinessEntitys() as approval

            //NOTE: Create new entity schema
            //NOTE: Create new entity table
            //TODO: AAF-97 Create new entity scripted data
            //NOTE: Update as published
            //NOTE: Re-cache SDS and EDM data

            //TODO: AAF-99 Refactor int, ArrayLists, etc to long, etc and other SDS changes in EDM

            //TODO: AAF-100 Remove publisher role from EDM and SDS README files
            //TODO: AAF-93 Consider converting this to a BPMN process when possible

            //TODO: AAF-101 Add JSON event data publishing URL to EDM and SDS for notifications that should result in re-caching

            //TODO: AAF-102 Add third uniqueness factor (subtype group name?) to EntitySubtype to account for the difference between conflicting "Value" and "GeneralDataType" Uuid LocalizedNames???

//            if (errorValues.length() > 0)
//            {
//                throw new Exception("Request body contains invalid value(s): " + errorValues);
//            }

            //NOTE: Create new schemas, based on any unpublished EntityTypeDefinitions
            //NOTE: Creating a schema alone does not complete the process of publishing an EntityTypeDefinition. The corresponding table must also be created (below) before the EntityTypeDefinition is updated as published.
            unpublishedEntityData = GetUnpublishedEntityData(1, asOfDateTimeUtc);

            if ((unpublishedEntityData != null) && (unpublishedEntityData.get("EntityData").size() > 0))
            {
                unpublishedEntityTypeDefinitions = new ArrayList<EntityTypeDefinition>();

                for (int i = 0; i < unpublishedEntityData.get("EntityData").size(); i++)
                {
                    //TODO: Check for future publish date

                    unpublishedEntityTypeDefinitions.add(new EntityTypeDefinition(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("Id").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("EntitySubtypeId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("TextKey").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedName").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedDescription").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedAbbreviation").asText(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("PublishedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("PublishedByInformationSystemUserId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("Ordinal").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("IsActive").asBoolean(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("CreatedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("CreatedByInformationSystemUserId").asLong(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("UpdatedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("UpdatedByInformationSystemUserId").asLong(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("DeletedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("DeletedByInformationSystemUserId").asLong()));

                    preparedStatementSql = "CREATE SCHEMA " + unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedName") + "\n" +
                            "    AUTHORIZATION \"AafCoreModeler\";\n" +
                            "GRANT USAGE ON SCHEMA " + unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedName") + " TO \"AafCoreReadWrite\";\n" +
                            "GRANT USAGE ON SCHEMA " + unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedName") + " TO \"AafCoreReadOnly\";";

                    preparedStatement = connection.prepareStatement(preparedStatementSql);
                    preparedStatement.executeUpdate();

                    logger.info("New schema " + unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedName") + " created in " + databaseName);
                }

                logger.info(unpublishedEntityTypeDefinitions.size() + " EntityTypeDefinitions published in " + databaseName);
            }
            else
            {
                logger.info("No unpublished EntityTypeDefinitions in " + databaseName);
            }


            //NOTE: Publish any unpublished EntityTypeAttributes
            //NOTE: The unpublishedEntityTypeDefinitions ArrayList, populated above, "remembers" the unpublished EntityTypeDefinitions, so it is safe to replace the unpublished EntityTypeDefinition data in unpublishedEntityData
            unpublishedEntityData = GetUnpublishedEntityData(2, asOfDateTimeUtc);

            if ((unpublishedEntityData != null) && (unpublishedEntityData.get("EntityData").size() > 0))
            {
                unpublishedEntityTypeAttributes = new ArrayList<EntityTypeAttribute>();

                headers = new HttpHeaders();
                headers.add("ApiKey", apiKey);
                headers.add("CorrelationUuid", correlationUuid);
                headers.add("Cookie", authenticationJwt.toString());

//                clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
//                restTemplate.setRequestFactory(clientHttpRequestFactory);

                restTemplate = new RestTemplate();
                requestFactory = new HttpComponentsClientHttpRequestFactory();
//                requestFactory.setConnectTimeout(TIMEOUT);
//                requestFactory.setReadTimeout(TIMEOUT);
                restTemplate.setRequestFactory(requestFactory);

                for (int i = 0; i < unpublishedEntityData.get("EntityData").size(); i++)
                {
                    //TODO: Check for future publish date

                    unpublishedEntityTypeAttributes.add(new EntityTypeAttribute(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("Id").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("EntitySubtypeId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("TextKey").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("LocalizedName").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("LocalizedDescription").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("LocalizedAbbreviation").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("LocalizedInformation").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("LocalizedPlaceholder").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("IsLocalizable").asBoolean(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("IsToBeAssociatedWithEachEntityTypeDefinition").asBoolean(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("GeneralizedDataTypeEntitySubtypeId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("DataSizeOrMaximumLengthInBytesOrCharacters").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("DataPrecision").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("DataScale").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("KeyTypeEntitySubtypeId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("RelatedEntityTypeId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("RelatedEntityTypeAttributeId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("RelatedEntityTypeCardinalityEntitySubtypeId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("EntitySubtypeGroupKey").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("ValueEntitySubtypeId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("DefaultValue").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("MinimumValue").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("MaximumValue").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("RegExValidationPattern").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("StepIncrementValue").floatValue(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("RemoteValidationMethodAsAjaxUri").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("IndexEntitySubtypeId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("UniquenessEntitySubtypeId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("SensitivityEntitySubtypeId").asLong(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("PublishedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("PublishedByInformationSystemUserId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("Ordinal").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("IsActive").asBoolean(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("CreatedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("CreatedByInformationSystemUserId").asLong(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("UpdatedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("UpdatedByInformationSystemUserId").asLong(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("DeletedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("DeletedByInformationSystemUserId").asLong()));

//                    publishActionRequestBody = "{\n" +
//                            "    \"PublishedAtDateTimeUtc\": \"" + asOfDateTimeUtc + "\",\n" +
//                            "    \"PublishedByInformationSystemUserId\": " + userId + "\n" +
//                            "  }";
//
//                    entity = new HttpEntity<String>(publishActionRequestBody, headers);
//
//                    //TODO: Create and use EDM URL here
//                    publishActionResponseBody = restTemplate.exchange("http://localhost:8080/entityTypes/EntityTypeDefinitionEntityTypeAttributeAssociation", HttpMethod.PATCH, entity, String.class);
                    publishActionResponseBody = UpdatePublishedEntity("EntityTypeAttribute", unpublishedEntityData.get("EntityData").get(i).get("EntityTypeAttribute").get("Id").asLong(), asOfDateTimeUtc, headers, userId, connection, restTemplate, exchange);
                    //TODO: * AAF-90 Check response body for success
                    unpublishedEntityTypeAttributes.get(i).setPublishedAtDateTimeUtc(Timestamp.from(asOfDateTimeUtc));
                    unpublishedEntityTypeAttributes.get(i).setPublishedByInformationSystemUserId(userId);

                    logger.info("New attribute " + unpublishedEntityTypeAttributes.get(i).getLocalizedName() + " published in " + databaseName);
                }

                logger.info(unpublishedEntityTypeAttributes.size() + " EntityTypeAttributes published in " + databaseName);
            }
            else
            {
                logger.info("No unpublished EntityTypeAttributes in " + databaseName);
            }


            //NOTE: Create new tables, based on newly published EntityTypeAttributes (above) and unpublished EntityTypeDefinitionEntityTypeAttributeAssociations
            //NOTE: The unpublishedEntityTypeDefinitions ArrayList, populated above, "remembers" the unpublished EntityTypeDefinitions, so it is safe to replace the unpublished EntityTypeDefinition data in unpublishedEntityData
            unpublishedEntityData = GetUnpublishedEntityData(3, asOfDateTimeUtc);

            if ((unpublishedEntityData != null) && (unpublishedEntityData.get("EntityData").size() > 0))
            {
                unpublishedEntityTypeAssociations = new ArrayList<EntityTypeDefinitionEntityTypeAttributeAssociation>();

                for (int i = 0; i < unpublishedEntityTypeDefinitions.size(); i++)
                {
                    preparedStatementSql = "CREATE TABLE \"" + unpublishedEntityTypeDefinitions.get(i).getLocalizedName() + "\".\"" + unpublishedEntityTypeDefinitions.get(i).getLocalizedName() + "\"\n";
                    preparedStatementSql = preparedStatementSql + "(" + "\n";

                    //NOTE: Get the Ids of any associated EntityTypeAttributes
                    for (int j = 0; j < unpublishedEntityData.get("EntityData").size(); j++)
                    {
                        if (unpublishedEntityData.get("EntityData").get(j).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("EntityTypeDefinitionId").asLong() == unpublishedEntityTypeDefinitions.get(i).getId())
                        {
                            //TODO: Check for future publish date

                            cachedEntityAssociation = new EntityTypeDefinitionEntityTypeAttributeAssociation(unpublishedEntityData.get("EntityData").get(j).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("Id").asLong(), unpublishedEntityData.get("EntityData").get(j).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("EntitySubtypeId").asLong(), unpublishedEntityData.get("EntityData").get(j).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("TextKey").asText(), unpublishedEntityData.get("EntityData").get(j).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("EntityTypeDefinitionId").asLong(), unpublishedEntityData.get("EntityData").get(j).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("EntityTypeAttributeId").asLong(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(j).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("PublishedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(j).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("PublishedByInformationSystemUserId").asLong(), unpublishedEntityData.get("EntityData").get(j).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("Ordinal").asLong(), unpublishedEntityData.get("EntityData").get(j).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("IsActive").asBoolean(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(j).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("CreatedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(j).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("CreatedByInformationSystemUserId").asLong(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(j).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("UpdatedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(j).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("UpdatedByInformationSystemUserId").asLong(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(j).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("DeletedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(j).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("DeletedByInformationSystemUserId").asLong());
                            unpublishedEntityTypeAssociations.add(cachedEntityAssociation);

                            cachedEntityTypeAttribute = GetCachedEntityTypeAttributeById(GetCachedEntityTypeAttributeById(unpublishedEntityData.get("EntityData").get(j).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("EntityTypeAttributeId").asLong()).getId());

                            //NOTE: EntitySubtypeId = 67 (SystemSupplied), 8 (Required in/with the request), 9 (Optional, will take on the database- or service-defined value, e.g. Ordinal or TextKey, respectively)
                            //NOTE: GeneralizedDataTypeEntitySubtypeId = 10 (Boolean), 11 (Integer), 12 (UnicodeCharacter), 13 (UnicodeString), 14 (DateTime), 16 (Decimal), 68 (UUID)
                            //NOTE: ValueEntitySubtypeId 25 (DefaultTextKey), 26 (UseDefaultValue) -- Note that the ValueEntitySubtypeId attribute (formerly named EntityTypeAttributeValueEntitySubtypeId) may indicate a formula-based rather than the statically-defined DefaultValue and may be relevant/related here
                            switch ((int) cachedEntityTypeAttribute.getGeneralizedDataTypeEntitySubtypeId())
                            {
                                //NOTE: Boolean
                                case 10:
                                    if ((cachedEntityTypeAttribute.getEntitySubtypeId() == 9) && (cachedEntityTypeAttribute.getValueEntitySubtypeId() == 26))
                                    {
                                        preparedStatementSql = preparedStatementSql + "    \"" + cachedEntityTypeAttribute.getLocalizedName() + "\" boolean DEFAULT " + cachedEntityTypeAttribute.getDefaultValue();
                                    }
                                    else
                                    {
                                        preparedStatementSql = preparedStatementSql + "    \"" + cachedEntityTypeAttribute.getLocalizedName() + "\" boolean";
                                    }
                                    break;
                                //NOTE: Integer
                                case 11:
                                    if ((cachedEntityTypeAttribute.getEntitySubtypeId() == 9) && (cachedEntityTypeAttribute.getValueEntitySubtypeId() == 26))
                                    {
                                        preparedStatementSql = preparedStatementSql + "    \"" + cachedEntityTypeAttribute.getLocalizedName() + "\" bigint DEFAULT " + cachedEntityTypeAttribute.getDefaultValue();
                                    }
                                    else
                                    {
                                        preparedStatementSql = preparedStatementSql + "    \"" + cachedEntityTypeAttribute.getLocalizedName() + "\" bigint";
                                    }
                                    break;
                                //NOTE: UnicodeCharacter
                                case 12:
                                    if ((cachedEntityTypeAttribute.getEntitySubtypeId() == 9) && (cachedEntityTypeAttribute.getValueEntitySubtypeId() == 26))
                                    {
                                        preparedStatementSql = preparedStatementSql + "    \"" + cachedEntityTypeAttribute.getLocalizedName() + "\" character DEFAULT '" + cachedEntityTypeAttribute.getDefaultValue() + "'";
                                    }
                                    else
                                    {
                                        preparedStatementSql = preparedStatementSql + "    \"" + cachedEntityTypeAttribute.getLocalizedName() + "\" character";
                                    }
                                    break;
                                //NOTE: UnicodeString
                                case 13:
                                    if ((cachedEntityTypeAttribute.getEntitySubtypeId() == 9) && (cachedEntityTypeAttribute.getValueEntitySubtypeId() == 26))
                                    {
                                        preparedStatementSql = preparedStatementSql + "    \"" + cachedEntityTypeAttribute.getLocalizedName() + "\" character varying(" + cachedEntityTypeAttribute.getDataSizeOrMaximumLengthInBytesOrCharacters() + ") COLLATE pg_catalog.\"default\" DEFAULT '" + cachedEntityTypeAttribute.getDefaultValue() + "'";
                                    }
                                    else
                                    {
                                        preparedStatementSql = preparedStatementSql + "    \"" + cachedEntityTypeAttribute.getLocalizedName() + "\" character varying(" + cachedEntityTypeAttribute.getDataSizeOrMaximumLengthInBytesOrCharacters() + ") COLLATE pg_catalog.\"default\"";
                                    }
                                    break;
                                //NOTE: DateTime
                                case 14:
                                    if ((cachedEntityTypeAttribute.getEntitySubtypeId() == 9) && (cachedEntityTypeAttribute.getValueEntitySubtypeId() == 26))
                                    {
                                        preparedStatementSql = preparedStatementSql + "    \"" + cachedEntityTypeAttribute.getLocalizedName() + "\" timestamp without time zone DEFAULT '" + cachedEntityTypeAttribute.getDefaultValue() + "'";
                                    }
                                    else
                                    {
                                        preparedStatementSql = preparedStatementSql + "    \"" + cachedEntityTypeAttribute.getLocalizedName() + "\" timestamp without time zone";
                                    }
                                    break;
                                //NOTE: Decimal (including currency values)
                                case 16:
                                    if ((cachedEntityTypeAttribute.getEntitySubtypeId() == 9) && (cachedEntityTypeAttribute.getValueEntitySubtypeId() == 26))
                                    {
                                        preparedStatementSql = preparedStatementSql + "    \"" + cachedEntityTypeAttribute.getLocalizedName() + "\" decimal(" + cachedEntityTypeAttribute.getDataPrecision() + "," + cachedEntityTypeAttribute.getDataScale() + ") DEFAULT " + cachedEntityTypeAttribute.getDefaultValue();
                                    }
                                    else
                                    {
                                        preparedStatementSql = preparedStatementSql + "    \"" + cachedEntityTypeAttribute.getLocalizedName() + "\" decimal(" + cachedEntityTypeAttribute.getDataPrecision() + "," + cachedEntityTypeAttribute.getDataScale() + ")";
                                    }
                                    break;
                                //NOTE: UUID
                                case 68:
                                    if ((cachedEntityTypeAttribute.getEntitySubtypeId() == 9) && (cachedEntityTypeAttribute.getValueEntitySubtypeId() == 26))
                                    {
                                        preparedStatementSql = preparedStatementSql + "    \"" + cachedEntityTypeAttribute.getLocalizedName() + "\" uuid DEFAULT '" + cachedEntityTypeAttribute.getDefaultValue() + "'";
                                    }
                                    else
                                    {
                                        preparedStatementSql = preparedStatementSql + "    \"" + cachedEntityTypeAttribute.getLocalizedName() + "\" uuid";
                                    }
                                    break;
                                //TODO: AAF-103 Add floating point (real/double) data type here for approximate values???

                                //TODO: AAF-104 Set attribute's RelatedEntityTypeId on publish???
                                //TODO: AAF-105 Is attribute's RelatedEntityTypeAttributeId meaningful/necessary???
                                //TODO: AAF-106 How should an attributes RelatedEntityTypeCardinalityEntitySubtypeId be used during publish???
                                //TODO: AAF-107 Rename attribute's ValueEntitySubtypeId to indicate that it comes from a formula???
                                //TODO: AAF-108 How should an attribute's EntityTypeAttributeIndexEntitySubtypeId (NOTE: Not used or named instead as IndexEntitySubtypeId???) (Primary or Secondary) be used during publish???
                                //TODO: AAF-109 How should an attribute's EntityTypeAttributeUniquenessEntitySubtypeId (SingleColumn or Composite) be used during publish???
                                //TODO: AAF-110 How does an attribute's RelatedEntityTypeId differ from its EntityTypeId???  Is this for mapping external InformationSystem attributes???
                                //TODO: AAF-111 Does attribute's EntitySubtypeGroupKey (33) duplicate its GroupKey (45)???  Yes, it seems to.  EntitySubtypeGroupKey (33) is used in the EntityTypeAttribute data script file and once in an association (the association entity's reflexive self definition).  45 is used once in the EntitySubtype association, making changing it a breaking change with potentially wide-ranging effects, including EDM.  The EntitySubtypeGroupKey is more specific and descriptive than GroupKey, especially in the attribute context.  Postponing this decision until after publish is working.
                                //NOTE: AAF-112 Does an attribute's DataStructureEntitySubtypeId (Id = 66) duplicate its DataStructureEntitySubtypeId (Id = 20)???  Yes, it seems to.  66 is used once in an association (the association entity's reflexive self definition).  20 is never used.  Commented out 20 in data script file to prevent future use because 66 (data structure) is logically related to 65 (data location).
                                //NOTE: AAF-113 Added unique constraint on LocalizedName and DeletedAtDateTimeUtc for EntityTypeDefinition and EntityTypeAttribute models
                            }

                            preparedStatementSql = preparedStatementSql + " NOT NULL,\n";
                        }

                        publishActionResponseBody = UpdatePublishedEntity("EntityTypeDefinitionEntityTypeAttributeAssociation", unpublishedEntityData.get("EntityData").get(j).get("EntityTypeDefinitionEntityTypeAttributeAssociation").get("Id").asLong(), asOfDateTimeUtc, headers, userId, connection, restTemplate, exchange);
                        //TODO: * AAF-90 Check response body for success
                    }

                    //CONSTRAINT
                    preparedStatementSql = preparedStatementSql + "    CONSTRAINT " + unpublishedEntityTypeDefinitions.get(i).getLocalizedName() + "_PK PRIMARY KEY (\"Id\"),\n" +
                    "    CONSTRAINT " + unpublishedEntityTypeDefinitions.get(i).getLocalizedName() + "_CHK_TextKey CHECK (\"TextKey\" ~* '^[a-z0-9-]+$'),\n" +
                    "    CONSTRAINT " + unpublishedEntityTypeDefinitions.get(i).getLocalizedName() + "_UQ1_TextKey_DeletedAtDateTimeUtc UNIQUE (\"TextKey\", \"DeletedAtDateTimeUtc\"),\n" +
                    //TODO: AAF-114 Implement entity-specific constraints, e.g. does it have a LocalizedName or other attributes that must be unique???
//                        "    CONSTRAINT " + unpublishedEntityTypeDefinitions.get(i).getLocalizedName() + "_UQ1_LocalizedName_DeletedAtDateTimeUtc UNIQUE (\"LocalizedName\", \"DeletedAtDateTimeUtc\"),\n" +
                    "    CONSTRAINT " + unpublishedEntityTypeDefinitions.get(i).getLocalizedName() + "_FK_EntitySubtypeId FOREIGN KEY (\"EntitySubtypeId\") REFERENCES \"EntitySubtype\".\"EntitySubtype\"(\"Id\")\n" +
                    ")\n" +
                    "TABLESPACE pg_default;\n\n";

                    //INDEX
                    //TODO: AAF-115 Implement entity-specific indexes, e.g. does it have a LocalizedName or other attributes that will be searched, etc???
//                        preparedStatementSql = preparedStatementSql + "CREATE INDEX " + unpublishedEntityTypeDefinitions.get(i).getLocalizedName() + "_IDX_LocalizedName ON \"" + unpublishedEntityTypeDefinitions.get(i).getLocalizedName() + "\".\"" + unpublishedEntityTypeDefinitions.get(i).getLocalizedName() + "\" (\"LocalizedName\");";

                    preparedStatement = connection.prepareStatement(preparedStatementSql);
                    preparedStatement.executeUpdate();

                    publishActionResponseBody = UpdatePublishedEntity("EntityTypeDefinition", unpublishedEntityTypeDefinitions.get(i).getId(), asOfDateTimeUtc, headers, userId, connection, restTemplate, exchange);
                    //TODO: * AAF-90 Check response body for success

                    logger.info("New table " + unpublishedEntityTypeDefinitions.get(i).getLocalizedName() + " created in " + databaseName);

                    //TODO: *** Insert new entity into EntityType table
                }

                logger.info(unpublishedEntityTypeAssociations.size() + " EntityTypeDefinitionEntityTypeAttributeAssociations published in " + databaseName);
            }
            else
            {
                logger.info("No unpublished EntityTypeDefinitionEntityTypeAttributeAssociations in " + databaseName);
            }


            //NOTE: Create new scripted data, based on any unpublished EntityTypeData
            unpublishedEntityData = GetUnpublishedEntityData(4, asOfDateTimeUtc);

            if ((unpublishedEntityData != null) && (unpublishedEntityData.get("EntityData").size() > 0))
            {
//                unpublishedEntityTypeData = new ArrayList<EntityTypeDefinition>();
//
//                for (int i = 0; i < unpublishedEntityData.get("EntityData").size(); i++)
//                {
//                    //TODO: Check for future publish date
//
//                    unpublishedEntityTypeDefinitions.add(new EntityTypeDefinition(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("Id").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("EntitySubtypeId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("TextKey").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedName").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedDescription").asText(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedAbbreviation").asText(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("PublishedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("PublishedByInformationSystemUserId").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("Ordinal").asLong(), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("IsActive").asBoolean(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("CreatedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("CreatedByInformationSystemUserId").asLong(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("UpdatedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("UpdatedByInformationSystemUserId").asLong(), Timestamp.valueOf(unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("DeletedAtDateTimeUtc").asText()), unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("DeletedByInformationSystemUserId").asLong()));
//
//                    preparedStatementSql = "CREATE SCHEMA " + unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedName") + "\n" +
//                            "    AUTHORIZATION \"AafCoreModeler\";\n" +
//                            "GRANT USAGE ON SCHEMA " + unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedName") + " TO \"AafCoreReadWrite\";\n" +
//                            "GRANT USAGE ON SCHEMA " + unpublishedEntityData.get("EntityData").get(i).get("EntityTypeDefinition").get("LocalizedName") + " TO \"AafCoreReadOnly\";";
//
//                    preparedStatement = connection.prepareStatement(preparedStatementSql);
//                    preparedStatement.executeUpdate();
//
//                    //TODO: Update published status
//
//                    logger.info("New table " + unpublishedEntityTypeDefinitions.get(i).getLocalizedName() + " created in " + databaseName);
//                }
//
//                logger.info(unpublishedEntityTypeData.size() + " EntityTypeData records published in " + databaseName);
            }
            else
            {
                logger.info("No unpublished EntityTypeData in " + databaseName);
            }


            entityData = "{\n" +
                    "    \"EntityType\": \"Not Applicable (N/A)\",\n" +
                    "    \"TotalRows\": -1,\n" +
                    "    \"AsOfDataTimeUtc\": " + unpublishedEntityData.get("AsOfDataTimeUtc") + ",\n" +
                    "    \"EntityData\": []\n" +
                    "}";

            //TODO: AAF-116 Filter or mask unauthorized or sensitive attributes for this InformationSystemUserRole (as JSON)???

            UncacheEntityData();
            CacheEntityData();
            //TODO: AAF-92 Enqueue standardized event data to re-cache EDM data
        }
        catch (Exception e)
        {
            logger.error("PublishBusinessEntitys() for " + databaseName + " failed due to: " + e);
            return new ResponseEntity<String>("{\"EntityType\" : \"" + bodyJwtPayload.get("body").get("LocalizedName").asText() + "\", \"TotalRows\": -1, \"EntityData\": [], \"Code\": 500, \"Message\": \"" + e.toString() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
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
                logger.error("Failed to close statement and/or connection resource in PublishBusinessEntitys() due to: " + e);
            }
        }

        logger.info("PublishBusinessEntitys() succeeded for " + databaseName);
        //result = new JSONObject("{\"EntityData\":" + entityData + "}");
        //return "{\"EntityData\":" + entityData + "}";
        //TODO: AAF-82 Echo input parameters in Postgres function return JSON
        return new ResponseEntity<String>(entityData, HttpStatus.CREATED);
    }

    @Operation(summary = "Automate the creation of multi-level, composed entity data, e.g. InternetDomainLabel > InternetDomainLabelHierarchy > UniformResourceIdentifier > InformationSystem", description = "Automate the creation of multi-level, composed entity data by creating and linking dependent entity data in order to quickly create a valid structure that can be updated and improved later.")
    @Parameter(in = ParameterIn.HEADER, description = "API key", name = "ApiKey", content = @Content(schema = @Schema(type = "string")))
    @Parameter(in = ParameterIn.HEADER, description = "Correlation UUID", name = "CorrelationUuid", content = @Content(schema = @Schema(type = "string")), required = false)
    @Parameter(in = ParameterIn.COOKIE, description = "JWT Authentication token", name = "Authentication", content = @Content(schema = @Schema(type = "string")))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping(value = "/entityGraphs/{entityTypeName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> BuildBusinessEntityGraph(@PathVariable("entityTypeName") String entityTypeName, @RequestParam(defaultValue = "#{T(java.time.Instant).now()}") Instant asOfDateTimeUtc, @RequestBody String requestBody, ServerWebExchange exchange) throws Exception
    {
        ServerHttpRequest request = null;

        HttpHeaders headers = null;

        String apiKey = "";
        String correlationUuid = "";

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

        String localizedName = "";
        String localizedDescription = "";
        URL url = null;

        Long internetDomainLabelLevel = -1L;
        String internetDomainLabelLevelName = "";
        String[] internetDomainLabels = null;
        Long[] internetDomainLabelIds = null;
        Long[] internetDomainLabelHierarchyIds = null;
        String uniformResourceIdentifierName = "";
        Long uniformResourceIdentifierId = -1L;
        Long informationSystemId = -1L;

        String createActionRequestBody = "";
        ResponseEntity<String> createActionResponseBody = null;

        JsonNode entityData = null;

        //NOTE: In this initial version of this method, we are only supporting the InternetDomainLabel > InternetDomainLabelHierarchy >
        //NOTE: UniformResourceIdentifier > InformationSystem graph in order to quickly enable data import for WebSpanner.  In subsequent
        //NOTE: versions, we will support the full graph of published business entities, using a new (thought this was already there???)
        //NOTE: foreign key property in the EntityTypeAttribute model.
        try
        {
            logger.debug("Attempting to BuildBusinessEntityGraph() for '{}'", entityTypeName);

            request = exchange.getRequest();

            ENVIRONMENT_JWT_SHARED_SECRET = environment.getProperty("environmentJwtSharedSecret");

            apiKey = exchange.getRequest().getHeaders().getFirst("ApiKey");

            if ((apiKey == null) || (apiKey.length() < 1))
            {
                throw new Exception("No 'ApiKey' header included in the request");
            }
            else
            {
                //TODO: AAF-66 Validate API key by looking it up in the database, ensuring that it is not disabled, checking its associated permissions extent, and (later) checking that it is associated with the authenticated user's OrganizationalUnit
                logger.debug("ApiKey header '{}' included in the request", apiKey);
            }

            correlationUuid = exchange.getRequest().getHeaders().getFirst("CorrelationUuid");

            if ((correlationUuid == null) || (correlationUuid.length() < 1))
            {
                correlationUuid = UUID.randomUUID().toString();
                logger.debug("CorrelationUuid '{}' generated for the request", correlationUuid);
            }
            else
            {
                //TODO: AAF-66 Validate API key by looking it up in the database, ensuring that it is not disabled, checking its associated permissions extent, and (later) checking that it is associated with the authenticated user's OrganizationalUnit
                logger.debug("CorrelationUuid '{}' included in the request", correlationUuid);
            }

            objectMapper = new ObjectMapper();

            //TODO: AAF-67 Validate JWT
            authenticationJwt = request.getCookies().getFirst("Authentication");
            authenticationJwtSections = authenticationJwt.getValue().split("\\.");
            authenticationJwtHeader = objectMapper.readTree(decoderBase64.decode(authenticationJwtSections[0]));
            authenticationJwtPayload = objectMapper.readTree(decoderBase64.decode(authenticationJwtSections[1]));
            //TODO: Validate JWT signature per https://www.baeldung.com/java-jwt-token-decode

            if ((authenticationJwtHeader != null) && (authenticationJwtPayload != null))
            {
                logger.debug("Requested by '{}' using key '{}'", authenticationJwtPayload.get("body").get("EmailAddress").asText(), authenticationJwtHeader.get("kid").asText());
            }
            else
            {
                throw new Exception("Missing or invalid 'Authentication' cookie included with the request");
            }

            //TODO: AAF-68 Look up InformationSystemUser.Id using the authenticated user's EmailAddress in the Authentication JWT payload, and assign it below
            userId = -100L;

            //TODO: AAF-69 Check user's role(s) and permissions for this operation

            //NOTE: Example request http://localhost:8080/Person with "Authentication" JWT and JWT request body
            //NOTE: https://learning.postman.com/docs/sending-requests/response-data/cookies/
//            Authentication JWT: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0ZWQiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiJlZjRhZjRlMy1lNzM2LTQyNWEtYWFmZi1lY2EwM2I3YjliMjgiLCJib2R5Ijp7IkVtYWlsQWRkcmVzcyI6ImFteS5hbmRlcnNvbkBhbXlzYWNjb3VudGluZy5jb20ifX0.Djq5LYPEK1QFgBk9aN5Vei37K6Cb8TxNH3ADWDcUaHs
//            Request JWT: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IlBPU1QgL0VudGl0eVR5cGUiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiI4NzUyZjIzYi0xYTliLTQyMmEtOGIyNi0zNzQyNDM0ZGY0NzYiLCJib2R5Ijp7IkVudGl0eVN1YnR5cGVJZCI6LTEsIlRleHRLZXkiOiJwZXJzb24tbm9uZS1iaWxsLWJha2VyIiwiTGVnYWxHaXZlbk5hbWUiOiJCaWxsIiwiTGVnYWxTdXJuYW1lIjoiQmFrZXIiLCJCb3JuQXREYXRlVGltZVV0YyI6IjIwMDItMDItMDMgMTE6MTI6MTMuMTIzIiwiTGVnYWxDaXRpemVuT2ZDb3VudHJ5R2VvZ3JhcGhpY1VuaXRJZCI6MSwiTG9jYWxlSWQiOjEsIk9yZGluYWwiOi0xLCJJc0FjdGl2ZSI6dHJ1ZX19.rWNowmEoPkF8N0Q5KC5-W83g3hMqIf9TV8KHzLgNbio

            //TODO: AAF-67 Validate JWT
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
                    logger.debug("Request from '{}' for '{}' using key '{}'", bodyJwtPayload.get("iss").asText(), bodyJwtPayload.get("aud").asText(), authenticationJwtHeader.get("kid").asText());
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
                    ((ObjectNode) bodyJwtPayload).put("jti", correlationUuid);
                    ((ObjectNode) bodyJwtPayload).put("body", objectMapper.readTree(requestBody));

                    logger.debug("Request from '{}' for '{}' using key '{}'", bodyJwtPayload.get("iss").asText(), bodyJwtPayload.get("aud").asText(), authenticationJwtHeader.get("kid").asText());
                }
            }
            else
            {
                throw new Exception("Missing or invalid request body");
            }

            //NOTE: Insert new, unpublished InternetDomainLabel, and get resulting Id value
            headers = new HttpHeaders();
            headers.add("ApiKey", apiKey);
            headers.add("CorrelationUuid", correlationUuid);
            headers.add("Cookie", authenticationJwt.toString());

            //NOTE: Get LocalizedName from request body, e.g. "Example"
            if ((bodyJwtPayload.get("body").get("LocalizedName").asText() == null) || (bodyJwtPayload.get("body").get("LocalizedName").asText().length() < 1))
            {
                throw new Exception("Missing or invalid 'LocalizedName' in the request body");
            }
            else
            {
                logger.debug("LocalizedName '{}' included in the request body", bodyJwtPayload.get("body").get("LocalizedName").asText());
            }

            localizedName = bodyJwtPayload.get("body").get("LocalizedName").asText();

            //NOTE: Get LocalizedDescription from request body, e.g. "An example InformationSystem"
            if ((bodyJwtPayload.get("body").get("LocalizedDescription").asText() == null) || (bodyJwtPayload.get("body").get("LocalizedDescription").asText().length() < 1))
            {
                throw new Exception("Missing or invalid 'LocalizedDescription' in the request body");
            }
            else
            {
                logger.debug("LocalizedDescription '{}' included in the request body", bodyJwtPayload.get("body").get("LocalizedDescription").asText());
            }

            localizedDescription = bodyJwtPayload.get("body").get("LocalizedDescription").asText();

            //NOTE: Get URL from request body, e.g. "https://www.example.com"
            if ((bodyJwtPayload.get("body").get("Url").asText() == null) || (bodyJwtPayload.get("body").get("Url").asText().length() < 1))
            {
                throw new Exception("Missing or invalid 'Url' in the request body");
            }
            else
            {
                logger.debug("Url '{}' included in the request body", bodyJwtPayload.get("body").get("Url").asText());
            }

            url = new URL(bodyJwtPayload.get("body").get("Url").asText());

            //TODO: Check for existing InformationSystem with the same name and URL, and return it if found, i.e. skip the rest of this method

            internetDomainLabels = url.getHost().split("\\.");

            if (internetDomainLabels.length < 2)
            {
                throw new Exception("Invalid URL '" + url.toString() + "' in the request body, must contain at least a top-level and second-level domain");
            }
            else
            {
                logger.debug("InternetDomainLabels found in the URL: {}", (Object) internetDomainLabels);
            }

//            internetDomainLabels = new String[internetDomainLabels.length];
            internetDomainLabelIds = new Long[internetDomainLabels.length];
            internetDomainLabelHierarchyIds = new Long[internetDomainLabels.length];

            //NOTE: Working from right (top-level domain) to left (subdomains), e.g. "com", "example", "www":
            for (int i = internetDomainLabels.length - 1; i > -1; i--)
            {
                //NOTE: GET InternetDomainLabels by name and subtype to see if the label exists at the appropriate level
                if (i == internetDomainLabels.length - 1) {
                    internetDomainLabelLevel = (long) 76;   //NOTE: Top-level domain, e.g. "com"
                    internetDomainLabelLevelName = "topleveldomain";
                } else if (i == internetDomainLabels.length - 2) {
                    internetDomainLabelLevel = (long) 77;   //NOTE: Second-level domain, e.g. "example"
                    internetDomainLabelLevelName = "secondleveldomain";
                } else {
                    internetDomainLabelLevel = (long) 78;   //NOTE: Subdomain, e.g. "www", "app", etc
                    internetDomainLabelLevelName = "subdomain";
                }

                createActionResponseBody = ReadBusinessEntityData("InternetDomainLabel", asOfDateTimeUtc, headers, apiKey, authenticationJwt, correlationUuid, userId, URLEncoder.encode("LOWER(\"LocalizedName\") = '" + internetDomainLabels[i].toLowerCase() + "' AND \"EntitySubtypeId\" = " + internetDomainLabelLevel, StandardCharsets.UTF_8.toString()).replace("+", "%20"), exchange);
                entityData = objectMapper.readTree(createActionResponseBody.getBody());

                //NOTE: If not, POST InternetDomainLabel with the name, e.g. "com", "example", "www", and get the resulting Id
                if (entityData.get("EntityData").size() > 0)
                {
                    logger.debug("InternetDomainLabel '{}' found at level '{}'", internetDomainLabels[i], internetDomainLabelLevel);

                    internetDomainLabelIds[i] = entityData.get("EntityData").get(0).get("Id").asLong();
                    internetDomainLabels[i] = entityData.get("EntityData").get(0).get("LocalizedName").asText();
                }
                else
                {
                    logger.debug("InternetDomainLabel '{}' not found at level '{}', creating new InternetDomainLabel", internetDomainLabels[i], internetDomainLabelLevel);

                    createActionRequestBody = "{\n" +
                            "    \"EntitySubtypeId\": " + internetDomainLabelLevel + ",\n" +
                            "    \"TextKey\": \"internetdomainlabel-" + internetDomainLabelLevelName.toLowerCase() + "-" + internetDomainLabels[i].toLowerCase() + "\",\n" +
                            "    \"LocalizedName\": \"" + internetDomainLabels[i].toLowerCase() + "\",\n" +
                            "    \"LocalizedDescription\": \"The ''" + internetDomainLabels[i].toLowerCase() + "'' InternetDomainLabel from the ''" + url.toString().replace("'", "''") + "'' UniformResourceLocator (URL)\",\n" +
                            "    \"LocalizedAbbreviation\": \"" + internetDomainLabels[i].substring(0, internetDomainLabels[i].length() - 1).format("%1.15s", internetDomainLabels[i]).toLowerCase() + "\",\n" +
//                    "    \"Ordinal\": " + ordinal + ",\n" +
//                    "    \"IsActive\": " + isActive + ",\n" +
                            "    \"CorrelationUuid\": \"" + correlationUuid + "\"\n" +
                            "  }";

                    createActionResponseBody = WriteBusinessEntityData("InternetDomainLabel", asOfDateTimeUtc, headers, apiKey, authenticationJwt, correlationUuid, userId, createActionRequestBody, exchange);
                    entityData = objectMapper.readTree(createActionResponseBody.getBody());

                    if (entityData.get("EntityData").size() > 0)
                    {
                        logger.debug("InternetDomainLabel '{}' created at level '{}'", internetDomainLabels[i], internetDomainLabelLevel);

                        internetDomainLabelIds[i] = entityData.get("EntityData").get(0).get("Id").asLong();
                        internetDomainLabels[i] = entityData.get("EntityData").get(0).get("LocalizedName").asText();
                    }
                    else
                    {
                        throw new Exception("Failed to create InternetDomainLabel '" + internetDomainLabels[i] + "' at level '" + internetDomainLabelLevel);
                    }
                }

                //NOTE: POST InternetDomainLabelHierarchy with the Ids of the parent and child InternetDomainLabels
                if (i == internetDomainLabels.length - 1)       //NOTE: Top-level domain, e.g. "com"
                {
                    createActionResponseBody = ReadBusinessEntityData("InternetDomainLabelHierarchy", asOfDateTimeUtc, headers, apiKey, authenticationJwt, correlationUuid, userId, URLEncoder.encode("\"ParentInternetDomainLabelId\" = " + internetDomainLabelIds[i] + " AND \"ChildInternetDomainLabelId\" = " + internetDomainLabelIds[i], StandardCharsets.UTF_8.toString()).replace("+", "%20"), exchange);
                    entityData = objectMapper.readTree(createActionResponseBody.getBody());

                    //NOTE: If not, POST InternetDomainLabel with the name, e.g. "com", "example", "www", and get the resulting Id
                    if (entityData.get("EntityData").size() > 0)
                    {
                        logger.debug("Top-level InternetDomainLabelHierarchy '{}' found for InternetDomainLabel '{}'", entityData.get("EntityData").get(0).get("Id").asLong(), internetDomainLabels[i]);

                        internetDomainLabelHierarchyIds[i] = entityData.get("EntityData").get(0).get("Id").asLong();
                        uniformResourceIdentifierName = internetDomainLabels[i].toLowerCase();
                    }
                    else
                    {
                        logger.debug("Top-level InternetDomainLabelHierarchy not found for InternetDomainLabel '{}', creating new InternetDomainLabelHierarchy", internetDomainLabels[i]);

                        createActionRequestBody = "{\n" +
                                "    \"EntitySubtypeId\": 0,\n" +
                                "    \"TextKey\": \"internetdomainlabelhierarchy-" + internetDomainLabels[i].toLowerCase() + "-" + internetDomainLabels[i].toLowerCase() + "\",\n" +
                                "    \"ParentInternetDomainLabelId\": " + internetDomainLabelIds[i] + ",\n" +
                                "    \"ChildInternetDomainLabelId\": " + internetDomainLabelIds[i] + ",\n" +
//                    "    \"Ordinal\": " + ordinal + ",\n" +
//                    "    \"IsActive\": " + isActive + ",\n" +
                                "    \"CorrelationUuid\": \"" + correlationUuid + "\"\n" +
                                "  }";

                        createActionResponseBody = WriteBusinessEntityData("InternetDomainLabelHierarchy", asOfDateTimeUtc, headers, apiKey, authenticationJwt, correlationUuid, userId, createActionRequestBody, exchange);
                        entityData = objectMapper.readTree(createActionResponseBody.getBody());

                        if (entityData.get("EntityData").size() > 0)
                        {
                            logger.debug("InternetDomainLabelHierarchy '{}' created for InternetDomainLabel '{}'", entityData.get("EntityData").get(0).get("Id").asLong(), internetDomainLabels[i]);

                            internetDomainLabelHierarchyIds[i] = entityData.get("EntityData").get(0).get("Id").asLong();
                            uniformResourceIdentifierName = internetDomainLabels[i].toLowerCase();
                        }
                        else
                        {
                            throw new Exception("Failed to create InternetDomainLabel '" + internetDomainLabels[i] + "' at level '" + internetDomainLabelLevel);
                        }
                    }
                }
                else                                            //NOTE: Second-level domain, e.g. "example", or Subdomain, e.g. "www", "app", etc
                {
                    createActionResponseBody = ReadBusinessEntityData("InternetDomainLabelHierarchy", asOfDateTimeUtc, headers, apiKey, authenticationJwt, correlationUuid, userId, URLEncoder.encode("\"ParentInternetDomainLabelId\" = " + internetDomainLabelIds[i + 1] + " AND \"ChildInternetDomainLabelId\" = " + internetDomainLabelIds[i], StandardCharsets.UTF_8.toString()).replace("+", "%20"), exchange);
                    entityData = objectMapper.readTree(createActionResponseBody.getBody());

                    //NOTE: If not, POST InternetDomainLabel with the name, e.g. "com", "example", "www", and get the resulting Id
                    if (entityData.get("EntityData").size() > 0)
                    {
                        logger.debug("InternetDomainLabelHierarchy '{}' found for InternetDomainLabel '{}'", entityData.get("EntityData").get(0).get("Id").asLong(), internetDomainLabels[i]);

                        internetDomainLabelHierarchyIds[i] = entityData.get("EntityData").get(0).get("Id").asLong();
                        uniformResourceIdentifierName = internetDomainLabels[i + 1].toLowerCase() + "." + uniformResourceIdentifierName;
                    }
                    else
                    {
                        logger.debug("InternetDomainLabelHierarchy not found for InternetDomainLabel '{}', creating new InternetDomainLabelHierarchy", internetDomainLabels[i]);

                        createActionRequestBody = "{\n" +
                                "    \"EntitySubtypeId\": 0,\n" +
                                "    \"TextKey\": \"internetdomainlabelhierarchy-" + internetDomainLabels[i + 1].toLowerCase() + "-" + internetDomainLabels[i].toLowerCase() + "\",\n" +
                                "    \"ParentInternetDomainLabelId\": " + internetDomainLabelIds[i + 1] + ",\n" +
                                "    \"ChildInternetDomainLabelId\": " + internetDomainLabelIds[i] + ",\n" +
//                    "    \"Ordinal\": " + ordinal + ",\n" +
//                    "    \"IsActive\": " + isActive + ",\n" +
                                "    \"CorrelationUuid\": \"" + correlationUuid + "\"\n" +
                                "  }";

                        createActionResponseBody = WriteBusinessEntityData("InternetDomainLabelHierarchy", asOfDateTimeUtc, headers, apiKey, authenticationJwt, correlationUuid, userId, createActionRequestBody, exchange);
                        entityData = objectMapper.readTree(createActionResponseBody.getBody());

                        if (entityData.get("EntityData").size() > 0)
                        {
                            logger.debug("InternetDomainLabelHierarchy '{}' created for InternetDomainLabel '{}'", entityData.get("EntityData").get(0).get("Id").asLong(), internetDomainLabels[i]);

                            internetDomainLabelHierarchyIds[i] = entityData.get("EntityData").get(0).get("Id").asLong();
                            uniformResourceIdentifierName = internetDomainLabels[i + 1].toLowerCase() + "." + uniformResourceIdentifierName;
                        }
                        else
                        {
                            throw new Exception("Failed to create InternetDomainLabel '" + internetDomainLabels[i] + "' at level '" + internetDomainLabelLevel);
                        }
                    }
                }
            }

            //NOTE: POST UniformResourceIdentifier with the Id of the InternetDomainLabelHierarchy and the URL
            createActionResponseBody = ReadBusinessEntityData("UniformResourceIdentifier", asOfDateTimeUtc, headers, apiKey, authenticationJwt, correlationUuid, userId, URLEncoder.encode("\"LocalizedName\" = '" + localizedName + "' AND \"EntitySubtypeId\" = 84", StandardCharsets.UTF_8.toString()).replace("+", "%20"), exchange);
            entityData = objectMapper.readTree(createActionResponseBody.getBody());

            if (entityData.get("EntityData").size() > 0)
            {
                logger.debug("UniformResourceIdentifier '{}' found for '{}'", entityData.get("EntityData").get(0).get("Id").asLong(), url);

                uniformResourceIdentifierId = entityData.get("EntityData").get(0).get("Id").asLong();
            }
            else
            {
                logger.debug("UniformResourceIdentifier '{}' not found for '{}', creating new UniformResourceIdentifier", localizedName, url);

                createActionRequestBody = "{\n" +
                        "    \"EntitySubtypeId\": 84,\n" +
                        "    \"TextKey\": \"uniformresourceidentifier-uniformresourcelocator-" + localizedName.replace(" ", "").toLowerCase() + "\",\n" +
                        "    \"LocalizedName\": \"" + localizedName + "\",\n" +
                        "    \"LocalizedDescription\": \"" + localizedDescription.replace("'", "''") + "\",\n" +
                        "    \"LocalizedAbbreviation\": \"" + localizedName.substring(0, localizedName.length() - 1).format("%1.15s", localizedName) + "\",\n" +
                        "    \"Scheme\": \"" + url.getProtocol() + "\",\n" +
                        "    \"UserInfo\": \"" + Objects.requireNonNullElse(url.getUserInfo(), "") + "\",\n" +
                        "    \"DomainName\": \"" + url.getHost() + "\",\n" +
                        "    \"InternetDomainLabelHierarchyId\": " + internetDomainLabelHierarchyIds[0] + ",\n" +
                        "    \"Port\": \"" + url.getPort() + "\",\n" +
                        "    \"Path\": \"" + Objects.requireNonNullElse(url.getPath(), "") + "\",\n" +
                        "    \"Query\": \"" + Objects.requireNonNullElse(url.getQuery(), "") + "\",\n" +
                        "    \"Fragment\": \"" + Objects.requireNonNullElse(url.getRef(), "") + "\",\n" +
//                    "    \"Ordinal\": " + ordinal + ",\n" +
//                    "    \"IsActive\": " + isActive + ",\n" +
                        "    \"CorrelationUuid\": \"" + correlationUuid + "\"\n" +
                        "  }";

                createActionResponseBody = WriteBusinessEntityData("UniformResourceIdentifier", asOfDateTimeUtc, headers, apiKey, authenticationJwt, correlationUuid, userId, createActionRequestBody, exchange);
                entityData = objectMapper.readTree(createActionResponseBody.getBody());

                if (entityData.get("EntityData").size() > 0)
                {
                    logger.debug("UniformResourceIdentifier '{}' created for '{}'", entityData.get("EntityData").get(0).get("Id").asLong(), url);

                    uniformResourceIdentifierId = entityData.get("EntityData").get(0).get("Id").asLong();
                }
                else
                {
                    throw new Exception("Failed to create UniformResourceIdentifier for '" + url + "'");
                }
            }

            //NOTE: POST InformationSystem with the Id of the UniformResourceIdentifier and the InformationSystem name
            createActionResponseBody = ReadBusinessEntityData("InformationSystem", asOfDateTimeUtc, headers, apiKey, authenticationJwt, correlationUuid, userId, URLEncoder.encode("\"LocalizedName\" = '" + localizedName + "' AND \"EntitySubtypeId\" = 85", StandardCharsets.UTF_8.toString()).replace("+", "%20"), exchange);
            entityData = objectMapper.readTree(createActionResponseBody.getBody());

            if (entityData.get("EntityData").size() > 0)
            {
                logger.debug("InformationSystem '{}' found for '{}'", entityData.get("EntityData").get(0).get("Id").asLong(), localizedName);

                informationSystemId = entityData.get("EntityData").get(0).get("Id").asLong();
            }
            else
            {
                logger.debug("InformationSystem '{}' not found for '{}', creating new InformationSystem", localizedName, localizedName);

                createActionRequestBody = "{\n" +
                        "    \"EntitySubtypeId\": 85,\n" +
                        "    \"TextKey\": \"informationsystem-webapplication-" + localizedName.replace(" ", "").toLowerCase() + "\",\n" +
                        "    \"LocalizedName\": \"" + localizedName + "\",\n" +
                        "    \"LocalizedDescription\": \"" + localizedDescription.replace("'", "''") + "\",\n" +
                        "    \"LocalizedAbbreviation\": \"" + localizedName.substring(0, localizedName.length() - 1).format("%1.15s", localizedName) + "\",\n" +
                        "    \"UniformResourceIdentifierId\": " + uniformResourceIdentifierId + ",\n" +
                        "    \"PurposeInformationSystemEntitySubtypeId\": -1,\n" +
                        "    \"TechnologyInformationSystemEntitySubtypeId\": -1,\n" +
                        "    \"ControllingLegalEntityId\": -1,\n" +
//                    "    \"Ordinal\": " + ordinal + ",\n" +
//                    "    \"IsActive\": " + isActive + ",\n" +
                        "    \"CorrelationUuid\": \"" + correlationUuid + "\"\n" +
                        "  }";

                createActionResponseBody = WriteBusinessEntityData("InformationSystem", asOfDateTimeUtc, headers, apiKey, authenticationJwt, correlationUuid, userId, createActionRequestBody, exchange);
                entityData = objectMapper.readTree(createActionResponseBody.getBody());

                if (entityData.get("EntityData").size() > 0)
                {
                    logger.debug("InformationSystem '{}' created for '{}'", entityData.get("EntityData").get(0).get("Id").asLong(), localizedName);

                    informationSystemId = entityData.get("EntityData").get(0).get("Id").asLong();
                }
                else
                {
                    throw new Exception("Failed to create InformationSystem for '" + localizedName + "'");
                }
            }
        }
        catch (Exception e)
        {
            logger.error("BuildBusinessEntityGraph() for '{}' failed due to: ", entityTypeName, e);
            //TODO: AAF-81 Improve this error output???
            return new ResponseEntity<String>("{[]}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.debug("BuildBusinessEntityGraph() succeeded for '{}'", entityTypeName);
        //result = new JSONObject("{\"EntityData\":" + entityData + "}");
        //return "{\"EntityData\":" + entityData + "}";
        //TODO: AAF-82 Echo input parameters in Postgres function return JSON
        return new ResponseEntity<String>(entityData.toString(), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a system entity instance, i.e. database table record, of the specified type, e.g. EntityTypeDefinition, EntityTypeAttribute, EntityTypeDefinitionEntityTypeAttributeAssociation, or EntityTypeData, once the instance has been successfully published.", description = "Update the PublishedAt and PublishedBy attributes of the specified entity type and Id by providing the valid, required data and any valid, optional data as a JSON Web Token (JWT, please see https://jwt.io/) in the HTTP request body")
    @Parameter(in = ParameterIn.HEADER, description = "Correlation UUID", name = "CorrelationUuid", content = @Content(schema = @Schema(type = "string")), required = false)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PatchMapping(value = "/entityTypes/{entityTypeName}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> UpdatePublishedEntity(@PathVariable("entityTypeName") String entityTypeName, @PathVariable("id") Long id, @RequestParam(defaultValue = "#{T(java.time.Instant).now()}") Instant asOfDateTimeUtc, HttpHeaders headers, Long userId, Connection connection, RestTemplate restTemplate, ServerWebExchange exchange) throws Exception
    {
        ServerHttpRequest request = null;

        String correlationUuid = "";

        String updateActionRequestBody = "";
        HttpComponentsClientHttpRequestFactory requestFactory = null;
//        RestTemplateBuilder restTemplateBuilder = null;
//        RestTemplate restTemplate = null;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = null;
        HttpEntity<String> entity = null;
        ResponseEntity<String> updateActionResponseBody = null;

        //NOTE: Rather than validating the EntityTypeDefinition name, we're going to optimistically pass it through to the database if it returns attributes
        //NOTE: We don't request versions our business entity data structure explicitly in the base URL; instead our explicit (v1.2.3) versioning is maintained internally, based on/derived from the AsOfUtcDateTime query parameter
        //NOTE: Since we're not automatically parsing the resulting JSON into an object, we're returning a JSON String rather than a JSONObject
        try
        {
            logger.debug("Attempting to UpdatePublishedEntity() " + entityTypeName + " " + id);

//            ENVIRONMENT_JWT_SHARED_SECRET = environment.getProperty("environmentJwtSharedSecret");
//
            correlationUuid = exchange.getRequest().getHeaders().getFirst("CorrelationUuid");

            if ((correlationUuid == null) || (correlationUuid.length() < 1))
            {
                correlationUuid = UUID.randomUUID().toString();
                logger.info(("CorrelationUuid '" + correlationUuid + "' generated for the request"));
            }
            else
            {
                //TODO: AAF-66 Validate API key by looking it up in the database, ensuring that it is not disabled, checking its associated permissions extent, and (later) checking that it is associated with the authenticated user's OrganizationalUnit
                logger.info(("CorrelationUuid '" + correlationUuid + "' included in the request"));
            }

            //NOTE: Sort by Ordinal ASC in Gets (EntityTypeDefinitionId ASC, Ordinal ASC in Assocs).
            //NOTE: 20 (Cloned Priv) and 21 (CreatedNew Role) Entity Definitions have default (-1) Ordinals.
            //TODO: 5 (Name) ... 6 (Descr) and 7 (Abbrv) Attribs have 100, 200, and 300 Ordinals (should all be 300 Name).
            //NOTE: 20 (Priv) and 21 (Role) Associations have default (-1) Ordinals, and Assoc Ordinals should match Attrib "bands".
            updateActionRequestBody = "{\n" +
                    "    \"PublishedAtDateTimeUtc\": \"" + asOfDateTimeUtc + "\",\n" +
                    "    \"PublishedByInformationSystemUserId\": " + userId + ",\n" +
                    "    \"CorrelationUuid\": \"" + correlationUuid + "\"\n" +
                    "  }";

            entity = new HttpEntity<String>(updateActionRequestBody, headers);

            //TODO: Create and use EDM URL here
            updateActionResponseBody = restTemplate.exchange("http://localhost:8080/entityTypes/" + entityTypeName + "/" + id, HttpMethod.PATCH, entity, String.class);
            //TODO: * AAF-90 Check response body for success
        }
        catch (Exception e)
        {
            logger.error("UpdatePublishedEntity() failed due to: " + e);
            return new ResponseEntity<String>("{\"EntityType\" : \"" + entityTypeName + "\", \"TotalRows\": -1, \"EntityData\": [], \"Code\": 500, \"Message\": \"" + e.toString() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.debug("UpdatePublishedEntity() succeeded");
        return updateActionResponseBody;
    }

    private ObjectNode GetUnpublishedEntityData(long entityTypeDefinitionId, @RequestParam(defaultValue = "#{T(java.time.Instant).now()}") Instant asOfDateTimeUtc)
    {
        ObjectMapper objectMapper = null;
        ObjectNode unpublishedEntityData = null;

        ObjectNode entity = null;
        ArrayNode entityData = null;

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
                throw new Exception("'asOfDateTimeUtc' query parameter must be greater than or equal to '1900-01-01T00:00:00.000Z'.");
            }

            if (asOfDateTimeUtc.isAfter(Instant.parse("9999-12-31T23:59:59.999Z")))
            {
                throw new Exception("'asOfDateTimeUtc' query parameter must be less than or equal to '9999-12-31T23:59:59.999Z'.");
            }

            objectMapper = new ObjectMapper();
            unpublishedEntityData = objectMapper.createObjectNode();

            entityData = objectMapper.createArrayNode();

            //NOTE: Only pending (new but unpublished as of asOfDateTimeUtc ) changes in this first version.  Non-breaking changes next.  Etc, etc, etc.  Crawl, walk, run.
            switch ((int) entityTypeDefinitionId)
            {
                //NOTE: EntityTypeDefinition
                case 1:
                    unpublishedEntityData.put("EntityType", "EntityTypeDefinition");

                    for (int i = 0 ; i < entityTypeDefinitions.size() ; i++)
                    {
                        //TODO: AAF-117 GetUnpublishedEntityData with live, not cached data???
                        //TODO: AAF-118 Capture and check cached data timestamp???
                        //TODO: AAF-119 Also check if updated since asOfDateTimeUtc???
                        if (entityTypeDefinitions.get(i).getPublishedAtDateTimeUtc().equals(Timestamp.valueOf("9999-12-31 23:59:59.999")))
                        {
                            entity= objectMapper.createObjectNode();

                            entity.put("Id", entityTypeDefinitions.get(i).getId());
                            entity.put("EntitySubtypeId", entityTypeDefinitions.get(i).getEntitySubtypeId());
                            entity.put("TextKey", entityTypeDefinitions.get(i).getTextKey());
                            entity.put("LocalizedName", entityTypeDefinitions.get(i).getLocalizedName());
                            entity.put("LocalizedDescription", entityTypeDefinitions.get(i).getLocalizedDescription());
                            entity.put("LocalizedAbbreviation", entityTypeDefinitions.get(i).getLocalizedAbbreviation());
                            entity.put("PublishedAtDateTimeUtc", entityTypeDefinitions.get(i).getPublishedAtDateTimeUtc().toString());
                            entity.put("PublishedByInformationSystemUserId", entityTypeDefinitions.get(i).getPublishedByInformationSystemUserId());
                            entity.put("Ordinal", entityTypeDefinitions.get(i).getOrdinal());
                            entity.put("IsActive", entityTypeDefinitions.get(i).isIsActive());    //NOTE: See https://stackoverflow.com/questions/42619986/lombok-annotation-getter-for-boolean-field
                            entity.put("CreatedAtDateTimeUtc", entityTypeDefinitions.get(i).getCreatedAtDateTimeUtc().toString());
                            entity.put("CreatedByInformationSystemUserId", entityTypeDefinitions.get(i).getCreatedByInformationSystemUserId());
                            entity.put("UpdatedAtDateTimeUtc", entityTypeDefinitions.get(i).getUpdatedAtDateTimeUtc().toString());
                            entity.put("UpdatedByInformationSystemUserId", entityTypeDefinitions.get(i).getUpdatedByInformationSystemUserId());
                            entity.put("DeletedAtDateTimeUtc", entityTypeDefinitions.get(i).getDeletedAtDateTimeUtc().toString());
                            entity.put("DeletedByInformationSystemUserId", entityTypeDefinitions.get(i).getDeletedByInformationSystemUserId());

                            entityData.addObject().put("EntityTypeDefinition", entity);
                        }
                    }

                    unpublishedEntityData.put("TotalRows", entityData.size());
                    unpublishedEntityData.put("AsOfDateTimeUtc", asOfDateTimeUtc.toString());
                    unpublishedEntityData.set("EntityData", entityData);
                    break;

                //NOTE: EntityTypeAttribute
                case 2:
                    unpublishedEntityData.put("EntityType", "EntityTypeAttribute");

                    for (int i = 0 ; i < entityTypeAttributes.size() ; i++)
                    {
                        //TODO: AAF-117 GetUnpublishedEntityData with live, not cached data???
                        //TODO: AAF-118 Capture and check cached data timestamp???
                        //TODO: AAF-119 Also check if updated since asOfDateTimeUtc???
                        if (entityTypeAttributes.get(i).getPublishedAtDateTimeUtc().equals(Timestamp.valueOf("9999-12-31 23:59:59.999")))
                        {
                            entity= objectMapper.createObjectNode();

                            entity.put("Id", entityTypeAttributes.get(i).getId());
                            entity.put("EntitySubtypeId", entityTypeAttributes.get(i).getEntitySubtypeId());
                            entity.put("TextKey", entityTypeAttributes.get(i).getTextKey());
                            entity.put("LocalizedName", entityTypeAttributes.get(i).getLocalizedName());
                            entity.put("LocalizedDescription", entityTypeAttributes.get(i).getLocalizedDescription());
                            entity.put("LocalizedAbbreviation", entityTypeAttributes.get(i).getLocalizedAbbreviation());
                            entity.put("LocalizedInformation", entityTypeAttributes.get(i).getLocalizedInformation());
                            entity.put("LocalizedPlaceholder", entityTypeAttributes.get(i).getLocalizedPlaceholder());
                            entity.put("IsLocalizable", entityTypeAttributes.get(i).isIsLocalizable());
                            entity.put("IsToBeAssociatedWithEachEntityTypeDefinition", entityTypeAttributes.get(i).isIsLocalizable());
                            entity.put("GeneralizedDataTypeEntitySubtypeId", entityTypeAttributes.get(i).getGeneralizedDataTypeEntitySubtypeId());
                            entity.put("DataSizeOrMaximumLengthInBytesOrCharacters", entityTypeAttributes.get(i).getDataSizeOrMaximumLengthInBytesOrCharacters());
                            entity.put("DataPrecision", entityTypeAttributes.get(i).getDataPrecision());
                            entity.put("DataScale", entityTypeAttributes.get(i).getDataScale());
                            entity.put("KeyTypeEntitySubtypeId", entityTypeAttributes.get(i).getKeyTypeEntitySubtypeId());
                            entity.put("RelatedEntityTypeId", entityTypeAttributes.get(i).getRelatedEntityTypeId());
                            entity.put("RelatedEntityTypeAttributeId", entityTypeAttributes.get(i).getRelatedEntityTypeAttributeId());
                            entity.put("RelatedEntityTypeCardinalityEntitySubtypeId", entityTypeAttributes.get(i).getRelatedEntityTypeCardinalityEntitySubtypeId());
                            entity.put("EntitySubtypeGroupKey", entityTypeAttributes.get(i).getEntitySubtypeGroupKey());
                            entity.put("ValueEntitySubtypeId", entityTypeAttributes.get(i).getValueEntitySubtypeId());
                            entity.put("DefaultValue", entityTypeAttributes.get(i).getDefaultValue());
                            entity.put("MinimumValue", entityTypeAttributes.get(i).getMinimumValue());
                            entity.put("MaximumValue", entityTypeAttributes.get(i).getMaximumValue());
                            entity.put("RegExValidationPattern", entityTypeAttributes.get(i).getRegExValidationPattern());
                            entity.put("StepIncrementValue", entityTypeAttributes.get(i).getStepIncrementValue());
                            entity.put("RemoteValidationMethodAsAjaxUri", entityTypeAttributes.get(i).getRemoteValidationMethodAsAjaxUri());
                            entity.put("IndexEntitySubtypeId", entityTypeAttributes.get(i).getIndexEntitySubtypeId());
                            entity.put("UniquenessEntitySubtypeId", entityTypeAttributes.get(i).getUniquenessEntitySubtypeId());
                            entity.put("SensitivityEntitySubtypeId", entityTypeAttributes.get(i).getSensitivityEntitySubtypeId());
                            entity.put("PublishedAtDateTimeUtc", entityTypeAttributes.get(i).getPublishedAtDateTimeUtc().toString());
                            entity.put("PublishedByInformationSystemUserId", entityTypeAttributes.get(i).getPublishedByInformationSystemUserId());
                            entity.put("Ordinal", entityTypeAttributes.get(i).getOrdinal());
                            entity.put("IsActive", entityTypeAttributes.get(i).isIsActive());    //NOTE: See https://stackoverflow.com/questions/42619986/lombok-annotation-getter-for-boolean-field
                            entity.put("CreatedAtDateTimeUtc", entityTypeAttributes.get(i).getCreatedAtDateTimeUtc().toString());
                            entity.put("CreatedByInformationSystemUserId", entityTypeAttributes.get(i).getCreatedByInformationSystemUserId());
                            entity.put("UpdatedAtDateTimeUtc", entityTypeAttributes.get(i).getUpdatedAtDateTimeUtc().toString());
                            entity.put("UpdatedByInformationSystemUserId", entityTypeAttributes.get(i).getUpdatedByInformationSystemUserId());
                            entity.put("DeletedAtDateTimeUtc", entityTypeAttributes.get(i).getDeletedAtDateTimeUtc().toString());
                            entity.put("DeletedByInformationSystemUserId", entityTypeAttributes.get(i).getDeletedByInformationSystemUserId());

                            entityData.addObject().put("EntityTypeAttribute", entity);
                        }
                    }

                    unpublishedEntityData.put("TotalRows", entityData.size());
                    unpublishedEntityData.put("AsOfDateTimeUtc", asOfDateTimeUtc.toString());
                    unpublishedEntityData.set("EntityData", entityData);
                    break;

                //NOTE: EntityTypeDefinitionEntityTypeAttributeAssociation
                case 3:
                    unpublishedEntityData.put("EntityType", "EntityTypeDefinitionEntityTypeAttributeAssociation");

                    for (int i = 0 ; i < entityTypeDefinitionEntityTypeAttributeAssociations.size() ; i++)
                    {
                        //TODO: AAF-117 GetUnpublishedEntityData with live, not cached data???
                        //TODO: AAF-118 Capture and check cached data timestamp???
                        //TODO: AAF-119 Also check if updated since asOfDateTimeUtc???
                        if (entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getPublishedAtDateTimeUtc().equals(Timestamp.valueOf("9999-12-31 23:59:59.999")))
                        {
                            entity= objectMapper.createObjectNode();

                            entity.put("Id", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getId());
                            entity.put("EntitySubtypeId", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getEntitySubtypeId());
                            entity.put("TextKey", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getTextKey());
                            entity.put("EntityTypeDefinitionId", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getEntityTypeDefinitionId());
                            entity.put("EntityTypeAttributeId", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getEntityTypeAttributeId());
                            entity.put("PublishedAtDateTimeUtc", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getPublishedAtDateTimeUtc().toString());
                            entity.put("PublishedByInformationSystemUserId", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getPublishedByInformationSystemUserId());
                            entity.put("Ordinal", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getOrdinal());
                            entity.put("IsActive", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).isIsActive());    //NOTE: See https://stackoverflow.com/questions/42619986/lombok-annotation-getter-for-boolean-field
                            entity.put("CreatedAtDateTimeUtc", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getCreatedAtDateTimeUtc().toString());
                            entity.put("CreatedByInformationSystemUserId", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getCreatedByInformationSystemUserId());
                            entity.put("UpdatedAtDateTimeUtc", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getUpdatedAtDateTimeUtc().toString());
                            entity.put("UpdatedByInformationSystemUserId", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getUpdatedByInformationSystemUserId());
                            entity.put("DeletedAtDateTimeUtc", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getDeletedAtDateTimeUtc().toString());
                            entity.put("DeletedByInformationSystemUserId", entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getDeletedByInformationSystemUserId());

                            entityData.addObject().put("EntityTypeDefinitionEntityTypeAttributeAssociation", entity);
                        }
                    }

                    unpublishedEntityData.put("TotalRows", entityData.size());
                    unpublishedEntityData.put("AsOfDateTimeUtc", asOfDateTimeUtc.toString());
                    unpublishedEntityData.set("EntityData", entityData);
                    break;

                //NOTE: EntitySubtype
                case 4:
                    unpublishedEntityData.put("EntityType", "EntitySubtype");

                    for (int i = 0 ; i < entitySubtypes.size() ; i++)
                    {
                        //TODO: AAF-117 GetUnpublishedEntityData with live, not cached data???
                        //TODO: AAF-118 Capture and check cached data timestamp???
                        //TODO: AAF-119 Also check if updated since asOfDateTimeUtc???
//                        if (entitySubtypes.get(i).getPublishedAtDateTimeUtc().equals(Timestamp.valueOf("9999-12-31 23:59:59.999")))
//                        {
//                            entity= objectMapper.createObjectNode();
//
//                            entity.put("Id", entitySubtypes.get(i).getId());
//                            entity.put("EntitySubtypeId", entitySubtypes.get(i).getEntitySubtypeId());
//                            entity.put("TextKey", entitySubtypes.get(i).getTextKey());
//                            entity.put("LocalizedName", entitySubtypes.get(i).getLocalizedName());
//                            entity.put("LocalizedDescription", entitySubtypes.get(i).getLocalizedDescription());
//                            entity.put("LocalizedAbbreviation", entitySubtypes.get(i).getLocalizedAbbreviation());
//                            entity.put("PublishedAtDateTimeUtc", entitySubtypes.get(i).getPublishedAtDateTimeUtc().toString());
//                            entity.put("PublishedByInformationSystemUserId", entitySubtypes.get(i).getPublishedByInformationSystemUserId());
//                            entity.put("Ordinal", entitySubtypes.get(i).getOrdinal());
//                            entity.put("IsActive", entitySubtypes.get(i).isIsActive());    //NOTE: See https://stackoverflow.com/questions/42619986/lombok-annotation-getter-for-boolean-field
//                            entity.put("CreatedAtDateTimeUtc", entitySubtypes.get(i).getCreatedAtDateTimeUtc().toString());
//                            entity.put("CreatedByInformationSystemUserId", entitySubtypes.get(i).getCreatedByInformationSystemUserId());
//                            entity.put("UpdatedAtDateTimeUtc", entitySubtypes.get(i).getUpdatedAtDateTimeUtc().toString());
//                            entity.put("UpdatedByInformationSystemUserId", entitySubtypes.get(i).getUpdatedByInformationSystemUserId());
//                            entity.put("DeletedAtDateTimeUtc", entitySubtypes.get(i).getDeletedAtDateTimeUtc().toString());
//                            entity.put("DeletedByInformationSystemUserId", entitySubtypes.get(i).getDeletedByInformationSystemUserId());
//
//                            entityData.addObject().put("EntitySubtype", entity);
//                        }
                    }

                    unpublishedEntityData.put("TotalRows", entityData.size());
                    unpublishedEntityData.put("AsOfDateTimeUtc", asOfDateTimeUtc.toString());
                    unpublishedEntityData.set("EntityData", entityData);
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
            logger.debug("Attempting to GetCachedEntityTypeDefinitionById() for Id = " + id);

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

        logger.debug("GetCachedEntityTypeDefinitionById succeeded");
        return entityTypeDefinition;
    }

    private EntityTypeAttribute GetCachedEntityTypeAttributeById(long id)
    {
        EntityTypeAttribute entityTypeAttribute = null;

        try
        {
            logger.debug("Attempting to GetCachedEntityTypeAttributeById() for Id = " + id);

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

        logger.debug("GetCachedEntityTypeAttributeById succeeded");
        return entityTypeAttribute;
    }

    private EntitySubtype GetCachedEntitySubtypeById(long id)
    {
        EntitySubtype entitySubtype = null;

        try
        {
            logger.debug("Attempting to GetCachedEntitySubtypeById() for Id = " + id);

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

        logger.debug("GetCachedEntitySubtypeById succeeded");
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

    //NOTE: Private utility for reading specified business entity data from the EDM in processes with multiple read and/or write operations in order to avoid code duplication
    //NOTE: Internal use only as the headers, apiKey, cookie, and other required values have already been checked, encoded, etc in the public method and passed in
    private ResponseEntity<String> ReadBusinessEntityData(String entityTypeName, Instant asOfDateTimeUtc, HttpHeaders headers, String apiKey, HttpCookie authenticationJwt, String correlationUuid, Long userId, String whereClause, ServerWebExchange exchange) throws Exception
    {
        HttpComponentsClientHttpRequestFactory requestFactory = null;
//        RestTemplateBuilder restTemplateBuilder = null;
        RestTemplate restTemplate = null;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = null;
        HttpEntity<String> entity = null;
        ResponseEntity<String> readActionResponseBody = null;

        try
        {
            logger.debug("Attempting to ReadBusinessEntityData() for '{}'", entityTypeName);

            ENVIRONMENT_JWT_SHARED_SECRET = environment.getProperty("environmentJwtSharedSecret");

            if ((apiKey == null) || (apiKey.length() < 1))
            {
                throw new Exception("No 'ApiKey' header specified");
            }
            else
            {
                logger.debug("ApiKey header '{}' specified", apiKey);
            }

            if ((correlationUuid == null) || (correlationUuid.length() < 1))
            {
//                correlationUuid = UUID.randomUUID().toString();
                throw new Exception("No 'CorrelationUuid' value specified");
            }
            else
            {
                logger.debug("CorrelationUuid '{}' specified", correlationUuid);
            }

            //TODO: AAF-87 Implement fixes for RestTemplateBuilder
//            restTemplateBuilder = new RestTemplateBuilder();
//            restTemplate = restTemplate;

            restTemplate = new RestTemplate();
            requestFactory = new HttpComponentsClientHttpRequestFactory();
//                requestFactory.setConnectTimeout(TIMEOUT);
//                requestFactory.setReadTimeout(TIMEOUT);
            restTemplate.setRequestFactory(requestFactory);

            readActionResponseBody = restTemplate.exchange("http://localhost:8080/entityTypes/" + entityTypeName + "?whereClause=" + whereClause, HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        }
        catch (Exception e)
        {
            logger.error("ReadBusinessEntityData() for '{}' failed due to: ", entityTypeName, e);
            //TODO: AAF-81 Improve this error output???
            return new ResponseEntity<String>("{[]}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.debug("ReadBusinessEntityData() succeeded for '{}'", entityTypeName);
        //result = new JSONObject("{\"EntityData\":" + entityData + "}");
        //return "{\"EntityData\":" + entityData + "}";
        //TODO: AAF-82 Echo input parameters in Postgres function return JSON
        return readActionResponseBody;
    }

    //NOTE: Private utility for reading specified business entity data from the EDM in processes with multiple read and/or write operations in order to avoid code duplication
    //NOTE: Internal use only as the headers, apiKey, cookie, and other required values have already been checked in the public method and passed in
    private ResponseEntity<String> WriteBusinessEntityData(String entityTypeName, Instant asOfDateTimeUtc, HttpHeaders headers, String apiKey, HttpCookie authenticationJwt, String correlationUuid, Long userId, String requestBody, ServerWebExchange exchange) throws Exception
    {
        String writeActionRequestBody = "";
        HttpComponentsClientHttpRequestFactory requestFactory = null;
//        RestTemplateBuilder restTemplateBuilder = null;
        RestTemplate restTemplate = null;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = null;
        HttpEntity<String> entity = null;
        ResponseEntity<String> writeActionResponseBody = null;

        try
        {
            logger.debug("Attempting to WriteBusinessEntityData() for '{}'", entityTypeName);

            ENVIRONMENT_JWT_SHARED_SECRET = environment.getProperty("environmentJwtSharedSecret");

            if ((apiKey == null) || (apiKey.length() < 1))
            {
                throw new Exception("No 'apiKey' parameter specified");
            }
            else
            {
                logger.debug("'apiKey' parameter '{}' specified", apiKey);
            }

            if ((correlationUuid == null) || (correlationUuid.length() < 1))
            {
                throw new Exception("No 'correlationUuid' parameter specified");
            }
            else
            {
                logger.debug("'correlationUuid' parameter '{}' specified", correlationUuid);
            }

            if (requestBody.length() < 1)
            {
                throw new Exception("No 'requestBody' parameter specified");
            }
            else
            {
                logger.debug("'requestBody' parameter '{}' specified", requestBody);
                writeActionRequestBody = requestBody;
            }

            entity = new HttpEntity<String>(writeActionRequestBody, headers);

            //TODO: AAF-87 Implement fixes for RestTemplateBuilder
//            restTemplateBuilder = new RestTemplateBuilder();
//            restTemplate = restTemplate;

            restTemplate = new RestTemplate();
            requestFactory = new HttpComponentsClientHttpRequestFactory();
//                requestFactory.setConnectTimeout(TIMEOUT);
//                requestFactory.setReadTimeout(TIMEOUT);
            restTemplate.setRequestFactory(requestFactory);

            writeActionResponseBody = restTemplate.postForEntity("http://localhost:8080/entityTypes/" + entityTypeName, entity, String.class);
        }
        catch (Exception e)
        {
            logger.error("WriteBusinessEntityData() for '{}' failed due to: ", entityTypeName, e);
            //TODO: AAF-81 Improve this error output???
            return new ResponseEntity<String>("{[]}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.debug("WriteBusinessEntityData() succeeded for '{}'", entityTypeName);
        //result = new JSONObject("{\"EntityData\":" + entityData + "}");
        //return "{\"EntityData\":" + entityData + "}";
        //TODO: AAF-82 Echo input parameters in Postgres function return JSON
//        return new ResponseEntity<String>(entityData.toString(), HttpStatus.CREATED);
        return writeActionResponseBody;
    }
}