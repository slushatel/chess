package com.javamonkeys.api;

import com.javamonkeys.dao.user.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserService implements IUserService {

    @Inject
    IUserDao userDao;

    @Transactional
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public void register(@RequestParam("email") String email, @RequestParam("password") String password) {
       // TODO
    }

    @Transactional
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam("email") String email, @RequestParam("password") String password) {
        // TODO
        return  "" + email + " - " + password;
    }

    @Transactional
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    //@ResponseStatus(value = HttpStatus.OK)
    public void logout(User user) {
        // TODO
    }

    @RequestMapping(value = "/getUserByEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public User getUserByEmail(@RequestParam("email") String email) {
        return userDao.getUserByEmail(email);
    }

    //@Transactional
    public User getUserByToken(String token) {
        // TODO
        return null; //userDao.getUserByToken(token);
    }

    @Transactional
    public User createUser(String eMail, String password, UserAccessGroup userAccessGroup) {
        // TODO
        return null;
    }

    @Transactional
    public User createUser(String eMail, String password, UserAccessGroup userAccessGroup, Date birthDate) {
        // TODO
        return null;
    }

    @Transactional
    public void deleteUser(User user) {
        // TODO
    }

    @Transactional
    public void updateUser(User user) {
        // TODO
    }

    @Transactional
    public UserAccessGroup getUserAccessGroup(String name) {
        // TODO
        return null;
    }

    @Transactional
    public UserAccessGroup createUserAccessGroup(String name, boolean isAdmin) {
        // TODO
        return null;
    }

    @Transactional
    public void deleteUserAccessGroup(UserAccessGroup userAccessGroup) {
        // TODO
    }
}
