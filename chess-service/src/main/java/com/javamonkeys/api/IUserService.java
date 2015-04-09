package com.javamonkeys.api;

import com.javamonkeys.dao.user.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface IUserService {

    public void register(String email, String password) throws UserAlreadyExistException;

    public String login(String email, String password);

    public void logout(User user);

    public User getUserByEmail(String email);

    public User getUserByToken(String token);

    public User createUser(String eMail, String password,  UserAccessGroup userAccessGroup);

    public User createUser(String eMail, String password, UserAccessGroup userAccessGroup, Date birthDate);

    public void deleteUser(User user);

    public void updateUser(User user);

    public UserAccessGroup getUserAccessGroup(String name);

    public UserAccessGroup createUserAccessGroup(String name, boolean isAdmin);

    public void deleteUserAccessGroup(UserAccessGroup userAccessGroup);

}
