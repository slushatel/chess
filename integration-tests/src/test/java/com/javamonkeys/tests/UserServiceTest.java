package com.javamonkeys.tests;

import com.javamonkeys.dao.user.User;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.*;

public class UserServiceTest {

    private static final String baseUrl = "http://localhost:8555";
    private CustomResponseErrorHandler customResponseErrorHandler = new CustomResponseErrorHandler();

    @BeforeClass
    public static void init(){

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());

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

        // 2. ADD user "DeleteUser@javamonkeys.com", pass: 12345 if not exist

        // DeleteUser@javamonkeys.com / 12345 / in Base64
        String basicAuthDeleteUser = "Basic RGVsZXRlVXNlckBqYXZhbW9ua2V5cy5jb206MTIzNDU=";

        headers = new HttpHeaders();
        headers.add("Authorization", basicAuthDeleteUser);
        entity = new HttpEntity<String>(headers);

        responseEntityUser = restTemplate.exchange(baseUrl + "/api/users/login", HttpMethod.GET, entity, User.class);
        if (responseEntityUser.getStatusCode() == HttpStatus.BAD_REQUEST) { // user not found
            ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "/api/users/register", HttpMethod.POST, entity, String.class);
            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        }

        // 3. ADD user "UpdateUser@javamonkeys.com", pass: 12345 if not exist

        // UpdateUser@javamonkeys.com / 12345 / in Base64
        String basicAuthUpdateUser = "Basic VXBkYXRlVXNlckBqYXZhbW9ua2V5cy5jb206MTIzNDU=";

        headers = new HttpHeaders();
        headers.add("Authorization", basicAuthUpdateUser);
        entity = new HttpEntity<String>(headers);

        responseEntityUser = restTemplate.exchange(baseUrl + "/api/users/login", HttpMethod.GET, entity, User.class);
        if (responseEntityUser.getStatusCode() == HttpStatus.BAD_REQUEST) { // user not found
            ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "/api/users/register", HttpMethod.POST, entity, String.class);
            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        }

        // 4. DELETE user "NewUserTest@javamonkeys.com" if exist

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

    ///////////////////////////////////////// LOGIN //////////////////////////////////////////

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
        assertEquals("Filippov@javamonkeys.com".toLowerCase(), responseEntity.getBody().getEmail().toLowerCase());
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

    //////////////////////////////////////// LOGOUT /////////////////////////////////////////

    @Test
    public void testLogout() {

        // Filippov@javamonkeys.com / 12345 / in Base64
        String basicAuth = "Basic RmlsaXBwb3ZAamF2YW1vbmtleXMuY29tOjEyMzQ1";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(customResponseErrorHandler);

        // LOGIN
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", basicAuth);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<User> responseEntityUser = restTemplate.exchange(baseUrl + "/api/users/login", HttpMethod.GET, entity, User.class);
        User beforeUser = responseEntityUser.getBody();

        // LOGOUT
        headers = new HttpHeaders();
        headers.add("id", Integer.toString(beforeUser.getId()));
        entity = new HttpEntity<String>(headers);

        ResponseEntity responseEntity = restTemplate.exchange(baseUrl + "/api/users/logout", HttpMethod.POST, entity, User.class);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // LOGOUT with incorrect id
        headers = new HttpHeaders();
        headers.add("id", "-999");
        entity = new HttpEntity<String>(headers);

        responseEntity = restTemplate.exchange(baseUrl + "/api/users/logout", HttpMethod.POST, entity, User.class);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    /////////////////////////////////////// GET USER ///////////////////////////////////////

    @Test
    public void testGetUser() {

        // Filippov@javamonkeys.com / 12345 / in Base64
        String basicAuth = "Basic RmlsaXBwb3ZAamF2YW1vbmtleXMuY29tOjEyMzQ1";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(customResponseErrorHandler);

        // LOGIN for check user id
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", basicAuth);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<User> responseEntityUser = restTemplate.exchange(baseUrl + "/api/users/login", HttpMethod.GET, entity, User.class);
        User firstUser = responseEntityUser.getBody();

        // Correct user id
        headers = new HttpHeaders();
        entity = new HttpEntity<String>(headers);

        responseEntityUser = restTemplate.exchange(baseUrl + "/api/users/" + firstUser.getId(), HttpMethod.GET, entity, User.class);
        User secondUser = responseEntityUser.getBody();

        assertEquals(HttpStatus.OK, responseEntityUser.getStatusCode());
        assertEquals(firstUser.getId(), secondUser.getId());
        assertEquals(firstUser.getEmail(), secondUser.getEmail());

        // Incorrect user id
        headers = new HttpHeaders();
        entity = new HttpEntity<String>(headers);

        responseEntityUser = restTemplate.exchange(baseUrl + "/api/users/-999", HttpMethod.GET, entity, User.class);

        assertEquals(HttpStatus.OK, responseEntityUser.getStatusCode());
        assertNull(responseEntityUser.getBody());
    }

    ///////////////////////////////////// DELETE USER //////////////////////////////////////

    @Test
    public void testDeleteUser() {

        // DeleteUser@javamonkeys.com / 12345 / in Base64
        String basicAuth = "Basic RGVsZXRlVXNlckBqYXZhbW9ua2V5cy5jb206MTIzNDU=";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(customResponseErrorHandler);

        // LOGIN for check user id
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", basicAuth);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<User> responseEntityUser = restTemplate.exchange(baseUrl + "/api/users/login", HttpMethod.GET, entity, User.class);
        User currentUser = responseEntityUser.getBody();

        // DELETE user
        headers = new HttpHeaders();
        entity = new HttpEntity<String>(headers);

        ResponseEntity responseEntity = restTemplate.exchange(baseUrl + "/api/users/" + currentUser.getId(), HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // Check user
        headers = new HttpHeaders();
        entity = new HttpEntity<String>(headers);

        responseEntityUser = restTemplate.exchange(baseUrl + "/api/users/" + currentUser.getId(), HttpMethod.GET, entity, User.class);

        assertEquals(HttpStatus.OK, responseEntityUser.getStatusCode());
        assertNull(responseEntity.getBody());

        // Incorrect user id
        headers = new HttpHeaders();
        entity = new HttpEntity<String>(headers);

        responseEntity = restTemplate.exchange(baseUrl + "/api/users/-999", HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    ///////////////////////////////////// UPDATE USER //////////////////////////////////////

    @Test
    public void testUpdateUser() {

        // UpdateUser@javamonkeys.com / 12345 / in Base64
        String basicAuth = "Basic VXBkYXRlVXNlckBqYXZhbW9ua2V5cy5jb206MTIzNDU=";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(customResponseErrorHandler);

        // LOGIN for check user id
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", basicAuth);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<User> responseEntityUser = restTemplate.exchange(baseUrl + "/api/users/login", HttpMethod.GET, entity, User.class);
        User beforeUser = responseEntityUser.getBody();

        // UPDATE user
        String beforeName = beforeUser.getName();
        String afterName  = (beforeName == null || beforeName.isEmpty()) ? "update user" : beforeName + "_postfix)";

        Date beforeBirthDate = beforeUser.getBirthDate();
        Date afterBirthDate;

        if (beforeBirthDate == null) {
            LocalDate newLocalDate = LocalDate.now();
            Instant instant = newLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            afterBirthDate = Date.from(instant);
        } else {
             afterBirthDate = null;
        }

        assertNotEquals(beforeName, afterName);
        assertNotEquals(beforeBirthDate, afterBirthDate);

        beforeUser.setName(afterName);
        beforeUser.setBirthDate(afterBirthDate);

        headers = new HttpHeaders();
        HttpEntity<User> entityUser = new HttpEntity<User>(beforeUser, headers);

        ResponseEntity responseEntity = restTemplate.exchange(baseUrl + "/api/users/" + beforeUser.getId(), HttpMethod.PUT, entityUser, String.class);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // Check user
        headers = new HttpHeaders();
        entity = new HttpEntity<String>(headers);

        responseEntityUser = restTemplate.exchange(baseUrl + "/api/users/" + beforeUser.getId(), HttpMethod.GET, entity, User.class);
        User afterUser = responseEntityUser.getBody();

        assertEquals(HttpStatus.OK, responseEntityUser.getStatusCode());
        assertNotNull(afterUser);

        assertEquals(afterName, afterUser.getName());
        assertEquals(afterBirthDate, afterUser.getBirthDate());

        // Incorrect user id
        headers = new HttpHeaders();
        entityUser = new HttpEntity<User>(beforeUser, headers);

        responseEntity = restTemplate.exchange(baseUrl + "/api/users/-999", HttpMethod.PUT, entityUser, User.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}
