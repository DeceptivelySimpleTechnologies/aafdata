//NOTE: Copyright © 2003-2024 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

package com.dsimpletech.aafdata.EntityDataMicroservice;


import org.junit.Before;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import com.dsimpletech.aafdata.EntityDataMicroservice.controller.BusinessEntityController;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BusinessEntityControllerTest
{
//    @Autowired
    private TestRestTemplate restTemplate;

//    @Before
//    public void setup() {
//        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
//    }

    @Autowired
    private Environment environment;

    @Autowired
    private BusinessEntityController businessEntityController;

    @BeforeEach
    public void setUp() {
        // Set up any necessary environment properties or configurations here
    }

    private int entityId = -1;


    //TODO: *** Health check
    //TODO: *** API docs
    //TODO: *** Swagger UI


    //TODO: *** testPostBusinessEntityPersonWithoutApiKeyWithAuthenticationCookieAndWithValidRequestBody
    //TODO: *** testPostBusinessEntityPersonWithApiKeyWithoutAuthenticationCookieAndWithValidRequestBody
    //TODO: *** testPostBusinessEntityPersonWithApiKeyWithAuthenticationCookieAndWithoutValidRequestBody

    @Test
    @Order(2000)
	public void testPostBusinessEntityPersonWithApiKeyWithAuthenticationCookieAndWithValidRequestBody() throws Exception
    {
        int entityIdStart = -1;
        int entityIdEnd = -1;

        try
        {
            //NOTE: Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("ApiKey", "8f67f68e-dec9-4fdb-be0b-10e4f0451e42");
            headers.add("Cookie", "Authentication=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0ZWQiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiJlZjRhZjRlMy1lNzM2LTQyNWEtYWFmZi1lY2EwM2I3YjliMjgiLCJib2R5Ijp7IkVtYWlsQWRkcmVzcyI6ImFteS5hbmRlcnNvbkBhbXlzYWNjb3VudGluZy5jb20ifX0.Djq5LYPEK1QFgBk9aN5Vei37K6Cb8TxNH3ADWDcUaHs");

            //NOTE: Create request body
            String requestBody = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IlBPU1QgL0VudGl0eVR5cGUiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiI4NzUyZjIzYi0xYTliLTQyMmEtOGIyNi0zNzQyNDM0ZGY0NzYiLCJib2R5Ijp7IkVudGl0eVN1YnR5cGVJZCI6LTEsIlRleHRLZXkiOiJwZXJzb24tbm9uZS1iaWxsLWJha2VyIiwiTGVnYWxHaXZlbk5hbWUiOiJCaWxsIiwiTGVnYWxTdXJuYW1lIjoiQmFrZXIiLCJCb3JuQXREYXRlVGltZVV0YyI6IjIwMDItMDItMDMgMTE6MTI6MTMuMTIzIiwiTGVnYWxDaXRpemVuT2ZDb3VudHJ5R2VvZ3JhcGhpY1VuaXRJZCI6MSwiTG9jYWxlSWQiOjEsIk9yZGluYWwiOi0xLCJJc0FjdGl2ZSI6dHJ1ZX19.rWNowmEoPkF8N0Q5KC5-W83g3hMqIf9TV8KHzLgNbio";

            //NOTE: Create entity with headers and body
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            //NOTE: Call the method
            restTemplate = new TestRestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/entityTypes/Person", entity, String.class);

            entityIdStart = response.getBody().indexOf("\"Id\":") + 5;
            System.out.println("entityIdStart: " + entityIdStart);

            entityIdEnd = response.getBody().indexOf(",", entityIdStart);
            System.out.println("entityIdEnd: " + entityIdEnd);

            entityId = Integer.parseInt(response.getBody().substring(entityIdStart, entityIdEnd));
            System.out.println("entityId: " + entityId);

            //NOTE: Verify the response
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            //TODO: Check for "CorrelationUuid" that came in with request
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
	}


    //TODO: *** testGetBusinessEntityPersonWithoutApiKeyWithAuthenticationCookieWithoutWhereClauseAndWithoutSortClause
    //TODO: *** testGetBusinessEntityPersonWithApiKeyWithoutAuthenticationCookieWithoutWhereClauseAndWithoutSortClause

    @Test
    @Order(3000)
    public void testGetBusinessEntityPersonWithApiKeyWithAuthenticationCookieWithWhereClauseAndWithSortClause() throws Exception
    {
        try
        {
            //NOTE: Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("ApiKey", "8f67f68e-dec9-4fdb-be0b-10e4f0451e42");
            headers.add("Cookie", "Authentication=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0ZWQiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiJlZjRhZjRlMy1lNzM2LTQyNWEtYWFmZi1lY2EwM2I3YjliMjgiLCJib2R5Ijp7IkVtYWlsQWRkcmVzcyI6ImFteS5hbmRlcnNvbkBhbXlzYWNjb3VudGluZy5jb20ifX0.Djq5LYPEK1QFgBk9aN5Vei37K6Cb8TxNH3ADWDcUaHs");

            //NOTE: Create WHERE clause
            String whereClause = "%22Id%22%20%3E%200";    //NOTE: URL-encoded '"Id" > 0'

            //NOTE: Create sort by (ORDER BY) clause
            String sortClause = "%22LegalSurname%22%20ASC%2C%20%22LegalGivenName%22%20ASC%2C%20%22BornAtDateTimeUtc%22%20ASC";  //NOTE: URL-encoded '"LegalSurname" ASC, "LegalGivenName" ASC, "BornAtDateTimeUtc" ASC'

            //NOTE: Create request body
            String requestBody = "";    //NOTE: HTTP GET requests should not have a request body (ignored if present)

            //NOTE: Create entity with headers and body
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            //NOTE: Call the method
            restTemplate = new TestRestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/entityTypes/Person?whereClause=" + whereClause + "&sortClause=" + sortClause, String.class);

            //NOTE: Verify the response
            assertEquals(HttpStatus.OK, response.getStatusCode());
            //TODO: Check for non-empty "EntityData" JSON array
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    //TODO: *** testGetBusinessEntityPersonWithApiKeyWithAuthenticationCookieWithWhereClauseAndWithoutSortClause
    //TODO: *** testGetBusinessEntityPersonWithApiKeyWithAuthenticationCookieWithoutWhereClauseAndWithSortClause
    //TODO: *** testGetBusinessEntityPersonWithApiKeyWithAuthenticationCookieWithoutWhereClauseAndWithoutSortClause
    //TODO: *** Test pagination with pageNumber and pageSize parameters, e.g. no pageNumber = 0, etc


    //TODO: *** testUpdateBusinessEntityPersonWithoutApiKeyWithAuthenticationCookieWithEntityIdAndWithValidRequestBody
    //TODO: *** testUpdateBusinessEntityPersonWithApiKeyWithoutAuthenticationCookieWithEntityIdAndWithValidRequestBody
    //TODO: *** testUpdateBusinessEntityPersonWithApiKeyWithAuthenticationCookieWithoutEntityIdAndWithValidRequestBody
    //TODO: *** testUpdateBusinessEntityPersonWithApiKeyWithAuthenticationCookieWithEntityIdAndWithoutValidRequestBody

//    @Test
//    @Order(4000)
//    public void testUpdateBusinessEntityPersonWithApiKeyWithAuthenticationCookieWithEntityIdAndWithValidRequestBody() throws Exception
//    {
//        try
//        {
//            //NOTE: Create headers
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("ApiKey", "8f67f68e-dec9-4fdb-be0b-10e4f0451e42");
//            headers.add("Cookie", "Authentication=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0ZWQiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiJlZjRhZjRlMy1lNzM2LTQyNWEtYWFmZi1lY2EwM2I3YjliMjgiLCJib2R5Ijp7IkVtYWlsQWRkcmVzcyI6ImFteS5hbmRlcnNvbkBhbXlzYWNjb3VudGluZy5jb20ifX0.Djq5LYPEK1QFgBk9aN5Vei37K6Cb8TxNH3ADWDcUaHs");
//
//            //NOTE: Create request body
//            String requestBody = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IlBBVENIIC9FbnRpdHlUeXBlIiwiYXVkIjoiQUFGRGF0YS1FbnRpdHlEYXRhTWljcm9zZXJ2aWNlIiwiZXhwIjoxNzIzODE2OTIwLCJpYXQiOjE3MjM4MTY4MDAsIm5iZiI6MTcyMzgxNjc4OSwianRpIjoiMzZkMGRiMjYtZjIyYy00NTc3LTgwNzYtMTZjZGFkMThjZDU4IiwiYm9keSI6eyJMZWdhbEdpdmVuTmFtZSI6IldpbGxpYW0iLCJMZWdhbFN1cm5hbWUiOiJCYWtlci1QQVRDSEVEIiwiQm9ybkF0RGF0ZVRpbWVVdGMiOiIyMDAyLTAzLTA0IDEyOjEzOjE0LjIzNCJ9fQ.kZRsD0iQ0gADzWEkY2-8R80TDlhC4Jm1P3qWYLwbkhk";
//
//            //NOTE: Create entity with headers and body
//            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
//
//            //NOTE: Call the method
//            restTemplate = new TestRestTemplate();
//            ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/entityTypes/Person/" + entityId, HttpMethod.PATCH, entity, String.class);
//
//            //NOTE: Verify the response
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            //TODO: Check for correctly updated values
//        }
//        catch (Exception e)
//        {
//            throw new RuntimeException(e);
//        }
//    }


    //TODO: *** testDeleteBusinessEntityPersonWithoutApiKeyWithAuthenticationCookieWithEntityIdAndWithValidRequestBody
    //TODO: *** testDeleteBusinessEntityPersonWithApiKeyWithoutAuthenticationCookieWithEntityIdAndWithValidRequestBody
    //TODO: *** testDeleteBusinessEntityPersonWithApiKeyWithAuthenticationCookieWithoutEntityIdAndWithValidRequestBody
    //TODO: *** testDeleteBusinessEntityPersonWithApiKeyWithAuthenticationCookieWithEntityIdAndWithoutValidRequestBody

//    @Test
//    @Order(5000)
//    public void testDeleteBusinessEntityPersonWithApiKeyWithAuthenticationCookieWithEntityIdAndWithValidRequestBody() throws Exception
//    {
//        try
//        {
//            //NOTE: Create headers
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("ApiKey", "8f67f68e-dec9-4fdb-be0b-10e4f0451e42");
//            headers.add("Cookie", "Authentication=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0ZWQiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiJlZjRhZjRlMy1lNzM2LTQyNWEtYWFmZi1lY2EwM2I3YjliMjgiLCJib2R5Ijp7IkVtYWlsQWRkcmVzcyI6ImFteS5hbmRlcnNvbkBhbXlzYWNjb3VudGluZy5jb20ifX0.Djq5LYPEK1QFgBk9aN5Vei37K6Cb8TxNH3ADWDcUaHs");
//
//            //NOTE: Create request body
//            String requestBody = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1pbiJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IlBPU1QgL0VudGl0eVR5cGUiLCJhdWQiOiJBQUZEYXRhLUVudGl0eURhdGFNaWNyb3NlcnZpY2UiLCJleHAiOjE3MjM4MTY5MjAsImlhdCI6MTcyMzgxNjgwMCwibmJmIjoxNzIzODE2Nzg5LCJqdGkiOiI4NzUyZjIzYi0xYTliLTQyMmEtOGIyNi0zNzQyNDM0ZGY0NzYiLCJib2R5Ijp7IkVudGl0eVN1YnR5cGVJZCI6LTEsIlRleHRLZXkiOiJwZXJzb24tbm9uZS1iaWxsLWJha2VyIiwiTGVnYWxHaXZlbk5hbWUiOiJCaWxsIiwiTGVnYWxTdXJuYW1lIjoiQmFrZXIiLCJCb3JuQXREYXRlVGltZVV0YyI6IjIwMDItMDItMDMgMTE6MTI6MTMuMTIzIiwiTGVnYWxDaXRpemVuT2ZDb3VudHJ5R2VvZ3JhcGhpY1VuaXRJZCI6MSwiTG9jYWxlSWQiOjEsIk9yZGluYWwiOi0xLCJJc0FjdGl2ZSI6dHJ1ZX19.rWNowmEoPkF8N0Q5KC5-W83g3hMqIf9TV8KHzLgNbio";
//
//            //NOTE: Create entity with headers and body
//            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
//
//            //NOTE: Call the method
//            restTemplate = new TestRestTemplate();
//            ResponseEntity<String> response = restTemplate.delete("http://localhost:8080/entityTypes/Person");
//
//            //NOTE: Verify the response
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//        }
//        catch (Exception e)
//        {
//            throw new RuntimeException(e);
//        }
//    }

}
