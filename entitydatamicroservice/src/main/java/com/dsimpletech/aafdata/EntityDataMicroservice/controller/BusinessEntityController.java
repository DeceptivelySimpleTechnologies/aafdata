package com.dsimpletech.aafdata.EntityDataMicroservice.controller;


import com.dsimpletech.aafdata.EntityDataMicroservice.database.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

//import org.json.*;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
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

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


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

    private CallableStatement statement = null;

    private ArrayList<EntityTypeDefinition> entityTypeDefinitions = null;
    private ArrayList<EntityTypeAttribute> entityTypeAttributes = null;
    private ArrayList<EntityTypeDefinitionEntityTypeAttributeAssociation> entityTypeDefinitionEntityTypeAttributeAssociations = null;
    private ArrayList<EntitySubtype> entitySubtypes = null;

    //NOTE: Spring Boot Logback default logging implemented per https://www.baeldung.com/spring-boot-logging
    Logger logger = LoggerFactory.getLogger(BusinessEntityController.class);

    //NOTE: Implement this
    @PostConstruct
    private void GetEntityData() {

        try {
            logger.info("Attempting to GetEntityData()");

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
            logger.error("GetEntityData() failed due to: " + e);
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
                logger.error("Failed to close resultset and/or statement resource in GetEntityData() due to: " + e);
            }
        }

            logger.info("GetEntityData() succeeded");
    }

    //NOTE: Implement this
    @PreDestroy
    private void DeleteEntityData() {
        entityTypeDefinitions = null;
        entityTypeAttributes = null;
        entityTypeDefinitionEntityTypeAttributeAssociations = null;
        entitySubtypes = null;
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
    @PostMapping(value = "/{entityTypeName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> PostBusinessEntity(@PathVariable("entityTypeName") String entityTypeName, ServerWebExchange exchange) throws Exception
    {
        ServerHttpRequest request = null;
//        MultiValueMap<String,String> queryParams = null;

        int entityTypeId = -1;
        ArrayList<Integer> entityTypeAssociations = null;

        String[] sqlBlacklistValues = null;
        String errorValues = "";

        String requestHeader = "";

        HttpCookie authenticationJwt = null;
        Base64.Decoder decoderBase64 = Base64.getUrlDecoder();
        String[] authenticationJwtSections = null;
        JsonNode authenticationJwtHeader = null;
        JsonNode authenticationJwtPayload = null;
        String authenticationJwtSignature = "";
        int userId = -1;

        Flux<DataBuffer> requestBody = null;
        Mono<String> requestBodyData = null;
        String[] bodyJwtSections = null;
        ObjectMapper objectMapper = null;
        JsonNode bodyJwtHeader = null;
        JsonNode bodyJwtPayload = null;
        String bodyJwtSignature = "";

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
            logger.info("Attempting to PostBusinessEntity() for " + entityTypeName);

            request = exchange.getRequest();
//            queryParams = request.getQueryParams();

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

            //NOTE: Example request http://localhost:8080/Person with "Authentication" JWT and JWT request body
            //NOTE: https://learning.postman.com/docs/sending-requests/response-data/cookies/
//            Authentication JWT: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0ZWQiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiJlZjRhZjRlMy1lNzM2LTQyNWEtYWFmZi1lY2EwM2I3YjliMjgiLCJib2R5Ijp7IkVtYWlsQWRkcmVzcyI6ImFteS5hbmRlcnNvbkBhbXlzYWNjb3VudGluZy5jb20ifX0.Djq5LYPEK1QFgBk9aN5Vei37K6Cb8TxNH3ADWDcUaHs
//            Request JWT: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IlBPU1QgL0VudGl0eVR5cGUiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiI4NzUyZjIzYi0xYTliLTQyMmEtOGIyNi0zNzQyNDM0ZGY0NzYiLCJib2R5Ijp7IkVudGl0eVN1YnR5cGVJZCI6LTEsIlRleHRLZXkiOiJwZXJzb24tbm9uZS1iaWxsLWJha2VyIiwiTGVnYWxHaXZlbk5hbWUiOiJCaWxsIiwiTGVnYWxTdXJuYW1lIjoiQmFrZXIiLCJCb3JuQXREYXRlVGltZVV0YyI6IjIwMDItMDItMDMgMTE6MTI6MTMuMTIzIiwiTGVnYWxDaXRpemVuT2ZDb3VudHJ5R2VvZ3JhcGhpY1VuaXRJZCI6MSwiTG9jYWxlSWQiOjEsIk9yZGluYWwiOi0xLCJJc0FjdGl2ZSI6dHJ1ZX19.rWNowmEoPkF8N0Q5KC5-W83g3hMqIf9TV8KHzLgNbio

            //TODO: Validate JWT
            requestHeader = exchange.getRequest().getHeaders().getFirst("Temp-Body");
            bodyJwtSections = requestHeader.split("\\.");
            objectMapper = new ObjectMapper();
            bodyJwtHeader = objectMapper.readTree(decoderBase64.decode(bodyJwtSections[0]));
            bodyJwtPayload = objectMapper.readTree(decoderBase64.decode(bodyJwtSections[1]));
            //TODO: Validate JWT signature per https://www.baeldung.com/java-jwt-token-decode

            logger.info(("Request from '" + bodyJwtPayload.get("iss").asText() + "' for '" + bodyJwtPayload.get("aud").asText() + "' using key '" + bodyJwtHeader.get("kid").asText() + "'"));

            //TODO: Validate JWT
            authenticationJwt = request.getCookies().getFirst("Authentication");
            authenticationJwtSections = authenticationJwt.getValue().split("\\.");
            authenticationJwtHeader = objectMapper.readTree(decoderBase64.decode(authenticationJwtSections[0]));
            authenticationJwtPayload = objectMapper.readTree(decoderBase64.decode(authenticationJwtSections[1]));
            //TODO: Validate JWT signature per https://www.baeldung.com/java-jwt-token-decode

            logger.info(("Requested by '" + authenticationJwtPayload.get("body").get("EmailAddress").asText() + "' using key '" + authenticationJwtHeader.get("kid").asText() + "'"));

            //TODO: Look up InformationSystemUser.Id using the authenticated user's EmailAddress in the Authentication JWT payload, and assign it below
            userId = -100;

            //TODO: Check user's role(s) and permissions for this operation

            sqlBlacklistValues = environment.getProperty("sqlNotToAllow").toLowerCase().split(",");

            //TODO: *** Only check request body for SQL injection
//            errorValues = GuardAgainstSqlIssues(queryParams.toString(), sqlBlacklistValues);

//            requestBody = request.getBody();
//            requestBodyData = DataBufferUtils.join(requestBody).map(dataBuffer -> {
//                byte[] bytes = new byte[dataBuffer.readableByteCount()];
//                dataBuffer.read(bytes);
//                DataBufferUtils.release(dataBuffer);
//                return bytes.toString();
//            });
//
//            bodyJwtSections = requestBodyData.toString().split("\\.");
//            bodyJwtHeader = new String(decoderBase64.decode(bodyJwtSections[0]));
//            bodyJwtPayload = new String(decoderBase64.decode(bodyJwtSections[1]));
//            bodyJwtSignature = new String(decoderBase64.decode(bodyJwtSections[2]));
            //TODO: Validate JWT signature per https://www.baeldung.com/java-jwt-token-decode

            //NOTE: Get the Id of the requested entityTypeName
            for (int i = 0 ; i < entityTypeDefinitions.size() ; i++)
            {
                if (entityTypeDefinitions.get(i).getLocalizedName().equals(entityTypeName))
                {
                    entityTypeId = entityTypeDefinitions.get(i).getId();
                    break;
                }
            }

            if (entityTypeId == -1)
            {
                throw new Exception("Invalid 'entityTypeName' query parameter '" + entityTypeName + "'");
            }

            //NOTE: Get the Ids of any associated EntityTypeAttributes
            entityTypeAssociations = new ArrayList<Integer>();

            for (int i = 0 ; i < entityTypeDefinitionEntityTypeAttributeAssociations.size() ; i++)
            {
                if (entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getEntityTypeDefinitionId() == entityTypeId)
                {
                    entityTypeAssociations.add(entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getEntityTypeAttributeId());
                }
            }

            if (entityTypeAssociations.size() == 0)
            {
                throw new Exception("No associated EntityTypeAttributes for 'entityTypeName' query parameter '" + entityTypeName + "'");
            }

            //NOTE: Build the insertClause using the associated EntityTypeAttributes LocalizedNames, while filtering out any entityTypeAttributesNeverToReturn
            //NOTE: insertClause = "\"Id\",\"LocalizedName\"";
            entityTypeAttributesNeverToReturn = environment.getProperty("entityTypeAttributesNeverToReturn").split(",");

            for (int i = 0 ; i < entityTypeAttributes.size() ; i++)
            {
                if (entityTypeAssociations.contains(entityTypeAttributes.get(i).getId())) {
                    insertClause = insertClause + "\"" + entityTypeAttributes.get(i).getLocalizedName() + "\",";

                    switch (entityTypeAttributes.get(i).getEntityTypeAttributeValueEntitySubtypeId()) {
                        //NOTE: PrimaryKey, Uuid, CreatedDateTime, CreatedByUserId, UpdatedDateTime, UpdatedByUserId, DeletedDateTime, DeletedByUserId, CorrelationUuid, Digest
                        case 23, 24, 27, 28, 29, 30, 31, 32, 33, 34:
                            if (bodyJwtPayload.get("body").has(entityTypeAttributes.get(i).getLocalizedName())) {
                                //NOTE: System-generated (server-side) values
                                logger.warn(("Attempt to specify system-generated '" + entityTypeAttributes.get(i).getLocalizedName() + "' value as " + authenticationJwtPayload.get("body").get(entityTypeAttributes.get(i).getLocalizedName()).asText() + "'"));
                            }
                            break;

                        //NOTE: DefaultTextKey
                        //TODO: Log non-pattern matching provided TextKey values
                        case 25:
                            //NOTE: Optional value
                            if (bodyJwtPayload.get("body").has("TextKey"))
                            {
                                switch (entityTypeAttributes.get(i).getGeneralizedDataTypeEntitySubtypeId()) {
                                    //NOTE: Boolean, Integer, Decimal
                                    case 10, 11, 16:
                                        //TODO: Find and replace "magic", hard-coded values, e.g. "TextKey"
                                        insertValues = insertValues + bodyJwtPayload.get("body").get("TextKey").asText() + ",";
                                        break;
                                    //NOTE: UnicodeCharacter, UnicodeString, DateTime
                                    case 12, 13, 14:
                                        insertValues = insertValues + "'" + bodyJwtPayload.get("body").get("TextKey").asText() + "',";
                                        break;
                                }
                            }
                            else
                            {
                                switch (entityTypeAttributes.get(i).getLocalizedName()){
                                    //NOTE: EntityTypes with LocalizedName attribute
                                    case "EntityTypeDefinition", "EntityTypeAttribute", "EntityType", "EntitySubtype", "GeographicUnit", "Language", "LegalEntity":
                                        insertValues = insertValues + "'" + entityTypeAttributes.get(i).getLocalizedName().toLowerCase() + "-" + entitySubtypes.get(bodyJwtPayload.get("body").get("EntitySubtypeId").asInt()).getLocalizedName().toLowerCase() + "-" + bodyJwtPayload.get("body").get("LocalizedName").asText().toLowerCase() + "',";
                                        break;
                                    //NOTE: EntityTypes with StandardizedName attribute
                                    case "Locale":
                                        insertValues = insertValues + "'" + entityTypeAttributes.get(i).getLocalizedName().toLowerCase() + "-" + entitySubtypes.get(bodyJwtPayload.get("body").get("EntitySubtypeId").asInt()).getLocalizedName().toLowerCase() + "-" + bodyJwtPayload.get("body").get("StandardizedName").asText().toLowerCase() + "',";
                                        break;
                                    //NOTE: EntityTypes with LegalName attribute
                                    case "Organization", "OrganizationalUnit":
                                        insertValues = insertValues + "'" + entityTypeAttributes.get(i).getLocalizedName().toLowerCase() + "-" + entitySubtypes.get(bodyJwtPayload.get("body").get("EntitySubtypeId").asInt()).getLocalizedName().toLowerCase() + "-" + bodyJwtPayload.get("body").get("LegalName").asText().toLowerCase() + "',";
                                        break;
                                    //NOTE: EntityTypes with LegalName attribute
                                    case "LegalGivenName", "LegalSurname":
                                        insertValues = insertValues + "'" + entityTypeAttributes.get(i).getLocalizedName().toLowerCase() + "-" + entitySubtypes.get(bodyJwtPayload.get("body").get("EntitySubtypeId").asInt()).getLocalizedName().toLowerCase() + "-" + bodyJwtPayload.get("body").get("LegalSurname").asText().toLowerCase() + "-" + bodyJwtPayload.get("body").get("LegalGivenName").asText().toLowerCase() + "',";
                                        break;
                                    default:
                                        insertValues = insertValues + "'" + entityTypeAttributes.get(i).getLocalizedName().toLowerCase() + "-" + entitySubtypes.get(bodyJwtPayload.get("body").get("EntitySubtypeId").asInt()).getLocalizedName().toLowerCase() + "-" + new Random().ints(97, 123)
                                                                                                                                                                                                                                                                        .limit(5)
                                                                                                                                                                                                                                                                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                                                                                                                                                                                                                                                        .toString() + "',";
                                        break;
                                }
                            }
                            break;
                        //NOTE: UseDefault
                        case 26:
                            if (bodyJwtPayload.get("body").has(entityTypeAttributes.get(i).getLocalizedName())) {
                                switch (entityTypeAttributes.get(i).getGeneralizedDataTypeEntitySubtypeId()) {
                                    //NOTE: Boolean, Integer, Decimal
                                    case 10, 11, 16:
                                        insertValues = insertValues + bodyJwtPayload.get("body").get(entityTypeAttributes.get(i).getLocalizedName()).asText() + ",";
                                        break;
                                    //NOTE: UnicodeCharacter, UnicodeString, DateTime
                                    case 12, 13, 14:
                                        insertValues = insertValues + "'" + bodyJwtPayload.get("body").get(entityTypeAttributes.get(i).getLocalizedName()).asText() + "',";
                                        break;
                                }
                            }
                            else {
                                switch (entityTypeAttributes.get(i).getGeneralizedDataTypeEntitySubtypeId()) {
                                    //NOTE: Boolean, Integer, Decimal
                                    case 10, 11, 16:
                                        insertValues = insertValues + entityTypeAttributes.get(i).getDefaultValue() + ",";
                                        break;
                                    //NOTE: UnicodeCharacter, UnicodeString, DateTime
                                    case 12, 13, 14:
                                        insertValues = insertValues + "'" + entityTypeAttributes.get(i).getDefaultValue() + "',";
                                        break;
                                }
                            }
                            break;
                        //NOTE: Required value
                        default:
                            if (bodyJwtPayload.get("body").has(entityTypeAttributes.get(i).getLocalizedName())) {
                                switch (entityTypeAttributes.get(i).getGeneralizedDataTypeEntitySubtypeId()) {
                                    //NOTE: Boolean, Integer, Decimal
                                    case 10, 11, 16:
                                        insertValues = insertValues + bodyJwtPayload.get("body").get(entityTypeAttributes.get(i).getLocalizedName()).asText() + ",";
                                        break;
                                    //NOTE: UnicodeCharacter, UnicodeString, DateTime
                                    case 12, 13, 14:
                                        insertValues = insertValues + "'" + bodyJwtPayload.get("body").get(entityTypeAttributes.get(i).getLocalizedName()).asText() + "',";
                                        break;
                                }
                            }
                            else
                            {
                                throw new Exception("Required EntityTypeAttribute '" + entityTypeAttributes.get(i).getLocalizedName() + "' not found in request body");
                            }
                            break;
                    }
                }

                if (entityTypeAssociations.contains(entityTypeAttributes.get(i).getId()) && !Arrays.asList(entityTypeAttributesNeverToReturn).contains(entityTypeAttributes.get(i).getLocalizedName()))
                {
                    selectClause = selectClause + "\"" + entityTypeAttributes.get(i).getLocalizedName() + "\",";
                }
            }

            if (insertClause.length() > 0){
                insertClause = insertClause.substring(0, insertClause.length() - 1);
            }

            if (insertValues.length() > 0){
                insertValues = insertValues.substring(0, insertValues.length() - 1);
            }

            if (selectClause.length() > 0){
                selectClause = selectClause.substring(0, selectClause.length() - 1);
            }

            //TODO: *** Get UTC time in Postgres function (currently getting local) for Create, Update, and Delete operations

            //TODO: Since EntityDataCreate() is in public, ensure that is locked down to correct role(s) only
            //TODO: Refactor the statements below to be reusable for validation, local caching, etc
            if (errorValues.length() == 0)
            {
                statement = connection.prepareCall("{call \"EntityDataCreate\"(?,?,?,?,?,?,?)}");
                statement.setString(1, entityTypeName);
                statement.setString(2, insertClause);
                statement.setString(3, insertValues);
                statement.setString(4, selectClause);
                statement.setObject(5, bodyJwtPayload.get("jti").asText(), Types.OTHER);
                statement.setLong(6, userId);

                //NOTE: Register the data OUT parameter before calling the stored procedure
                statement.registerOutParameter(7, Types.LONGVARCHAR);
                statement.executeUpdate();

                //NOTE: Read the OUT parameter now
                entityData = statement.getString(7);

                if (entityData == null)
                {
                    //TODO: Improve this error output???
                    entityData = "{[]}";
                }

                //TODO: Filter or mask unauthorized or sensitive attributes for this InformationSystemUserRole (as JSON)???
            }
        }
        catch (Exception e)
        {
            logger.error("PostBusinessEntity() failed due to: " + e);
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
                logger.error("Failed to close statement and/or connection resource in PostBusinessEntity() due to: " + e);
            }
        }

        logger.info("PostBusinessEntity() succeeded for " + entityTypeName);
        //result = new JSONObject("{\"EntityData\":" + entityData + "}");
        //return "{\"EntityData\":" + entityData + "}";
        //TODO: Echo input parameters in Postgres function return JSON
        return new ResponseEntity<String>(entityData, HttpStatus.OK);
    }

    @GetMapping(value = "/{entityTypeName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> GetBusinessEntities(@PathVariable("entityTypeName") String entityTypeName, @RequestParam(defaultValue = "") String whereClause, @RequestParam(defaultValue = "") String sortClause, @RequestParam(defaultValue = "#{T(java.time.Instant).now()}") Instant asOfDateTimeUtc, @RequestParam(defaultValue = "1") long graphDepthLimit, @RequestParam(defaultValue = "1") long pageNumber, @RequestParam(defaultValue = "20") long pageSize, ServerWebExchange exchange) throws Exception
    {
        ServerHttpRequest request = null;
        MultiValueMap<String,String> queryParams = null;

        int entityTypeId = -1;
        ArrayList<Integer> entityTypeAssociations = null;

        String[] sqlBlacklistValues = null;
        String errorValues = "";

//        MultiValueMap<String, HttpCookie> authenticationCookie = null;

        String[] entityTypeAttributesNeverToReturn = null;

        String selectClause = "";

        String entityData = "";

        //NOTE: In Postgres function, if possible change ALTER FUNCTION public."EntityDataRead"(character varying) OWNER TO postgres to GRANT
        //NOTE: Add ReadWrite and ReadOnly roles, and remove direct table access with SQL GRANTs; CRUD only enforced upstream through process service calls

        //NOTE: Add automation batch script at infrastructure root
        //NOTE: Add uniqueness constraints to table/model scripts
        //NOTE: *** Add indexes to name, association id, and parent/child id columns in table/model scripts
        //TODO: *** Implement Delete
        //TODO: *** Finish Swagger/OpenAPI documentation (try /entities/{entityTypeName}...)
        //NOTE: *** Finish health check

        //TODO: *** Finish README.md
        //TODO: *** Unit testing
        //TODO: *** Security testing (database table direct access, etc)
        //TODO: *** Dockerfile with AWS CloudFront logging driver, etc
        //TODO: *** Add EDM image to DockerHub

        //TODO: ** Set up Terraform Remote Backend With AWS Using A Bash Script
        //TODO: ** Set up JWT infrastructure with request body in token

        //TODO: * Test performance and possibly add indexes to Uuid, EntitySubtypeId, and TextKey columns in table/model scripts
        //TODO: * Extend health check per https://reflectoring.io/spring-boot-health-check/

        //NOTE: Test pagination (currently only 1 at a time, not pageNumber x pageSize)

        //TODO: Validate API key upstream in Client Communication Service (CCS)
        //TODO: Validate JWT upstream in Client Communication Service (CCS)
        //TODO: Validate authenticated user and API key OrganizationalUnit association if Employee upstream in Client Communication Service (CCS)
        //TODO: Validate entity data authorization, i.e. appropriate permissions for requested entity and operation? upstream in Client Communication Service (CCS)
        //TODO: Add Organization/OrganizationalUnit filtering to authorization/permissioning (what about non-Employees??? only *my* stuff???)
        //TODO: Implement a pipeline/plugin architecture to replace system behavior like JWT communication, etc

        //TODO: Implement a global error handler for all exceptions with JSON return, per:
        //  https://bootcamptoprod.com/spring-boot-no-explicit-mapping-error-handling/#:~:text=them%20in%20detail.-,Solution%2D1%3A%20Request%20Mapping%20and%20Controller,in%20one%20of%20your%20controllers.
        //  https://skryvets.com/blog/2018/12/27/enhance-exception-handling-when-building-restful-api-with-spring-boot/

        //NOTE: Rather than validating the EntityTypeDefinition name, we're going to optimistically pass it through to the database if it returns attributes
        //NOTE: We don't request versions our business entity data structure explicitly in the base URL; instead our explicit (v1.2.3) versioning is maintained internally, based on/derived from the AsOfUtcDateTime query parameter
        //NOTE: Since we're not automatically parsing the resulting JSON into an object, we're returning a JSON String rather than a JSONObject
        try
        {
            logger.info("Attempting to GetBusinessEntities() for " + entityTypeName);

            request = exchange.getRequest();
            queryParams = request.getQueryParams();

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

            //NOTE: Example request http://localhost:8080/EntityType?whereClause=%22Id%22%20%3E%20-2&sortClause=%22Ordinal%22%252C%22Id%22&asOfDateTimeUtc=2023-01-01T00:00:00.000Z&graphDepthLimit=1&pageNumber=1&pageSize=20

            //TODO: Add Unknown and None to EntityTypeDefinition data???

            sqlBlacklistValues = environment.getProperty("sqlNotToAllow").toLowerCase().split(",");
            //TODO: *** Only check whereClause and sortClause for SQL injection, not other query parameters
            errorValues = GuardAgainstSqlIssues(queryParams.toString(), sqlBlacklistValues);

//            authenticationCookie = (MultiValueMap<String, HttpCookie>) exchange.getRequest().getCookies().getFirst("Authentication");

            //NOTE: Build selectClause, based on EntityTypeDefinition.LocalizedName > EntityTypeAttribute.LocalizedName
            //NOTE: Build query parameter array while validating explicit filter criteria and logging invalid attribute names, etc to be returned (see Create GET Method AAF-48)
            //NOTE: Use GetBusinessEntities() to get attributes for specified EntityTypeDefinition
            //NOTE: Remove attributes that should never be returned, e.g. Digest, from selectClause
            //NOTE: Use GetBusinessEntities() to cache EntityTypeDefinition, EntityTypeAttribute, and EntityTypeDefinitionEntityTypeAttributeAssociation for input validation at service startup

            //NOTE: Nothing returned for GeographicUnitHierarchy, Language, Locale, Person, OrganizationalUnitHierarchy, Employee,
            //NOTE: Error on OrganizationalUnit: "PersonId" does not exist (now "LocalizedName")
            //NOTE: No associated EntityTypeAttributes for 'entityTypeName' query parameter 'LegalEntity'
            //TODO: *** Error when whereClause removed from query string

            //NOTE: Get the Id of the requested entityTypeName
            for (int i = 0 ; i < entityTypeDefinitions.size() ; i++)
            {
                if (entityTypeDefinitions.get(i).getLocalizedName().equals(entityTypeName))
                {
                    entityTypeId = entityTypeDefinitions.get(i).getId();
                    break;
                }
            }

            if (entityTypeId == -1)
            {
                throw new Exception("Invalid 'entityTypeName' query parameter '" + entityTypeName + "'");
            }

            //NOTE: Get the Ids of any associated EntityTypeAttributes
            entityTypeAssociations = new ArrayList<Integer>();

            for (int i = 0 ; i < entityTypeDefinitionEntityTypeAttributeAssociations.size() ; i++)
            {
                if (entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getEntityTypeDefinitionId() == entityTypeId)
                {
                    entityTypeAssociations.add(entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getEntityTypeAttributeId());
                }
            }

            if (entityTypeAssociations.size() == 0)
            {
                throw new Exception("No associated EntityTypeAttributes for 'entityTypeName' query parameter '" + entityTypeName + "'");
            }

            //NOTE: Build the selectClause using the associated EntityTypeAttributes LocalizedNames, while filtering out any entityTypeAttributesNeverToReturn
            //NOTE: selectClause = "\"Id\",\"LocalizedName\"";
            entityTypeAttributesNeverToReturn = environment.getProperty("entityTypeAttributesNeverToReturn").split(",");

            for (int i = 0 ; i < entityTypeAttributes.size() ; i++)
            {
                if (entityTypeAssociations.contains(entityTypeAttributes.get(i).getId()) && !Arrays.asList(entityTypeAttributesNeverToReturn).contains(entityTypeAttributes.get(i).getLocalizedName()))
                {
                    selectClause = selectClause + "\"" + entityTypeAttributes.get(i).getLocalizedName() + "\",";
                }
            }

            if (selectClause.length() > 0){
                selectClause = selectClause.substring(0, selectClause.length() - 1);
            }

            //NOTE: Validate and sanitize whereClause
            if (whereClause.contains("WHERE"))
            {
                throw new Exception("'whereClause' query parameter must not include 'WHERE'.");
            }

            if (whereClause.length() > 0)
            {
                whereClause = "WHERE " + whereClause;
            }

            if (!whereClause.contains("IsActive"))
            {
                whereClause = whereClause + " AND \"IsActive\" = true";
            }

            whereClause = whereClause + " AND \"DeletedAtDateTimeUtc\" = '9999-12-31T23:59:59.999'";

            //NOTE: Validate and sanitize sortClause
            if (sortClause.contains("ORDER BY"))
            {
                throw new Exception("'sortClause' query parameter must not include 'ORDER BY'.");
            }

            if (sortClause.length() > 0)
            {
                sortClause = "ORDER BY " + sortClause;

                if (!sortClause.contains("Ordinal"))
                {
                    sortClause = sortClause + ", \"Ordinal ASC\" = true";
                }
            }

            //NOTE: Validate and sanitize asOfDateTimeUtc
//            if (asOfDateTimeUtc.isBefore(LocalDateTime.parse("1900-01-01T00:00:00.000")))
            if (asOfDateTimeUtc.isBefore(Instant.parse("1900-01-01T00:00:00.000Z")))
            {
                throw new Exception("'asOfDateTimeUtc' query parameter must be greater than or equal to '1900-01-01'.");
            }

            //if (asOfDateTimeUtc.isAfter(LocalDateTime.parse("9999-12-31T23:59:59.999")))
            if (asOfDateTimeUtc.isAfter(Instant.parse("9999-12-31T23:59:59.999Z")))
            {
                throw new Exception("'asOfDateTimeUtc' query parameter must be less than or equal to '9999-12-31'.");
            }

            //NOTE: Validate and sanitize graphDepthLimit
            if (graphDepthLimit < 1)
            {
                throw new Exception("'graphDepthLimit' query parameter must be greater than or equal to 1.");
            }

            if (graphDepthLimit > Long.parseLong(Objects.requireNonNull(environment.getProperty("systemDefaultGraphDepthLimitMaximum"))))
            {
                throw new Exception("'graphDepthLimit' query parameter must be less than or equal to " + environment.getProperty("systemDefaultGraphDepthLimitMaximum") + ".");
            }

            //NOTE: Validate and sanitize pageNumber
            if (pageNumber < 1)
            {
                throw new Exception("'pageNumber' query parameter must be greater than or equal to 1.");
            }

            if (pageNumber > Long.parseLong(Objects.requireNonNull(environment.getProperty("systemDefaultPaginationPageNumberMaximum"))))
            {
                throw new Exception("'pageNumber' query parameter must be less than or equal to " + environment.getProperty("systemDefaultPaginationPageNumberMaximum") + ".");
            }

            //NOTE: Validate and sanitize pageSize
            if (pageSize < 1)
            {
                throw new Exception("'pageSize' query parameter must be greater than or equal to 1.");
            }

            if (pageSize > Long.parseLong(Objects.requireNonNull(environment.getProperty("systemDefaultPaginationPageSizeMaximum"))))
            {
                throw new Exception("'pageSize' query parameter must be less than or equal to " + environment.getProperty("systemDefaultPaginationPageSizeMaximum") + ".");
            }

            //TODO: Since EntityDataRead() is in public, ensure that is locked down to correct role(s) only
            //TODO: Refactor the statements below to be reusable for validation, local caching, etc
            if (errorValues.length() == 0)
            {
                statement = connection.prepareCall("{call \"EntityDataRead\"(?,?,?,?,?,?,?,?,?)}");
                statement.setString(1, entityTypeName);
                statement.setString(2, selectClause);
                statement.setString(3, URLDecoder.decode(whereClause, StandardCharsets.UTF_8));
                statement.setString(4, URLDecoder.decode(sortClause, StandardCharsets.UTF_8));
                statement.setTimestamp(5, Timestamp.from(asOfDateTimeUtc));
                statement.setLong(6, graphDepthLimit);
                statement.setLong(7, pageNumber);
                statement.setLong(8, pageSize);

                //NOTE: Register the data OUT parameter before calling the stored procedure
                statement.registerOutParameter(9, Types.LONGVARCHAR);
                statement.executeUpdate();

                //NOTE: Read the OUT parameter now
                entityData = statement.getString(9);

                if (entityData == null)
                {
                    //TODO: Improve this error output???
                    entityData = "{[]}";
                }

                //TODO: Filter or mask unauthorized or sensitive attributes for this InformationSystemUserRole (as JSON)???
            }
        }
        //TODO: Create and throw a custom exception for "No associated EntityTypeAttributes for ... " (or associate some attributes)???
        catch (Exception e)
        {
            logger.error("GetBusinessEntities() failed due to: " + e);
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
                logger.error("Failed to close statement and/or connection resource in GetBusinessEntities() due to: " + e);
            }
        }

        logger.info("GetBusinessEntities() succeeded for " + entityTypeName);
        //result = new JSONObject("{\"EntityData\":" + entityData + "}");
        //return "{\"EntityData\":" + entityData + "}";
        //TODO: Echo input parameters in Postgres function return JSON???
        return new ResponseEntity<String>(entityData, HttpStatus.OK);
    }

    @PatchMapping(value = "/{entityTypeName}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> UpdateBusinessEntity(@PathVariable("entityTypeName") String entityTypeName, @PathVariable("id") Long id, ServerWebExchange exchange) throws Exception
    {
        ServerHttpRequest request = null;
        MultiValueMap<String,String> queryParams = null;

        ResponseEntity<String> existingEntityData = null;

        int entityTypeId = -1;
        ArrayList<Integer> entityTypeAssociations = null;

        String[] sqlBlacklistValues = null;
        String errorValues = "";

        String requestHeader = "";

        HttpCookie authenticationJwt = null;
        Base64.Decoder decoderBase64 = Base64.getUrlDecoder();
        String[] authenticationJwtSections = null;
        JsonNode authenticationJwtHeader = null;
        JsonNode authenticationJwtPayload = null;
        String authenticationJwtSignature = "";
        int userId = -1;

        Flux<DataBuffer> requestBody = null;
        Mono<String> requestBodyData = null;
        String[] bodyJwtSections = null;
        ObjectMapper objectMapper = null;
        JsonNode bodyJwtHeader = null;
        JsonNode bodyJwtPayload = null;
        String bodyJwtSignature = "";

        String[] entityTypeAttributesNeverToReturn = null;

        String updateClause = "";

        String selectClause = "";

        String entityData = "";

        JsonNode entityDataJsonCurrent = null;
        JsonNode entityDataNodeCurrent = null;

        JsonNode entityDataJsonPrevious = null;
        JsonNode entityDataNodePrevious = null;

        ObjectNode entityDataNodeCombined = null;

        //NOTE: Rather than validating the EntityTypeDefinition name, we're going to optimistically pass it through to the database if it returns attributes
        //NOTE: We don't request versions our business entity data structure explicitly in the base URL; instead our explicit (v1.2.3) versioning is maintained internally, based on/derived from the AsOfUtcDateTime query parameter
        //NOTE: Since we're not automatically parsing the resulting JSON into an object, we're returning a JSON String rather than a JSONObject
        try
        {
            logger.info("Attempting to UpdateBusinessEntity() for " + entityTypeName);

            request = exchange.getRequest();
            queryParams = request.getQueryParams();

            existingEntityData = GetBusinessEntities(entityTypeName, URLEncoder.encode("\"Id\" = " + id, StandardCharsets.UTF_8), "", Instant.now(), 1, 1, 1, exchange);

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

            //NOTE: Example request http://localhost:8080/Person with "Authentication" JWT and JWT request body
            //NOTE: https://learning.postman.com/docs/sending-requests/response-data/cookies/
//            Authentication JWT: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0ZWQiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiJlZjRhZjRlMy1lNzM2LTQyNWEtYWFmZi1lY2EwM2I3YjliMjgiLCJib2R5Ijp7IkVtYWlsQWRkcmVzcyI6ImFteS5hbmRlcnNvbkBhbXlzYWNjb3VudGluZy5jb20ifX0.Djq5LYPEK1QFgBk9aN5Vei37K6Cb8TxNH3ADWDcUaHs
//            Request JWT: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IlBPU1QgL0VudGl0eVR5cGUiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiI4NzUyZjIzYi0xYTliLTQyMmEtOGIyNi0zNzQyNDM0ZGY0NzYiLCJib2R5Ijp7IkVudGl0eVN1YnR5cGVJZCI6LTEsIlRleHRLZXkiOiJwZXJzb24tbm9uZS1iaWxsLWJha2VyIiwiTGVnYWxHaXZlbk5hbWUiOiJCaWxsIiwiTGVnYWxTdXJuYW1lIjoiQmFrZXIiLCJCb3JuQXREYXRlVGltZVV0YyI6IjIwMDItMDItMDMgMTE6MTI6MTMuMTIzIiwiTGVnYWxDaXRpemVuT2ZDb3VudHJ5R2VvZ3JhcGhpY1VuaXRJZCI6MSwiTG9jYWxlSWQiOjEsIk9yZGluYWwiOi0xLCJJc0FjdGl2ZSI6dHJ1ZX19.rWNowmEoPkF8N0Q5KC5-W83g3hMqIf9TV8KHzLgNbio

            //TODO: Validate JWT
            requestHeader = exchange.getRequest().getHeaders().getFirst("Temp-Body");
            bodyJwtSections = requestHeader.split("\\.");
            objectMapper = new ObjectMapper();
            bodyJwtHeader = objectMapper.readTree(decoderBase64.decode(bodyJwtSections[0]));
            bodyJwtPayload = objectMapper.readTree(decoderBase64.decode(bodyJwtSections[1]));
            //TODO: Validate JWT signature per https://www.baeldung.com/java-jwt-token-decode

            logger.info(("Request from '" + bodyJwtPayload.get("iss").asText() + "' for '" + bodyJwtPayload.get("aud").asText() + "' using key '" + bodyJwtHeader.get("kid").asText() + "'"));

            //TODO: Validate JWT
            authenticationJwt = request.getCookies().getFirst("Authentication");
            authenticationJwtSections = authenticationJwt.getValue().split("\\.");
            authenticationJwtHeader = objectMapper.readTree(decoderBase64.decode(authenticationJwtSections[0]));
            authenticationJwtPayload = objectMapper.readTree(decoderBase64.decode(authenticationJwtSections[1]));
            //TODO: Validate JWT signature per https://www.baeldung.com/java-jwt-token-decode

            logger.info(("Requested by '" + authenticationJwtPayload.get("body").get("EmailAddress").asText() + "' using key '" + authenticationJwtHeader.get("kid").asText() + "'"));

            //TODO: Look up InformationSystemUser.Id using the authenticated user's EmailAddress in the Authentication JWT payload, and assign it below
            userId = -100;

            //TODO: Check user's role(s) and permissions for this operation

            sqlBlacklistValues = environment.getProperty("sqlNotToAllow").toLowerCase().split(",");

            //TODO: *** Only check request body for SQL injection
            errorValues = GuardAgainstSqlIssues(queryParams.toString(), sqlBlacklistValues);

//            requestBody = request.getBody();
//            requestBodyData = DataBufferUtils.join(requestBody).map(dataBuffer -> {
//                byte[] bytes = new byte[dataBuffer.readableByteCount()];
//                dataBuffer.read(bytes);
//                DataBufferUtils.release(dataBuffer);
//                return bytes.toString();
//            });
//
//            bodyJwtSections = requestBodyData.toString().split("\\.");
//            bodyJwtHeader = new String(decoderBase64.decode(bodyJwtSections[0]));
//            bodyJwtPayload = new String(decoderBase64.decode(bodyJwtSections[1]));
//            bodyJwtSignature = new String(decoderBase64.decode(bodyJwtSections[2]));
            //TODO: Validate JWT signature per https://www.baeldung.com/java-jwt-token-decode

            //NOTE: Get the Id of the requested entityTypeName
            for (int i = 0 ; i < entityTypeDefinitions.size() ; i++)
            {
                if (entityTypeDefinitions.get(i).getLocalizedName().equals(entityTypeName))
                {
                    entityTypeId = entityTypeDefinitions.get(i).getId();
                    break;
                }
            }

            if (entityTypeId == -1)
            {
                throw new Exception("Invalid 'entityTypeName' query parameter '" + entityTypeName + "'");
            }

            //NOTE: Get the Ids of any associated EntityTypeAttributes
            entityTypeAssociations = new ArrayList<Integer>();

            for (int i = 0 ; i < entityTypeDefinitionEntityTypeAttributeAssociations.size() ; i++)
            {
                if (entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getEntityTypeDefinitionId() == entityTypeId)
                {
                    entityTypeAssociations.add(entityTypeDefinitionEntityTypeAttributeAssociations.get(i).getEntityTypeAttributeId());
                }
            }

            if (entityTypeAssociations.size() == 0)
            {
                throw new Exception("No associated EntityTypeAttributes for 'entityTypeName' query parameter '" + entityTypeName + "'");
            }

            //NOTE: Build the updateClause using the associated EntityTypeAttributes LocalizedNames, while filtering out any entityTypeAttributesNeverToReturn
            //NOTE: updateClause = "\"Id\",\"LocalizedName\"";
            entityTypeAttributesNeverToReturn = environment.getProperty("entityTypeAttributesNeverToReturn").split(",");

            for (int i = 0 ; i < entityTypeAttributes.size() ; i++)
            {
                if (entityTypeAssociations.contains(entityTypeAttributes.get(i).getId())) {
                    switch (entityTypeAttributes.get(i).getEntityTypeAttributeValueEntitySubtypeId()) {
                        //NOTE: PrimaryKey, Uuid, CreatedDateTime, CreatedByUserId, UpdatedDateTime, UpdatedByUserId, DeletedDateTime, DeletedByUserId, CorrelationUuid, Digest
                        case 23, 24, 27, 28, 29, 30, 31, 32, 33, 34:
                            if (bodyJwtPayload.get("body").has(entityTypeAttributes.get(i).getLocalizedName())) {
                                //NOTE: System-generated (server-side) values
                                logger.warn(("Attempt to specify system-generated '" + entityTypeAttributes.get(i).getLocalizedName() + "' value as " + authenticationJwtPayload.get("body").get(entityTypeAttributes.get(i).getLocalizedName()).asText() + "'"));
                            }
                            break;

                        //NOTE: DefaultTextKey
                        //TODO: Log non-pattern matching provided TextKey values
//                        case 25:
//
                        //NOTE: Required and optional values
                        default:
                            if (bodyJwtPayload.get("body").has(entityTypeAttributes.get(i).getLocalizedName())) {
                                switch (entityTypeAttributes.get(i).getGeneralizedDataTypeEntitySubtypeId()) {
                                    //NOTE: Boolean, Integer, Decimal
                                    case 10, 11, 16:
                                        updateClause = updateClause + "\"" + entityTypeAttributes.get(i).getLocalizedName() + "\" = " + bodyJwtPayload.get("body").get(entityTypeAttributes.get(i).getLocalizedName()).asText() + ", ";
                                        break;
                                    //NOTE: UnicodeCharacter, UnicodeString, DateTime
                                    case 12, 13, 14:
                                        updateClause = updateClause + "\"" + entityTypeAttributes.get(i).getLocalizedName() + "\" = '" + bodyJwtPayload.get("body").get(entityTypeAttributes.get(i).getLocalizedName()).asText() + "', ";
                                        break;
                                }
                            }
                            break;
                    }
                }

                if (entityTypeAssociations.contains(entityTypeAttributes.get(i).getId()) && !Arrays.asList(entityTypeAttributesNeverToReturn).contains(entityTypeAttributes.get(i).getLocalizedName()))
                {
                    selectClause = selectClause + "\"" + entityTypeAttributes.get(i).getLocalizedName() + "\",";
                }
            }

            if (updateClause.length() > 0){
                updateClause = updateClause.substring(0, updateClause.length() - 2);
            }

            if (selectClause.length() > 0){
                selectClause = selectClause.substring(0, selectClause.length() - 1);
            }

            //TODO: *** Get UTC time in Postgres function (currently getting local) for Create, Update, and Delete operations

            //TODO: Since EntityDataCreate() is in public, ensure that is locked down to correct role(s) only
            //TODO: Refactor the statements below to be reusable for validation, local caching, etc
            if (errorValues.length() == 0)
            {
                statement = connection.prepareCall("{call \"EntityDataUpdate\"(?,?,?,?,?,?,?)}");
                statement.setString(1, entityTypeName);
                statement.setString(2, updateClause);
                statement.setString(3, selectClause);
                statement.setObject(4, bodyJwtPayload.get("jti").asText(), Types.OTHER);
                statement.setLong(5, userId);
                statement.setLong(6, id);

                //NOTE: Register the data OUT parameter before calling the stored procedure
                statement.registerOutParameter(7, Types.LONGVARCHAR);
                statement.executeUpdate();

                //NOTE: Read the OUT parameter now
                entityData = statement.getString(7);

                if (entityData == null)
                {
                    //TODO: Improve this error output???
                    entityData = "{[]}";
                }
                else
                {
                    entityDataJsonCurrent = objectMapper.readTree(entityData);
                    entityDataNodeCurrent = objectMapper.readTree(entityDataJsonCurrent.path("EntityData").toString());

                    entityDataJsonPrevious = objectMapper.readTree(existingEntityData.getBody());
                    entityDataNodePrevious = objectMapper.readTree(entityDataJsonPrevious.path("EntityData").toString());

                    entityDataNodeCombined = objectMapper.createObjectNode();

                    entityDataNodeCombined.put("EntityType", entityDataJsonCurrent.path("EntityType").asText());
                    entityDataNodeCombined.put("TotalRows", entityDataJsonCurrent.path("TotalRows").asLong());

                    entityDataNodeCombined.set("EntityData", entityDataNodeCurrent);
                    entityDataNodeCombined.set("EntityDataPrevious", entityDataNodePrevious);
                }

                //TODO: Filter or mask unauthorized or sensitive attributes for this InformationSystemUserRole (as JSON)???
            }
        }
        catch (Exception e)
        {
            logger.error("UpdateBusinessEntity() failed due to: " + e);
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
                logger.error("Failed to close statement and/or connection resource in UpdateBusinessEntity() due to: " + e);
            }
        }

        logger.info("UpdateBusinessEntity() succeeded for " + entityTypeName);
        //result = new JSONObject("{\"EntityData\":" + entityData + "}");
        //return "{\"EntityData\":" + entityData + "}";
        //TODO: Echo input parameters in Postgres function return JSON???
        return new ResponseEntity<String>(entityDataNodeCombined.toString(), HttpStatus.OK);
    }

    @DeleteMapping
    public void DeleteBusinessEntity()
    {
    }

    public String GuardAgainstSqlIssues(String sqlFragment, String[] sqlBlacklistValues)
    {
        String issueValues = "";

        try
        {
            //NOTE: Based on Spring Tips: Configuration (https://spring.io/blog/2020/04/23/spring-tips-configuration)
            //filterIssueValues = new String[]{environment.getProperty("sqlToAllowInWhereClause").toLowerCase()};

            logger.info("Attempting to GuardAgainstSqlIssues() for " + sqlFragment);

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

        logger.info("GuardAgainstSqlIssues succeeded when " + sqlFragment + " did not contain " + Arrays.toString(sqlBlacklistValues));
        return issueValues;
    }
}
