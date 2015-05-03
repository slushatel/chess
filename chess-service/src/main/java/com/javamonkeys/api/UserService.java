package com.javamonkeys.api;

import com.javamonkeys.dao.user.*;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.util.Date;

@Service("userService")
@RestController
@RequestMapping("/users")
public class UserService implements IUserService {

    @Inject
    IUserDao userDao;

    @Transactional
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void register(@RequestHeader(value="Authorization") String authorization) throws UserAlreadyExistException, IncorrectUserCredentialsException {

        if(authorization == null)
            throw new IncorrectUserCredentialsException();

        String[] credentials = getCredentialsFromBase64String(authorization);
        userDao.register(credentials[0], credentials[1]);
    }

    @Transactional
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody String login(@RequestHeader(value="Authorization") String authorization) throws IncorrectUserCredentialsException {

        if(authorization == null)
            throw new IncorrectUserCredentialsException();

        String[] credentials = getCredentialsFromBase64String(authorization);
        return userDao.login(credentials[0], credentials[1]);
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
    public @ResponseBody User getUser(@PathVariable("id") String id) {
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
    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="User with this credentials was not found!")
    @ExceptionHandler(IncorrectUserCredentialsException.class)
    public void IncorrectUserCredentialsExceptionHandler() {}

    // Convert a UserAlreadyExistException exception to an HTTP Status code
    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="User with this credentials already exists!")
    @ExceptionHandler(UserAlreadyExistException.class)
    public void UserAlreadyExistExceptionHandler() {}

    // Convert a UserNotFoundException exception to an HTTP Status code
    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="User was not found!")
    @ExceptionHandler(UserNotFoundException.class)
    public void UserNotFoundExceptionHandler() {}

    private String[] getCredentialsFromBase64String(String authorization) {

        String[] credentials = new String[]{"",""};

        if (authorization != null && authorization.startsWith("Basic")) {
            String base64Credentials = authorization.substring("Basic".length()).trim();
            String decodedCredentials = new String(Base64.decode(base64Credentials), Charset.forName("UTF-8"));
            credentials = decodedCredentials.split(":", 2);
        }

        return credentials;
    }

    // TEST METHOD FOR ENCODE / DECODE DATA
    @RequestMapping(value = "/GetBase64", method = RequestMethod.POST)
    public String GetBase64(@RequestParam("email") String email, @RequestParam("password") String password){
        return Base64.encode(("" + email + ":" + password).getBytes());
    }
}
