package com.javamonkeys.dao.user;

import java.util.Date;

/**
 * Class which implemented IUserDao interface.
 * Use this class for manage next type of user data:
 *   - class "User" (create/get/delete)
 *   - class "UserAccessGroup" (create/get/delete)
 */
public class UserDao implements IUserDao {

    /**
     * Find user by email.
     * @param  email user email
     * @return user
     * @throws UserNotFoundException if user doesn't exist in DataBase
     */
    public User getUser(String email) throws UserNotFoundException {

        // TODO: create method
        UserAccessGroup testGroup = new UserAccessGroup("admin", true);
        testGroup.setId(1);

        User testUser = new User(email, "12345", new Date(), testGroup);
        testUser.setId(1);

        return testUser;
    }

    /**
     * Create new user.
     * @param email new user email
     * @param password new user password
     * @param userAccessGroup new user access group
     * @return new user
     * @throws UserAlreadyExistException if user which this email already exist
     */
    public User createUser(String email, String password, UserAccessGroup userAccessGroup) throws UserAlreadyExistException {

        // TODO: create method
        User testUser = new User(email, password, userAccessGroup);
        testUser.setId(1);

        return testUser;
    }

    /**
     * Create new user.
     * @param email new user email
     * @param password new user password
     * @param userAccessGroup new user access group
     * @param birthDate new user birth date
     * @return new User
     * @throws UserAlreadyExistException if user which this email already exist
     */
    public User createUser(String email, String password, UserAccessGroup userAccessGroup, Date birthDate) throws UserAlreadyExistException {

        // TODO: create method
        User testUser = new User(email, password, birthDate, userAccessGroup);
        testUser.setId(1);

        return testUser;
    }

    /**
     * Delete user.
     * @param user user which need to delete
     * @throws UserNotFoundException if this user doesn't exist in DataBase
     */
    public  void deleteUser(User user) throws UserNotFoundException {
        // TODO: create method
    }

    /**
     * Find user access group by name.
     * @param name name of group
     * @return user access group
     * @throws UserAccessGroupNotFoundException if this user access group doesn't exist in DataBase
     */
    public UserAccessGroup getUserAccessGroup(String name) throws UserAccessGroupNotFoundException {

        // TODO: create method
        UserAccessGroup testGroup = new UserAccessGroup(name, true);
        testGroup.setId(1);

        return testGroup;

    }

    /**
     * Create new user access group.
     * @param name user access group name
     * @param isAdmin if admin - true, else - false
     * @return new user access group
     * @throws UserAccessGroupAlreadyExistException if user access group which this name already exist
     */
    public UserAccessGroup createUserAccessGroup(String name, boolean isAdmin) throws UserAccessGroupAlreadyExistException{

        // TODO: create method
        UserAccessGroup testGroup = new UserAccessGroup(name, isAdmin);
        testGroup.setId(1);

        return testGroup;

    }

    /**
     * Delete user access group.
     * @param userAccessGroup user access group which need to delete
     * @throws UserAccessGroupNotFoundException if this user access group doesn't exist in DataBase
     */
    public void deleteUserAccessGroup(UserAccessGroup userAccessGroup) throws UserAccessGroupNotFoundException{
        // TODO: create method
    }

}
