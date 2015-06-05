package com.javamonkeys.tests;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserServiceTest {

    private static final String baseUrl = "http://localhost:8555";
    private CustomResponseErrorHandler customResponseErrorHandler = new CustomResponseErrorHandler();

    @Before
    public void init(){
        // TODO: database initialization

        // 1. add user "Filippov@javamonkeys.com", pass: 12345 if not exist

        // 2. delete user "NewUserTest@javamonkeys.com" if exist

    }

    ///////////////////////////////////////// REGISTER /////////////////////////////////////////

    @Test
    public void testRegisterUserAlreadyExistsException() {

        // Filippov@javamonkeys.com / 12345 / in Base64
        String basicAuth = "Basic RmlsaXBwb3ZAamF2YW1vbmtleXMuY29tOjEyMzQ1";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(customResponseErrorHandler);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", basicAuth);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "/api/users/register", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    public void testRegisterIncorrectAuthorization() {

        HttpEntity<String> entity;
        HttpHeaders headers;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(customResponseErrorHandler);

        // NULL auth
        headers = new HttpHeaders();
        headers.add("Authorization", null);
        entity = new HttpEntity<String>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "/api/users/register", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());

        // Empty string auth
        headers.clear();
        headers.add("Authorization", "");
        entity = new HttpEntity<String>(headers);

        responseEntity = restTemplate.exchange(baseUrl + "/api/users/register", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());

        // Incorrect BASE64 string auth
        headers.clear();
        headers.add("Authorization", "Basic 1234567890abcdefghijk");
        entity = new HttpEntity<String>(headers);

        responseEntity = restTemplate.exchange(baseUrl + "/api/users/register", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());

    }

    @Test
    public void testRegisterNewUser() {

        // NewUserTest@javamonkeys.com / 12345 / in Base64
        String basicAuth = "Basic TmV3VXNlckBqYXZhbW9ua2V5cy5jb206MTIzNDU=";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(customResponseErrorHandler);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", basicAuth);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "/api/users/register", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    ///////////////////////////////////////// LOGIN /////////////////////////////////////////

    @Test
    public void testLoginUserAlreadyExists() {

        // Filippov@javamonkeys.com / 12345 / in Base64
        String basicAuth = "Basic RmlsaXBwb3ZAamF2YW1vbmtleXMuY29tOjEyMzQ1";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(customResponseErrorHandler);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", basicAuth);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "/api/users/login", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(38, responseEntity.getBody().length());
    }

    @Test
    public void testLoginUserNotFound() {

        // UserNotFound@javamonkeys.com / 12345 / in Base64
        String basicAuth = "Basic VXNlck5vdEZvdW5kQGphdmFtb25rZXlzLmNvbToxMjM0NQ==";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(customResponseErrorHandler);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", basicAuth);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "/api/users/login", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    public void testLoginIncorrectAuthorization() {

        HttpEntity<String> entity;
        HttpHeaders headers;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(customResponseErrorHandler);

        // NULL auth
        headers = new HttpHeaders();
        headers.add("Authorization", null);
        entity = new HttpEntity<String>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "/api/users/login", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());

        // Empty string auth
        headers.clear();
        headers.add("Authorization", "");
        entity = new HttpEntity<String>(headers);

        responseEntity = restTemplate.exchange(baseUrl + "/api/users/login", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());

        // Incorrect BASE64 string auth
        headers.clear();
        headers.add("Authorization", "Basic 1234567890abcdefghijk");
        entity = new HttpEntity<String>(headers);

        responseEntity = restTemplate.exchange(baseUrl + "/api/users/login", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());

    }
}
