package com.dsimpletech.aafdata.EntityDataMicroservice.controller;


//import org.json.*;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import org.springframework.http.server.reactive.ServerHttpRequest;

import org.springframework.util.MultiValueMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.server.ServerWebExchange;

import java.lang.Exception;

import java.net.URLDecoder;
import java.sql.*;
import java.time.LocalDateTime;

import com.dsimpletech.aafdata.EntityDataMicroservice.database.DatabaseConnection;

import static java.lang.Long.parseLong;


@RestController
@RequestMapping(value = "/")
public class BusinessEntityController
{

    @Autowired
    private Environment environment;

    //NOTE: Spring Boot Logback default logging implemented per https://www.baeldung.com/spring-boot-logging
    Logger logger = LoggerFactory.getLogger(BusinessEntityController.class);

    @PostMapping
//    @Query("DECLARE\n" +
//            "    EntityId bigint;\n" +
//            "    EntityDataOutput json[];\n" +
//            "BEGIN\n" +
//            "    INSERT INTO aaf.\"Entity\"\n" +
//            "    (\"Id\", \"Name\", \"Updated\")\n" +
//            "    VALUES (default, 'Test', (now() AT time zone 'utc'))\n" +
//            "    RETURNING \"Id\" INTO EntityId;\n" +
//            "\n" +
//            "    SELECT json_agg(t)\n" +
//            "    INTO EntityDataOutput\n" +
//            "    FROM (\n" +
//            "             SELECT *\n" +
//            "             FROM aaf.\"Entity\"\n" +
//            "             WHERE \"Id\" = EntityId\n" +
//            "         ) t;\n" +
//            "END")
    public void CreateBusinessEntity()
    {
    }

