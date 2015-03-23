package com.javamonkeys.dao.user;

import java.util.Date;

public interface IUserDao {

    public User getUser(String eMail) throws UserNotFoundException;

    public User createUser(String eMail, String password,  UserAccessGroup userAccessGroup) throws UserAlreadyExistException;

    public User createUser(String eMail, String password, UserAccessGroup userAccessGroup, Date birthDate) throws UserAlreadyExistException;

    public void deleteUser(User user) throws UserNotFoundException;

    public UserAccessGroup getUserAccessGroup(String name) throws UserAccessGroupNotFoundException;

    public UserAccessGroup createUserAccessGroup(String name, boolean isAdmin) throws UserAccessGroupAlreadyExistException;

    public void deleteUserAccessGroup(UserAccessGroup userAccessGroup) throws UserAccessGroupNotFoundException;
}
