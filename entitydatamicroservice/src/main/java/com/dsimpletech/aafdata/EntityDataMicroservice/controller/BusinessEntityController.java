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
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.server.ServerWebExchange;

import java.lang.Exception;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
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
    public ResponseEntity<String> GetBusinessEntities(@PathVariable String entityTypeName, ServerWebExchange exchange) throws Exception
    {

        Connection connection = null;
        CallableStatement statement = null;
        MultiValueMap<String,String> queryParams = null;
        ServerHttpRequest request = null;
        String entityData = "";
        //String result = "";
        String[] sqlValues = null;
        String errorValues = "";
        String warnValues = "";
        LocalDateTime asOfDateTimeUtc = null;
        long graphDepthLimit = -1;
        long pageNumber = -1;
        long pageSize = -1;

        //TODO: In Postgres function, return total matching records, e.g. SELECT COUNT(Id)
        //TODO: In Postgres function, if possible change ALTER FUNCTION public."EntityDataRead"(character varying) OWNER TO postgres to GRANT
        //TODO: Index Ordinal???
        //TODO: Add ReadWrite and ReadOnly roles, and remove direct table access with SQL GRANTs; CRUD only enforced upstream through process service calls

        //NOTE: Since we're not automatically parsing the resulting JSON into an object, we're returning a JSON String rather than a JSONObject

        try
        {
            //TODO: Validate API key upstream in Client Communication Service (CCS)
            //TODO: Validate JWT upstream in Client Communication Service (CCS)
            //TODO: Validate authenticated user and API key OrganizationalUnit association if Employee upstream in Client Communication Service (CCS)
            //TODO: Validate entity data authorization, i.e. appropriate permissions for requested entity and operation? upstream in Client Communication Service (CCS)

            //TODO: Add version to base URL? Versioning: simple, explicit (v1.2.3) or internal, based on/derived from AsOfUtcDateTime?

            //TODO: Add Organization/OrganizationalUnit filtering to authorization/permissioning (what about non-Employees??? only *my* stuff???)

            //TODO: Pipeline/plugin architecture to replace system behavior like JWT communication, etc

            logger.info("Attempting to GetBusinessEntities for " + entityTypeName);

            request = exchange.getRequest();
            queryParams = request.getQueryParams();

            //NOTE: http://localhost:8080/EntityType?where="Id"%3D1&sortBy="Ordinal"%252C"Id"&asOfDateTimeUtc=2023-01-01T00:00:00.000&graphDepthLimit=1&pageNumber=1&pageSize=20

            //TODO: Build query parameter array while validating explicit filter criteria and logging invalid attribute names, etc to be returned (see Create GET Method AAF-48)
                //TODO: Add pagination, e.g. pageNumber, pageSize, with master defaults and max
                //TODO: Filter out all deleted in function
                //TODO: Filter out all IsActive = false by default in function

            //TODO: Use GetBusinessEntities() to cache EntityTypeDefinition, EntityTypeAttribute, and EntityTypeDefinitionEntityTypeAttributeAssociation for input validation at service startup
            //TODO: Validate EntityType name and attributes here rather than in function

            //TODO: Add automation batch script at infrastructure root
            //TODO: Add uniqueness contraints to table/model scripts
            //TODO: Add indexes to table/model scripts

            connection = DatabaseConnection.getConnection();

            //NOTE: Validate EntityType name
            statement = connection.prepareCall("{call \"EntityDataRead\"(?,?)}");
            statement.setString(1, entityTypeName);

            //NOTE: Register the OUT parameter before calling the stored procedure
            statement.registerOutParameter(2, Types.LONGVARCHAR);   //TODO: Return total number of entities from function
            statement.executeUpdate();

            //TODO: Filter or mask unauthorized or sensitive attributes

            //NOTE: Read the OUT parameter now
            entityData = statement.getString(2);

            if (entityData.indexOf(entityTypeName) < 0)
            {
                throw new Exception("EntityType '" + entityTypeName + "' not found in EntityTypeDefinition table, i.e. not a valid EntityType.");
            }

            //TODO: Check for query parameters before checking them
            sqlValues = environment.getProperty("sqlNotToAllow").toLowerCase().split(",");
            errorValues = GuardAgainstSqlIssues(queryParams.toString(), sqlValues);

            //TODO: If no asOfDateTimeUtc, grab ISO string value of Now() in database server's time zone
            //asOfDateTimeUtc = LocalDateTime.parse(String.valueOf(queryParams.get("asOfDateTimeUtc").get(0)));

            //TODO: If no graphDepthLimit, use default
            graphDepthLimit = parseLong(String.valueOf(queryParams.get("graphDepthLimit").get(0)), 10);

            //TODO: If no pageNumber, use default
            pageNumber = parseLong(String.valueOf(queryParams.get("pageNumber").get(0)), 10);

            //TODO: If no pageSize, use default
            pageSize = parseLong(String.valueOf(queryParams.get("pageSize").get(0)), 10);

            if (errorValues.length() == 0)
            {
                //connection = DatabaseConnection.getConnection();
                statement = connection.prepareCall("{call \"EntityDataRead\"(?,?)}");
                statement.setString(1, entityTypeName);

                //NOTE: Register the data OUT parameter before calling the stored procedure
                statement.registerOutParameter(2, Types.LONGVARCHAR);
                statement.executeUpdate();

                //TODO: Filter or mask unauthorized or sensitive attributes

                //NOTE: Read the OUT parameter now
                //TODO: Check for null entityData
                entityData = statement.getString(2);
            }
        }
        catch (Exception e)
        {
            logger.error("GetBusinessEntities failed due to: " + e);
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
                }
            }
            catch (SQLException e)
            {
                logger.error("Failed to close statement and/or connection resource in GetBusinessEntities due to: " + e);
            }
        }

        logger.info("GetBusinessEntities succeeded for " + entityTypeName);
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
