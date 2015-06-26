package com.javamonkeys.dao.user;

import java.util.Date;

public interface IUserDao {

    public User getUserById(Integer id);

    public User getUserByEmail(String email);

    public User getUserByToken(String token);

    public User createUser(String email, String password,  UserAccessGroup userAccessGroup) throws UserAlreadyExistException;

    public User createUser(String email, String password, UserAccessGroup userAccessGroup, Date birthDate) throws UserAlreadyExistException;

    public void deleteUser(User user) throws UserNotFoundException;

    public void updateUser(User user) throws UserNotFoundException;

    public UserAccessGroup getUserAccessGroupById(Integer id);

    public UserAccessGroup getUserAccessGroupByName(String name);

    public UserAccessGroup createUserAccessGroup(String name, boolean isAdmin) throws UserAccessGroupAlreadyExistException;

    public void deleteUserAccessGroup(UserAccessGroup userAccessGroup) throws UserAccessGroupNotFoundException;

    public User login(String email, String password) throws IncorrectUserCredentialsException;

    public void logout(User user);

    public void register(String email, String password) throws UserAlreadyExistException;

    public String generateNewUniqueToken();
}
