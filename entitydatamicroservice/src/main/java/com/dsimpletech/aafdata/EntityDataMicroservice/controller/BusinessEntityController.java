package com.dsimpletech.aafdata.EntityDataMicroservice.controller;


//import org.json.*;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

//import org.springframework.http.server.reactive.ServerHttpRequest;

//import org.springframework.util.MultiValueMap;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.server.ServerWebExchange;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import com.dsimpletech.aafdata.EntityDataMicroservice.database.DatabaseConnection;


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
    public String GetBusinessEntities(@PathVariable String entityTypeName, ServerWebExchange exchange)
    {

        Connection connection = null;
        CallableStatement statement = null;
        //MultiValueMap<String,String> queryParams = null;
        //ServerHttpRequest request = null;
        String entityData = "";
        //String result = "";
        String[] filterIssueValues = null;
        String errorValues = "";
        String warnValues = "";

        //TODO: In Postgres function, return total matching records, e.g. SELECT COUNT(Id)

        //TODO: Remove direct table access with SQL GRANTs; CRUD only enforced upstream through process service calls

        //NOTE: Since we're not automatically parsing the resulting JSON into an object, we're returning a JSON String rather than a JSONObject

        try
        {
            //TODO: Validate API key upstream in Client Communication Service (CCS)
            //TODO: Validate JWT upstream in Client Communication Service (CCS)
            //TODO: Validate authenticated user and API key OrganizationalUnit association if Employee upstream in Client Communication Service (CCS)
            //TODO: Validate entity data authorization, i.e. appropriate permissions for requested entity and operation? upstream in Client Communication Service (CCS)

            //TODO: Add version to base URL? Versioning: simple, explicit (v1.2.3) or internal, based on/derived from AsOfUtcDateTime?

            //request = exchange.getRequest();
            //queryParams = request.getQueryParams();

            //TODO: Build query parameter array while validating explicit filter criteria and logging invalid attribute names, etc to be returned (see Create GET Method AAF-48)
                //TODO: Add basic query filters, e.g. =, IN, LIKE, etc, by data type
                //TODO: Add sort order, e.g. ASC, DESC, including defaults like name, Ordinal, DESC, etc
                //TODO: Add pagination, e.g. pageNumber, pageSize, with master defaults and max
                //TODO: Add asOfDateTimeUtc (placeholder for later temporal operations), including NOW() default
                //TODO: Filter out all deleted
                //TODO: Filter out all IsActive = false by default

            //TODO: Use GetBusinessEntities() to cache EntityTypeDefinition, EntityTypeAttribute, and EntityTypeDefinitionEntityTypeAttributeAssociation for input validation at service startup
            //TODO: Validate EntityType name and attributes here rather than in function

            filterIssueValues = new String[]{environment.getProperty("sqlToErrorAndReport").toLowerCase()};
            errorValues = GuardAgainstSqlIssues("Delete".toLowerCase(), filterIssueValues);

            filterIssueValues = new String[]{environment.getProperty("sqlToWarnAndReport").toLowerCase() + "," + environment.getProperty("sqlToWarnAndReportAggregate").toLowerCase() + "," + environment.getProperty("sqlToWarnAndReportString").toLowerCase()};
            warnValues = GuardAgainstSqlIssues("JOIN,max".toLowerCase(), filterIssueValues);

            if (errorValues.length() == 0) {
                connection = DatabaseConnection.getConnection();
                statement = connection.prepareCall("{call \"EntityDataRead\"(?,?)}");
                statement.setString(1, entityTypeName);

                //NOTE: Register the OUT parameter before calling the stored procedure
                statement.registerOutParameter(2, Types.LONGVARCHAR);   //TODO: Return total number of entities from function
                statement.executeUpdate();

                //TODO: Filter or mask unauthorized or sensitive attributes

                //NOTE: Read the OUT parameter now
                entityData = statement.getString(2);
            }
        }
        catch (Exception e)
        {
            logger.error("GetBusinessEntities failed due to: " + e);
            return "{}";
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
        return "{\"EntityData\":" + entityData + "}";
    }

    public String GuardAgainstSqlIssues(String sql, String[] filterIssueValues)
    {
        String issueValues = "";
        //String[] filterIssueValues = null;

        try
        {
            //NOTE: Based on Spring Tips: Configuration (https://spring.io/blog/2020/04/23/spring-tips-configuration)
            //filterIssueValues = new String[]{environment.getProperty("sqlToErrorAndReport").toLowerCase()};

            for (String issueValue : filterIssueValues)
            {
                if (sql.contains(issueValue))
                {
                    issueValues += issueValue + ",";
                }
            }
        }
        catch (Exception e)
        {
            logger.error("GuardAgainstSqlIssues failed due to: " + e);
            return issueValues;
        }

        logger.info("GuardAgainstSqlIssues succeeded when " + sql + "contained" + issueValues + " listed in " + filterIssueValues.toString());
        return issueValues;
    }
}
