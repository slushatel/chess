package com.javamonkeys.api;

import com.javamonkeys.dao.user.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.util.Base64;

@RestController
@RequestMapping("/api/users")
public class UserService implements IUserService {

    @Inject
    IUserDao userDao;

    @Transactional
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestHeader(value = "Authorization", required=false) String authorization) {

        try {
            String[] credentials = getCredentialsFromBase64String(authorization);
            userDao.register(credentials[0], credentials[1]);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (IllegalArgumentException | UserAlreadyExistException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<User> login(@RequestHeader(value = "Authorization", required=false) String authorization) {

        try {
            String[] credentials = getCredentialsFromBase64String(authorization);
            User currentUser = userDao.login(credentials[0], credentials[1]);
            return new ResponseEntity<User>(currentUser, HttpStatus.OK);
        } catch (IllegalArgumentException | IncorrectUserCredentialsException e) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity logout(@RequestHeader("id") Integer id) {
        User currentUser = userDao.getUserById(id);
        userDao.logout(currentUser);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") Integer id) {
        return new ResponseEntity<User>(userDao.getUserById(id), HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("id") Integer id) {

        try {
            userDao.deleteUser(userDao.getUserById(id));
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException | IllegalArgumentException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@PathVariable("id") Integer id, @RequestBody User sourceUser) {

        User currentUser = userDao.getUserById(id);
        if (currentUser == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        currentUser.loadValues(sourceUser);
        try {
            userDao.updateUser(currentUser);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    // Decode string credentials from base64
    // return array of 2 elements (credentials) if decoding was successful, or empty array - otherwise
    private String[] getCredentialsFromBase64String(String authorization) throws IllegalArgumentException {

        if (authorization != null && authorization.startsWith("Basic")) {
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] decodedData = Base64.getDecoder().decode(base64Credentials);
            if (decodedData != null) {
                String decodedCredentials = new String(decodedData, Charset.forName("UTF-8"));
                return  decodedCredentials.split(":", 2);
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

}
