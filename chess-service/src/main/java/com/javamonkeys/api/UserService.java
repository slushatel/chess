package com.javamonkeys.api;

import com.javamonkeys.dao.user.*;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.nio.charset.Charset;

@RestController
@RequestMapping("/api/users")
public class UserService implements IUserService {

    @Inject
    IUserDao userDao;

    @Transactional
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> register(@RequestHeader(value = "Authorization", required=false) String authorization) {

        try {
            String[] credentials = getCredentialsFromBase64String(authorization);
            userDao.register(credentials[0], credentials[1]);
            return new ResponseEntity<String>(HttpStatus.CREATED);
        } catch (IllegalArgumentException | UserAlreadyExistException e) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<String> login(@RequestHeader(value = "Authorization", required=false) String authorization) {

        try {
            String[] credentials = getCredentialsFromBase64String(authorization);
            String token = userDao.login(credentials[0], credentials[1]);
            return new ResponseEntity<String>(token, HttpStatus.OK);
        } catch (IllegalArgumentException | IncorrectUserCredentialsException e) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void logout(@RequestHeader("token") String token) {
        User currentUser = userDao.getUserByToken(token);
        userDao.logout(currentUser);
    }

    @Transactional
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public
    @ResponseBody
    User getUser(@PathVariable("id") String id) {
        return userDao.getUserById(id);
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") String id) throws UserNotFoundException {
        userDao.deleteUser(userDao.getUserById(id));
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable("id") String id, @RequestBody User sourceUser) throws UserNotFoundException {

        User currentUser = userDao.getUserById(id);
        if (currentUser == null)
            throw new UserNotFoundException();

        currentUser.loadValues(sourceUser);
        userDao.updateUser(currentUser);
    }

    // Convert a IncorrectUserCredentialsException exception to an HTTP Status code
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User with this credentials was not found!")
    @ExceptionHandler(IncorrectUserCredentialsException.class)
    public void IncorrectUserCredentialsExceptionHandler() {
    }

    // Convert a UserAlreadyExistException exception to an HTTP Status code
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User with this credentials already exists!")
    @ExceptionHandler(UserAlreadyExistException.class)
    public void UserAlreadyExistExceptionHandler() {
    }

    // Convert a UserNotFoundException exception to an HTTP Status code
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User was not found!")
    @ExceptionHandler(UserNotFoundException.class)
    public void UserNotFoundExceptionHandler() {
    }

    // Decode string credentials from base64
    // return array of 2 elements (credentials) if decoding was successful, or empty array - otherwise
    private String[] getCredentialsFromBase64String(String authorization) throws IllegalArgumentException {

        if (authorization != null && authorization.startsWith("Basic")) {
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] decodedData = Base64.decode(base64Credentials);
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
