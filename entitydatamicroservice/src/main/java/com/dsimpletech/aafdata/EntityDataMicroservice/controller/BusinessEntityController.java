package com.dsimpletech.aafdata.EntityDataMicroservice.controller;

//import org.springframework.data.r2dbc.repository.Query;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String GetBusinessEntities(@PathVariable String entityTypeName) throws SQLException {

        Connection connection = null;
        CallableStatement statement = null;
        String result = "";

        //TODO: Add basic query filters
        //TODO: Add pagination
        //TODO: Add "as of" date (placeholder for later temporal operations)

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareCall("{call \"EntityDataRead\"(?,?)}");
            statement.setString(1, entityTypeName);

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

            return "\"EntityData\":" + result;
        }
    }
}