    @GetMapping("/{entityTypeName}")
    @ResponseBody
    public ResponseEntity<String> GetBusinessEntities(@PathVariable String entityTypeName, @RequestParam String whereClause, @RequestParam String sortClause, @RequestParam LocalDateTime asOfDateTimeUtc, @RequestParam long graphDepthLimit, @RequestParam long pageNumber, @RequestParam long pageSize, ServerWebExchange exchange) throws Exception
    {
        ServerHttpRequest request = null;
        MultiValueMap<String,String> queryParams = null;
        Connection connection = null;

        String[] sqlBlacklistValues = null;
        String errorValues = "";

        CallableStatement statement = null;

        String selectClause = "";

        String entityData = "";

        //TODO: In Postgres function, if possible change ALTER FUNCTION public."EntityDataRead"(character varying) OWNER TO postgres to GRANT
        //TODO: Add ReadWrite and ReadOnly roles, and remove direct table access with SQL GRANTs; CRUD only enforced upstream through process service calls

        //TODO: Add automation batch script at infrastructure root
        //TODO: Add uniqueness contraints to table/model scripts
        //TODO: Add indexes to table/model scripts

        //TODO: Validate API key upstream in Client Communication Service (CCS)
        //TODO: Validate JWT upstream in Client Communication Service (CCS)
        //TODO: Validate authenticated user and API key OrganizationalUnit association if Employee upstream in Client Communication Service (CCS)
        //TODO: Validate entity data authorization, i.e. appropriate permissions for requested entity and operation? upstream in Client Communication Service (CCS)
        //TODO: Add Organization/OrganizationalUnit filtering to authorization/permissioning (what about non-Employees??? only *my* stuff???)
        //TODO: Implement a pipeline/plugin architecture to replace system behavior like JWT communication, etc

        //NOTE: Rather than validating the EntityType name, we're going to optimistically pass it through to the database if it returns attributes
        //NOTE: We don't request versions our business entity data structure explicitly in the base URL; instead our explicit (v1.2.3) versioning is maintained internally, based on/derived from the AsOfUtcDateTime query parameter
        //NOTE: Since we're not automatically parsing the resulting JSON into an object, we're returning a JSON String rather than a JSONObject
        try
        {
            logger.info("Attempting to GetBusinessEntities() for " + entityTypeName);

            request = exchange.getRequest();
            queryParams = request.getQueryParams();

            connection = DatabaseConnection.getConnection();

            //NOTE: Example request http://localhost:8080/EntityType?whereClause=%22Id%22%3D1&sortClause=%22Ordinal%22%252C%22Id%22&asOfDateTimeUtc=2023-01-01T00:00:00.000&graphDepthLimit=1&pageNumber=1&pageSize=20

            //TODO: Build query parameter array while validating explicit filter criteria and logging invalid attribute names, etc to be returned (see Create GET Method AAF-48)
            //TODO: Add pagination, e.g. pageNumber, pageSize, with master defaults and max
            //TODO: Filter out all deleted in function
            //TODO: Filter out all IsActive = false by default in function
            //TODO: Index Ordinal???

            //TODO: Check for non-null query parameters before checking them
            sqlBlacklistValues = environment.getProperty("sqlNotToAllow").toLowerCase().split(",");
            //TODO: Only check entityTypeName, where, and sortBy for SQL injection, not other query parameters
            errorValues = GuardAgainstSqlIssues(queryParams.toString(), sqlBlacklistValues);

            //TODO: Use GetBusinessEntities() to get attributes for specified EntityType
            //TODO: Remove attributes that should never be returned, e.g. Digest
            //TODO: Use GetBusinessEntities() to cache EntityTypeDefinition, EntityTypeAttribute, and EntityTypeDefinitionEntityTypeAttributeAssociation for input validation at service startup
            //NOTE: Validate EntityType name
//            statement = connection.prepareCall("{call \"EntityDataRead\"(?,?)}");
//            statement.setString(1, entityTypeName);

            //NOTE: Register the OUT parameter before calling the stored procedure
//            statement.registerOutParameter(9, Types.LONGVARCHAR);
//            statement.executeUpdate();

            //NOTE: Read the OUT parameter now
//            entityData = statement.getString(9);
//
//            if (entityData.indexOf(entityTypeName) < 0)
//            {
//                throw new Exception("EntityType '" + entityTypeName + "' not found in EntityTypeDefinition table, i.e. not a valid EntityType.");
//            }

            selectClause = "\"Id\",\"Uuid\"";

            //TODO: Deal with empty whereClause, sortClause, asOfDateTimeUtc, graphDepthLimit, pageNumber, and pageSize
            //TODO: Where do asOfDateTimeUtc and graphDepthLimit go, and how do they work?

            //TODO: If no asOfDateTimeUtc, grab ISO string value of Now() in database server's time zone
            //asOfDateTimeUtc = LocalDateTime.parse(String.valueOf(queryParams.get("asOfDateTimeUtc").get(0)));

            //TODO: If no graphDepthLimit, use default
            //graphDepthLimit = parseLong(String.valueOf(queryParams.get("graphDepthLimit").get(0)), 10);

            //TODO: If no pageNumber, use default
            //pageNumber = parseLong(String.valueOf(queryParams.get("pageNumber").get(0)), 10);

            //TODO: If no pageSize, use default
            //pageSize = parseLong(String.valueOf(queryParams.get("pageSize").get(0)), 10);

            if (errorValues.length() == 0)
            {
                statement = connection.prepareCall("{call \"EntityDataRead\"(?,?,?,?,?,?,?,?,?)}");
                statement.setString(1, entityTypeName);
                statement.setString(2, selectClause);
                statement.setString(3, URLDecoder.decode(whereClause));
                statement.setString(4, URLDecoder.decode(sortClause));
                statement.setTimestamp(5, Timestamp.valueOf(asOfDateTimeUtc));
                statement.setLong(6, graphDepthLimit);
                statement.setLong(7, pageNumber);
                statement.setLong(8, pageSize);

                //NOTE: Register the data OUT parameter before calling the stored procedure
                statement.registerOutParameter(9, Types.LONGVARCHAR);
                statement.executeUpdate();

                //NOTE: Read the OUT parameter now
                //TODO: Check for null entityData
                entityData = statement.getString(9);

                //TODO: Filter or mask unauthorized or sensitive attributes for this InformationSystemUserRole
            }
        }
        catch (Exception e)
        {
            logger.error("GetBusinessEntities() failed due to: " + e);
            //return "{}";
            return new ResponseEntity<String>(entityData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        finally
        {
            try
            {
                if (statement != null) {
                    statement.close();
                }

                if (connection != null) {
                    connection.close();
//
//                if (request != null) {
//                    request.close();
//                }
//
//                if (queryParams != null) {
//                    queryParams.close();
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
        return new ResponseEntity<String>(entityData, HttpStatus.OK);
    }

    public String GuardAgainstSqlIssues(String sqlFragment, String[] sqlBlacklistValues)
    {
        String issueValues = "";

        try
        {
            //NOTE: Based on Spring Tips: Configuration (https://spring.io/blog/2020/04/23/spring-tips-configuration)
            //filterIssueValues = new String[]{environment.getProperty("sqlToAllowInWhereClause").toLowerCase()};

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

        logger.info("GuardAgainstSqlIssues succeeded when " + sqlFragment + " did not contain " + sqlBlacklistValues.toString());
        return issueValues;
    }
}
