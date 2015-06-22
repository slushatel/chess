package com.javamonkeys.tests;

import com.javamonkeys.dao.user.User;
import org.junit.Before;
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

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(customResponseErrorHandler);

        // 1. ADD user "Filippov@javamonkeys.com", pass: 12345 if not exist

        // Filippov@javamonkeys.com / 12345 / in Base64
        String basicAuthFilippov = "Basic RmlsaXBwb3ZAamF2YW1vbmtleXMuY29tOjEyMzQ1";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", basicAuthFilippov);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<User> responseEntityUser = restTemplate.exchange(baseUrl + "/api/users/login", HttpMethod.GET, entity, User.class);
        if (responseEntityUser.getStatusCode() == HttpStatus.BAD_REQUEST) { // user not found
            ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "/api/users/register", HttpMethod.POST, entity, String.class);
            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        }

        // 2. DELETE user "NewUserTest@javamonkeys.com" if exist

        // NewUserTest@javamonkeys.com / 12345 / in Base64
        String basicAuthNewUserTest = "Basic TmV3VXNlckBqYXZhbW9ua2V5cy5jb206MTIzNDU=";

        headers = new HttpHeaders();
        headers.add("Authorization", basicAuthNewUserTest);
        entity = new HttpEntity<String>(headers);

        responseEntityUser = restTemplate.exchange(baseUrl + "/api/users/login", HttpMethod.GET, entity, User.class);
        if (responseEntityUser.getStatusCode() == HttpStatus.OK){
            User currentUser = responseEntityUser.getBody();

            ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "/api/users/" + currentUser.getId(), HttpMethod.DELETE, entity, String.class);
            assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
        }
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

        ResponseEntity<User> responseEntity = restTemplate.exchange(baseUrl + "/api/users/login", HttpMethod.GET, entity, User.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Filippov@javamonkeys.com", responseEntity.getBody().getEmail());
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
