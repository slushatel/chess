package com.javamonkeys.dao.user;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Class which implemented IUserDao interface.
 * Use this class for manage next type of user data:
 * - class "User" (create/get/delete)
 * - class "UserAccessGroup" (create/get/delete)
 */
@Repository
public class UserDao implements IUserDao {

    @Autowired
    private SessionFactory hibernateSessionFactory;

    private Session getSession() {
        return hibernateSessionFactory.getCurrentSession();
    }

    private void save(Object entity) {
        getSession().save(entity);
    }

    private void delete(Object entity) {
        getSession().delete(entity);
    }

    private void merge(Object entity) {
        getSession().merge(entity);
    }

    private void persist(Object entity) {
        getSession().persist(entity);
    }

    /**
     * Find user by id.
     *
     * @param id user id
     * @return user
     */
    public User getUserById(Integer id) {

        Query query = getSession().createQuery("from User where id = :id");
        query.setParameter("id", id);

        List result = query.list();

        if (result.isEmpty()) {
            return null;
        } else {
            return (User)result.get(0);
        }
    }

    /**
     * Find user by email.
     *
     * @param email user email
     * @return user
     */
    public User getUserByEmail(String email) {

        if (email==null)
            return null;

        Query query = getSession().createQuery("from User where LOWER(email) = :email");
        query.setParameter("email", email.toLowerCase());

        List result = query.list();

        if (result.isEmpty()) {
            return null;
        } else {
            return (User)result.get(0);
        }
    }

    /**
     * Find user by token.
     *
     * @param token user token
     * @return user
     */
    public User getUserByToken(String token) {

        if (token == null)
            return null;

        Query query = getSession().createQuery("from User where token = :token");
        query.setParameter("token", token);

        List result = query.list();

        if (result.isEmpty()) {
            return null;
        } else {
            return (User)result.get(0);
        }
    }

    /**
     * Create new user.
     *
     * @param email           new user email
     * @param password        new user password
     * @param userAccessGroup new user access group
     * @return new user
     * @throws UserAlreadyExistException if user which this email already exists
     *         IllegalArgumentException if incorrect arguments passed
     */
    public User createUser(String email, String password, UserAccessGroup userAccessGroup) throws UserAlreadyExistException,
            IllegalArgumentException {

        if (email == null)
            throw new IllegalArgumentException("email: " + email + " is not valid");

        if (password == null)
            throw new IllegalArgumentException("password: " + password + " is not valid");

        if (userAccessGroup == null)
            throw new IllegalArgumentException("userAccessGroup: " + userAccessGroup + " is not valid");

        User currentUser = getUserByEmail(email);
        if (currentUser!=null)
            throw new UserAlreadyExistException("User with email: " + email + " is already exist.");

        User newUser = new User(email, password, userAccessGroup);
        save(newUser);

        return newUser;
    }

    /**
     * Create new user.
     *
     * @param email           new user email
     * @param password        new user password
     * @param userAccessGroup new user access group
     * @param birthDate       new user birth date
     * @return new User
     * @throws UserAlreadyExistException if user which this email already exist
     *         IllegalArgumentException if incorrect arguments passed
     */
    public User createUser(String email, String password, UserAccessGroup userAccessGroup, Date birthDate) throws UserAlreadyExistException,
            IllegalArgumentException {

        if (email == null)
            throw new IllegalArgumentException("argument \"email\": value " + email + " is not valid");

        if (password == null)
            throw new IllegalArgumentException("argument \"password\": value " + password + " is not valid");

        if (userAccessGroup == null)
            throw new IllegalArgumentException("argument \"userAccessGroup\": value " + userAccessGroup + " is not valid");

        if (birthDate == null)
            throw new IllegalArgumentException("argument \"birthDate\": value " + birthDate + " is not valid");

        User currentUser = getUserByEmail(email);
        if (currentUser!=null)
            throw new UserAlreadyExistException("User with email: " + email + " is already exist.");

        User newUser = new User(email, password, birthDate, userAccessGroup);
        save(newUser);

        return newUser;
    }

    /**
     * Delete user.
     *
     * @param user user which need to delete
     * @throws UserNotFoundException if this user doesn't exist in DataBase
     *         IllegalArgumentException if incorrect arguments passed
     */
    public void deleteUser(User user) throws UserNotFoundException, IllegalArgumentException {

        if (user==null)
            throw new IllegalArgumentException("argument \"user\": value \" + user + \" is not valid");

        User currentUser = getUserByEmail(user.getEmail());
        if (currentUser == null)
            throw new UserNotFoundException("User " + user + " not found");

        delete(currentUser);
    }

