package com.dsimpletech.aafdata.EntityDataMicroservice.controller;

//import org.springframework.data.r2dbc.repository.Query;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public void GetBusinessEntities() throws SQLException {

        Connection connection = null;
        CallableStatement statement = null;

        try {
//            String sql = "SELECT * FROM 'EntityType'.'EntityType'";
            connection = DatabaseConnection.getConnection();
//            statement = connection.prepareCall("{call insertEmployee(?,?,?,?,?,?)}");
            statement = connection.prepareCall("{call EntityDataRead(?,?)}");
//            statement.setInt(1, id);
            statement.setString(1, "EntityType");
//            statement.setString(2, name);
//            statement.setString(3, role);
//            statement.setString(4, city);
//            statement.setString(5, country);

            //register the OUT parameter before calling the stored procedure
            statement.registerOutParameter(2, Types.LONGVARCHAR);
//            statement.registerOutParameter(1, Types.TIMESTAMP);

            statement.executeUpdate();

            //read the OUT parameter now
            String result = statement.getString(2);
//            String result = statement.getString(1);

            System.out.println("Entity Data:: " + result);   //TODO: Log this
        }
        catch(Exception e)
        {
            e.printStackTrace();    //TODO: Log this
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
    }
}
