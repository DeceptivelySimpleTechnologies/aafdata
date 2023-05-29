package com.dsimpletech.aafdata.EntityDataMicroservice.controller;


//import org.json.*;

import org.springframework.http.server.reactive.ServerHttpRequest;

import org.springframework.util.MultiValueMap;

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
public class BusinessEntityController {

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
    public void CreateBusinessEntity() {
    }

    @GetMapping("/{entityTypeName}")
    @ResponseBody
    public String GetBusinessEntities(@PathVariable String entityTypeName, ServerWebExchange exchange) throws SQLException {

        Connection connection = null;
        CallableStatement statement = null;
        ServerHttpRequest request = null;
        MultiValueMap<String,String> queryParams = null;
        String entityData = "";
        String result = "";

        //TODO: In Postgres function, return total matching records, e.g. SELECT COUNT(Id)

        //NOTE: Since we're not automatically deserializing the resulting JSON into a class, we're returning a JSON String rather than a JSONObject

        try {
            //TODO: Validate API key
            //TODO: Validate JWT
            //TODO: Validate authenticated user and API key OrganizationalUnit association if Employee
            //TODO: Validate entity data authorization, i.e. appropriate permissions for requested entity and operation?

            request = exchange.getRequest();
            queryParams = request.getQueryParams();

            //TODO: Build query parameter array while validating explicit filter criteria and logging invalid attribute names, etc to be returned
                //TODO: Add basic query filters, e.g. =, IN, LIKE, etc, by data type
                //TODO: Add sort order, e.g. ASC, DESC, including defaults like name, Ordinal, DESC, etc
                //TODO: Add pagination, e.g. Offset, PageSize, with master defaults and max
                //TODO: Add "as of" datetime (placeholder for later temporal operations), including NOW() default
                //TODO: Filter out all deleted
                //TODO: Filter out all IsActive = false by default

                //TODO: Use GetBusinessEntities() to cache EntityTypeDefinition, EntityTypeAttribute, and EntityTypeDefinitionEntityTypeAttributeAssociation for input validation at service startup
                //TODO: Validate EntityType name and attributes here rather than in function

                //TODO: How to handle fragments, e.g. # parameters???

            connection = DatabaseConnection.getConnection();
            statement = connection.prepareCall("{call \"EntityDataRead\"(?,?)}");
            statement.setString(1, entityTypeName);

            //NOTE: Register the OUT parameter before calling the stored procedure
            statement.registerOutParameter(2, Types.LONGVARCHAR);
            statement.executeUpdate();

            //TODO: Filter or mask unauthorized or sensitive attributes

            //NOTE: Read the OUT parameter now
            entityData = statement.getString(2);
        }
        catch(Exception e)
        {
            e.printStackTrace();    //TODO: Log this
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
                e.printStackTrace();    //TODO: Log this
            }
        }

        //result = new JSONObject("{\"EntityData\":" + entityData + "}");
        return "{\"EntityData\":" + entityData + "}";
    }
}
