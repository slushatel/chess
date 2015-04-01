package com.javamonkeys.dao.user;

import java.util.Date;

public interface IUserDao {

    public User getUserByEmail(String eMail);

    public User getUserByToken(String token);

    public User createUser(String eMail, String password,  UserAccessGroup userAccessGroup) throws UserAlreadyExistException;

    public User createUser(String eMail, String password, UserAccessGroup userAccessGroup, Date birthDate) throws UserAlreadyExistException;

    public void deleteUser(User user) throws UserNotFoundException;

    public void updateUser(User user) throws UserNotFoundException;

    public UserAccessGroup getUserAccessGroup(String name);

    public UserAccessGroup createUserAccessGroup(String name, boolean isAdmin) throws UserAccessGroupAlreadyExistException;

    public void deleteUserAccessGroup(UserAccessGroup userAccessGroup) throws UserAccessGroupNotFoundException;

    public String login(String email, String password) throws IncorrectUserCredentials;

    public void logout(User user) throws UserNotFoundException;

    public void register(String email, String password) throws UserAlreadyExistException;

    public String generateNewUniqueToken();
}
