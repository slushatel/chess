package com.javamonkeys.api;

import com.javamonkeys.dao.user.*;

import java.util.Date;

public interface IUserService {

    public void register(String authorization) throws UserAlreadyExistException, IncorrectUserCredentialsException;

    public String login(String authorization) throws IncorrectUserCredentialsException;

    public void logout(String token);

    public User getUser(int id);

    public void deleteUser(int id) throws UserNotFoundException;

    public void updateUser(int id, User sourceUser) throws UserNotFoundException;

}
