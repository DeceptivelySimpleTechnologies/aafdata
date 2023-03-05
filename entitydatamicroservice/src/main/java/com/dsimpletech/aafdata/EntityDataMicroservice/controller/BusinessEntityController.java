package com.dsimpletech.aafdata.EntityDataMicroservice.controller;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class BusinessEntityController {

    @PostMapping
    @Query("DECLARE\n" +
            "    EntityId bigint;\n" +
            "    EntityDataOutput json[];\n" +
            "BEGIN\n" +
            "    INSERT INTO aaf.\"Entity\"\n" +
            "    (\"Id\", \"Name\", \"Updated\")\n" +
            "    VALUES (default, 'Test', (now() AT time zone 'utc'))\n" +
            "    RETURNING \"Id\" INTO EntityId;\n" +
            "\n" +
            "    SELECT json_agg(t)\n" +
            "    INTO EntityDataOutput\n" +
            "    FROM (\n" +
            "             SELECT *\n" +
            "             FROM aaf.\"Entity\"\n" +
            "             WHERE \"Id\" = EntityId\n" +
            "         ) t;\n" +
            "END")
    public void CreateBusinessEntity() {

    }
}
