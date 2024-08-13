package com.dsimpletech.aafdata.EntityDataMicroservice.controller;


//import org.json.*;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import org.springframework.http.server.reactive.ServerHttpRequest;

import org.springframework.util.MultiValueMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ServerWebExchange;

import java.lang.Exception;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import com.dsimpletech.aafdata.EntityDataMicroservice.database.EntityTypeDefinition;
import com.dsimpletech.aafdata.EntityDataMicroservice.database.EntityTypeAttribute;
import com.dsimpletech.aafdata.EntityDataMicroservice.database.EntityTypeDefinitionEntityTypeAttributeAssociation;
import com.dsimpletech.aafdata.EntityDataMicroservice.database.DatabaseConnection;


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
    @PostMapping
    public void CreateBusinessEntity()
    {
    }

    @GetMapping("/{entityTypeName}")
    @ResponseBody
    public ResponseEntity<String> GetBusinessEntities(@PathVariable("entityTypeName") String entityTypeName, @RequestParam(defaultValue = "") String whereClause, @RequestParam(defaultValue = "") String sortClause, @RequestParam(defaultValue = "#{T(java.time.Instant).now()}") Instant asOfDateTimeUtc, @RequestParam(defaultValue = "1") long graphDepthLimit, @RequestParam(defaultValue = "1") long pageNumber, @RequestParam(defaultValue = "20") long pageSize, ServerWebExchange exchange) throws Exception
    {
        ServerHttpRequest request = null;
        MultiValueMap<String,String> queryParams = null;

        int entityTypeId = -1;
        ArrayList<Integer> entityTypeAssociations = null;

        String[] sqlBlacklistValues = null;
        String errorValues = "";

        String selectClause = "";

        String entityData = "";

        //TODO: In Postgres function, if possible change ALTER FUNCTION public."EntityDataRead"(character varying) OWNER TO postgres to GRANT
        //TODO: Add ReadWrite and ReadOnly roles, and remove direct table access with SQL GRANTs; CRUD only enforced upstream through process service calls

        //TODO: Add automation batch script at infrastructure root
        //TODO: Add uniqueness constraints to table/model scripts
        //TODO: Add indexes to table/model scripts

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

            //NOTE: Example request http://localhost:8080/EntityType?whereClause=%22Id%22%20%3E%200&sortClause=%22Ordinal%22%252C%22Id%22&asOfDateTimeUtc=2023-01-01T00:00:00.000Z&graphDepthLimit=1&pageNumber=1&pageSize=20

            //TODO: Add Unknown and None to EntityTypeDefinition data?

            //TODO: Check for non-null query parameters before checking them
            sqlBlacklistValues = environment.getProperty("sqlNotToAllow").toLowerCase().split(",");
            //TODO: Only check whereClause and sortClause for SQL injection, not other query parameters
            errorValues = GuardAgainstSqlIssues(queryParams.toString(), sqlBlacklistValues);

            //NOTE: Build selectClause, based on EntityTypeDefinition.LocalizedName > EntityTypeAttribute.LocalizedName
            //TODO: Build query parameter array while validating explicit filter criteria and logging invalid attribute names, etc to be returned (see Create GET Method AAF-48)
            //TODO: Use GetBusinessEntities() to get attributes for specified EntityTypeDefinition
            //TODO: Remove attributes that should never be returned, e.g. Digest, from selectClause
            //TODO: Use GetBusinessEntities() to cache EntityTypeDefinition, EntityTypeAttribute, and EntityTypeDefinitionEntityTypeAttributeAssociation for input validation at service startup

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
//            selectClause = "\"Id\",\"LocalizedName\"";
            for (int i = 0 ; i < entityTypeAttributes.size() ; i++)
            {
                if (entityTypeAssociations.contains(entityTypeAttributes.get(i).getId()))
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
            }

            if (!sortClause.contains("Ordinal"))
            {
                sortClause = sortClause + ", \"Ordinal ASC\" = true";
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
                    entityData = "{[]}";
                }

                //TODO: Filter or mask unauthorized or sensitive attributes for this InformationSystemUserRole (as JSON)???
            }
        }
        catch (Exception e)
        {
            logger.error("GetBusinessEntities() failed due to: " + e);
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
        //TODO: Echo input parameters in Postgres function return JSON
        return new ResponseEntity<String>(entityData, HttpStatus.OK);
    }

    @PatchMapping
    public void UpdateBusinessEntity()
    {
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

        //TODO: Translate sqlBlacklistValues to human-readable values
        logger.info("GuardAgainstSqlIssues succeeded when " + sqlFragment + " did not contain " + Arrays.toString(sqlBlacklistValues));
        return issueValues;
    }
}
