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

import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ServerWebExchange;

import java.lang.Exception;

import java.net.URLDecoder;
import java.sql.*;
import java.time.Instant;
//TODO: Remove if not used
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.OptionalLong;

import com.dsimpletech.aafdata.EntityDataMicroservice.database.DatabaseConnection;

//TODO: Remove if not used
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
            //TODO: Filter out all IsActive = false by default in function
            //TODO: Filter out all deleted in function
            //TODO: Default sort order

            //TODO: Index Ordinal???
            //TODO: Add Unknown and None to EntityType data?

            //TODO: Check for non-null query parameters before checking them
            sqlBlacklistValues = environment.getProperty("sqlNotToAllow").toLowerCase().split(",");
            //TODO: Only check whereClause and sortClause for SQL injection, not other query parameters
            errorValues = GuardAgainstSqlIssues(queryParams.toString(), sqlBlacklistValues);

            //NOTE: Build selectClause, based on entityTypeName
            //TODO: Use GetBusinessEntities() to get attributes for specified EntityType
            //TODO: Remove attributes that should never be returned, e.g. Digest, from selectClause
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

            //TODO: Deal with empty asOfDateTimeUtc, graphDepthLimit, pageNumber, and pageSize
            //TODO: Where do asOfDateTimeUtc and graphDepthLimit go, and how do they work?
            //NOTE: Validate and sanitize whereClause
            //TODO: Strip out client-provided 'WHERE'???
            if (whereClause.length() > 0)
            {
                whereClause = "WHERE " + whereClause;
            }

            //NOTE: Validate and sanitize sortClause
            //TODO: Strip out client-provided 'ORDER BY'???
            if (sortClause.length() > 0)
            {
                sortClause = "ORDER BY " + sortClause;
            }

            //TODO: If no asOfDateTimeUtc value provided or asOfDateTimeUtc >= Java Date and Postgres timestamp max value ('9999-12-31T23:59:59.999Z'), utilize global error handler above
            //NOTE: Validate and sanitize asOfDateTimeUtc
            if (queryParams.containsKey("asOfDateTimeUtc") == false)
            {
                asOfDateTimeUtc = LocalDateTime.parse(String.valueOf(queryParams.get("asOfDateTimeUtc").get(0)));
            }

//            if (asOfDateTimeUtc.before(Timestamp.valueOf("1900-01-01")))
//            {
//                throw new Exception("'asOfDateTimeUtc' query parameter must be greater than or equal to '1900-01-01'.");
//            }
//
//            if (asOfDateTimeUtc.after(Timestamp.valueOf("9999-12-31")))
//            {
//                throw new Exception("'asOfDateTimeUtc' query parameter must be less than or equal to '9999-12-31'.");
//            }
//
            //TODO: If no graphDepthLimit value provided or graphDepthLimit >= Java long and Postgres bigint max value (Long.MAX_VALUE), utilize global error handler above
            //NOTE: Validate and sanitize graphDepthLimit
            if (queryParams.containsKey("graphDepthLimit") == false)
            {
                graphDepthLimit = Long.parseLong(environment.getProperty("systemDefaultGraphDepthLimit"));
            }

            if (graphDepthLimit < 1)
            {
                throw new Exception("'graphDepthLimit' query parameter must be greater than or equal to 1.");
            }

            if (graphDepthLimit > Long.parseLong(environment.getProperty("systemDefaultGraphDepthLimitMaximum")))
            {
                throw new Exception("'graphDepthLimit' query parameter must be less than or equal to " + environment.getProperty("systemDefaultGraphDepthLimitMaximum") + ".");
            }

            //TODO: If no pageNumber value provided or pageNumber >= Java long and Postgres bigint max value (Long.MAX_VALUE), utilize global error handler above
            //NOTE: Validate and sanitize pageNumber
            if (queryParams.containsKey("pageNumber") == false)
            {
                pageNumber = Long.parseLong(environment.getProperty("systemDefaultPaginationPageNumber"));
            }

            if (pageNumber < 1)
            {
                throw new Exception("'pageNumber' query parameter must be greater than or equal to 1.");
            }

            if (pageNumber > Long.parseLong(environment.getProperty("systemDefaultPaginationPageNumberMaximum")))
            {
                throw new Exception("'pageNumber' query parameter must be less than or equal to " + environment.getProperty("systemDefaultPaginationPageNumberMaximum") + ".");
            }

            //TODO: If no pageSize value provided or pageSize >= Java long and Postgres bigint max value (Long.MAX_VALUE), utilize global error handler above
            //NOTE: Validate and sanitize pageSize
            if (queryParams.containsKey("pageSize") == false)
            {
                pageSize = Long.parseLong(environment.getProperty("systemDefaultPaginationPageSize"));
            }

            if (pageSize < 1)
            {
                throw new Exception("'pageSize' query parameter must be greater than or equal to 1.");
            }

            if (pageSize > Long.parseLong(environment.getProperty("systemDefaultPaginationPageSizeMaximum")))
            {
                throw new Exception("'pageSize' query parameter must be less than or equal to " + environment.getProperty("systemDefaultPaginationPageSizeMaximum") + ".");
            }

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

                //TODO: Filter or mask unauthorized or sensitive attributes for this InformationSystemUserRole (as JSON)???
            }
        }
        catch (Exception e)
        {
            logger.error("GetBusinessEntities() failed due to: " + e);
            //return "{}";
            return new ResponseEntity<String>("{[]}", HttpStatus.INTERNAL_SERVER_ERROR);
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
        logger.info("GuardAgainstSqlIssues succeeded when " + sqlFragment + " did not contain " + sqlBlacklistValues.toString());
        return issueValues;
    }
}