    /**
     * Update user data
     *
     * @param user user which need to update
     * @throws UserNotFoundException if this user doesn't exist in DataBase
     *         IllegalArgumentException if incorrect arguments passed
     */
    public void updateUser(User user) throws UserNotFoundException, IllegalArgumentException {

        if (user == null)
            throw new IllegalArgumentException("argument \"user\": value " + user + " is not valid");

        User currentUser = getUserByEmail(user.getEmail());
        if (currentUser == null)
            throw new UserNotFoundException("User " + user + " not found");

        persist(currentUser);
    }

    /**
     * Find user access group by id.
     *
     * @param id group id
     * @return user access group
     */
    public UserAccessGroup getUserAccessGroupById(Integer id) {

        if (id == null)
            return null;

        Query query = getSession().createQuery("from UserAccessGroup where id = :id");
        query.setParameter("id", id);

        List result = query.list();

        if (result.isEmpty()) {
            return null;
        } else {
            return (UserAccessGroup)result.get(0);
        }

    }

    /**
     * Find user access group by name.
     *
     * @param name name of group
     * @return user access group
     */
    public UserAccessGroup getUserAccessGroupByName(String name) {

        if (name==null)
            return null;

        Query query = getSession().createQuery("from UserAccessGroup where name = :name");
        query.setParameter("name", name);

        List result = query.list();

        if (result.isEmpty()) {
            return null;
        } else {
            return (UserAccessGroup)result.get(0);
        }

    }

    /**
     * Create new user access group.
     *
     * @param name    user access group name
     * @param isAdmin if admin - true, else - false
     * @return new user access group
     * @throws UserAccessGroupAlreadyExistException if user access group which this name already exists
     *         IllegalArgumentException if incorrect arguments passed
     */
    public UserAccessGroup createUserAccessGroup(String name, boolean isAdmin) throws UserAccessGroupAlreadyExistException,
            IllegalArgumentException {

        if (name==null)
            throw new IllegalArgumentException("argument \"name\": value " + name + " is not valid");

        UserAccessGroup currentGroup = getUserAccessGroupByName(name);
        if (currentGroup!=null)
            throw new UserAccessGroupAlreadyExistException("User access group with name: " + name + " is already exist.");

        UserAccessGroup newGroup = new UserAccessGroup(name, isAdmin);
        save(newGroup);

        return newGroup;
    }

    /**
     * Delete user access group.
     *
     * @param userAccessGroup user access group which need to delete
     * @throws UserAccessGroupNotFoundException if this user access group doesn't exist in DataBase
     *         IllegalArgumentException if incorrect arguments passed
     */
    public void deleteUserAccessGroup(UserAccessGroup userAccessGroup) throws UserAccessGroupNotFoundException,
            IllegalArgumentException {

        if (userAccessGroup==null)
            throw new UserAccessGroupNotFoundException("User access group: " + userAccessGroup + " was not found");

        UserAccessGroup currentGroup = getUserAccessGroupById(userAccessGroup.getId());
        if (currentGroup==null)
            throw new UserAccessGroupNotFoundException("User access group " + userAccessGroup + " not found");

        delete(currentGroup);
    }

    /**
     * Login operation
     * @param email user email
     * @param password user password
     * @throws IncorrectUserCredentialsException if passed incorrect email or password data
     */
    public User login(String email, String password) throws IncorrectUserCredentialsException {

        if (email == null || password == null)
            throw new IncorrectUserCredentialsException();

        User currentUser = getUserByEmail(email);

        if(currentUser==null || !currentUser.getPassword().equals(password))
            throw new IncorrectUserCredentialsException();

        String newToken = generateNewUniqueToken();
        currentUser.setToken(newToken);
        persist(currentUser);

        return currentUser;
    }

    /**
     * Logout operation
     * @param user current user
     * @throws UserNotFoundException if user doesn't exist in DataBase
     */
    public void logout(User user) {

        if (user == null)
            return;

        user.setToken(null);
        persist(user);
    }

    /**
     * Register operation
     * @param email user email
     * @param password user password
     * @throws UserAlreadyExistException if user doesn't exist in DataBase
     */
    public void register(String email, String password) throws UserAlreadyExistException {

        User currentUser = getUserByEmail(email);

        if(currentUser!=null)
            throw new UserAlreadyExistException("User with email: " + email + " already exists");

        User newUser = new User(email, password, getUserAccessGroupByName("admin"));
        save(newUser);
    }

    /**
     * Generate new unique String token
     * @return token
     */
    public String generateNewUniqueToken() {
        return UUID.randomUUID().toString();
    }
}
