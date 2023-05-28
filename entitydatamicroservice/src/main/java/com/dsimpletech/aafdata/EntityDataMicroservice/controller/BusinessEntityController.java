package com.dsimpletech.aafdata.EntityDataMicroservice.controller;

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
        String result = "";

        //TODO: Validate EntityType name and attributes here rather than in function

        //TODO: Add basic query filters, e.g. =, IN, LIKE, etc
        //TODO: Add sort order, e.g. ASC, DESC
        //TODO: Add pagination, e.g. Offset, PageSize, with defaults and max
        //TODO: Add "as of" datetime (placeholder for later temporal operations)

        //TODO: Return total matching records

        //TODO: Return Json rather than String???

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareCall("{call \"EntityDataRead\"(?,?)}");
            statement.setString(1, entityTypeName);

            request = exchange.getRequest();
            queryParams = request.getQueryParams();

            //NOTE: Register the OUT parameter before calling the stored procedure
            statement.registerOutParameter(2, Types.LONGVARCHAR);
            statement.executeUpdate();

            //NOTE: Read the OUT parameter now
            result = statement.getString(2);
        }
        catch(Exception e)
        {
            e.printStackTrace();    //TODO: Log this
            return "";
        }
        finally
        {
            try
            {
                statement.close();
                connection.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();    //TODO: Log this
            }
        }

        return "\"EntityData\":" + result;
    }
}
