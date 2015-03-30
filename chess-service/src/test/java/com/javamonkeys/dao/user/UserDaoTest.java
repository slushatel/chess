package com.javamonkeys.dao.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.Date;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
public class UserDaoTest {

    @Inject
    IUserDao userDao;

    /** Create user (1 constructor)
    * User doesn't exist in the DB.
    * User must be created (not null) or expected UserAlreadyExistException exception */
    @Test
    public void testCreateUser1(){

        String email = "banana1@gmail.com";
        String password = "12345";

        UserAccessGroup userAccessGroup = new UserAccessGroup("admin", true);
        userAccessGroup.setId(1);

        try {
            User user = userDao.createUser(email, password, userAccessGroup);
            assertNotNull("Return value (USER) shouldn't be null!", user);

            User currentUser = userDao.getUser(email);
            assertEquals("Incorrect email for new user", email, currentUser.getEmail());
            assertEquals("Incorrect password for new user", password, currentUser.getPassword());
            assertEquals("Incorrect user access group for new user", userAccessGroup.getId(), currentUser.getUserAccessGroup().getId());
            assertEquals("Incorrect user access group for new user", userAccessGroup.getName(), currentUser.getUserAccessGroup().getName());

        } catch (UserAlreadyExistException e) {
            fail(String.format("User with email %s already exists. Check test data.", email));
        }
    }

    /** Create user (1 constructor)
     * User already exists in the DB.
     * Expected UserAlreadyExistException exception */
    @Test(expected = UserAlreadyExistException.class)
    public void testCreateUser1Exception() throws UserAlreadyExistException {

        UserAccessGroup userAccessGroup = new UserAccessGroup("admin", true);
        userAccessGroup.setId(1);

        userDao.createUser("serdyukov@javamonkeys.com", "12345", userAccessGroup);
    }

    /** Create user (1 constructor)
     * Null arguments passing
     * Expected IllegalArgumentException exception */
    @Test
    public void testCreateUser1TestForNullArguments(){

        UserAccessGroup userAccessGroup = new UserAccessGroup("admin", true);
        userAccessGroup.setId(1);

        // email == null
        try {
            userDao.createUser(null, "12345", userAccessGroup);
        } catch (IllegalArgumentException e){
            // ok
        } catch (Throwable e){
            // other types of exceptions
            fail("Method should return IllegalArgumentException (argument \"email\" is null)");
        }

        // password == null
        try {
            userDao.createUser("filippov@javamonkeys.com", null, userAccessGroup);
        } catch (IllegalArgumentException e){
            // ok
        } catch (Throwable e){
            // other types of exceptions
            fail("Method should return IllegalArgumentException (argument \"password\" is null)");
        }

        // userAccessGroup == null
        try {
            userDao.createUser("filippov@javamonkeys.com", "12345", null);
        } catch (IllegalArgumentException e){
            // ok
        } catch (Throwable e){
            // other types of exceptions
            fail("Method should return IllegalArgumentException (argument \"userAccessGroup\" is null)");
        }
    }

    /** Create user (2 constructor)
     * User doesn't exist in the DB.
     * User must be created (not null) or expected UserAlreadyExistException exception */
    @Test
    public void testCreateUser2(){

        String email = "banana2@gmail.com";
        String password = "12345";

        UserAccessGroup userAccessGroup = new UserAccessGroup("admin", true);
        userAccessGroup.setId(1);

        try {
            User user = userDao.createUser(email, password, userAccessGroup, new Date());
            assertNotNull("Return value (USER) shouldn't be null!", user);

            User currentUser = userDao.getUser(email);
            assertEquals("Incorrect email for new user", email, currentUser.getEmail());
            assertEquals("Incorrect password for new user", password, currentUser.getPassword());
            assertEquals("Incorrect user access group for new user", userAccessGroup.getId(), currentUser.getUserAccessGroup().getId());
            assertEquals("Incorrect user access group for new user", userAccessGroup.getName(), currentUser.getUserAccessGroup().getName());

        } catch (UserAlreadyExistException e) {
            fail(String.format("User with email %s already exists. Check test data.", email));
        }
    }

    /** Create user (2 constructor)
     * User already exists in the DB.
     * Expected UserAlreadyExistException exception */
    @Test(expected = UserAlreadyExistException.class)
    public void testCreateUser2Exception() throws UserAlreadyExistException {
        UserAccessGroup userAccessGroup = new UserAccessGroup("admin", true);
        userAccessGroup.setId(1);
        userDao.createUser("serdyukov@javamonkeys.com", "12345", userAccessGroup, new Date());
    }

    /** Create user (2 constructor)
     * Null arguments passing
     * Expected IllegalArgumentException exception */
    @Test
    public void testCreateUser2TestForNullArguments(){

        UserAccessGroup userAccessGroup = new UserAccessGroup("admin", true);
        userAccessGroup.setId(1);

        // email == null
        try {
            userDao.createUser(null, "12345", userAccessGroup, new Date());
        } catch (IllegalArgumentException e){
            // ok
        } catch (Throwable e){
            // other types of exceptions
            fail("Method should return IllegalArgumentException (argument \"email\" is null)");
        }

        // password == null
        try {
            userDao.createUser("filippov@javamonkeys.com", null, userAccessGroup, new Date());
        } catch (IllegalArgumentException e){
            // ok
        } catch (Throwable e) {
            // other types of exceptions
            fail("Method should return IllegalArgumentException (argument \"password\" is null)");
        }

        // userAccessGroup == null
        try {
            userDao.createUser("filippov@javamonkeys.com", "12345", null, new Date());
        } catch (IllegalArgumentException e){
            // ok
        } catch (Throwable e){
            // other types of exceptions
            fail("Method should return IllegalArgumentException (argument \"userAccessGroup\" is null)");
        }

        // birthDate == null
        try {
            userDao.createUser("filippov@javamonkeys.com", "12345", userAccessGroup, null);
        } catch (IllegalArgumentException e){
            // ok
        } catch (Throwable e){
            // other types of exceptions
            fail("Method should return IllegalArgumentException (argument \"birthDate\" is null)");
        }
    }

    /** Get user from DB
     * User already exists in the DB.
     * User must be create (not null) or expected UserNotFoundException exception */
    @Test
    public void testGetUser(){

        String email = "filippov@javamonkeys.com";

        User user = userDao.getUser(email);
        assertNotNull("Return value (USER) shouldn't be null!",user);
        assertEquals("Incorrect user", email, user.getEmail());

    }

    /** Get user
     * User doesn't exist in the DB. */
    @Test
    public void testGetUserNullIfNotFound() {
        User currentUser = userDao.getUser("EmptyEmail@javamonkey.com");
        assertNull(currentUser);
    }

    /** Get user
     * Null arguments passing */
    @Test
    public void testGetUserTestForNullArguments() {
        User currentUser = userDao.getUser(null);
        assertNull(currentUser);
    }

    /** Delete user from DB
     * User already exists in the DB
     * User must be delete or expected UserNotFoundException exception*/
    @Test
    public void testDeleteUser(){

        String email = "filippov@javamonkeys.com";

        try {
            User user = userDao.getUser(email);
            userDao.deleteUser(user);

            User currentUser = userDao.getUser(email);
            assertNull(currentUser);
        } catch (UserNotFoundException e) {
            fail(String.format("User with email %s was not found", "filippov@javamonkeys.com"));
        }
    }

    /** Delete user from DB
     * User doesn't exist in the DB.
     * Expected UserNotFoundException exception */
    @Test(expected = UserNotFoundException.class)
    public void testDeleteUserException() throws UserNotFoundException {
        userDao.deleteUser(new User("",""));
    }

    /** Delete user from DB
     * Null arguments passing
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUserTestForNullArguments() throws UserNotFoundException {
        userDao.deleteUser(null);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //  USER ACCESS GROUPS
    //////////////////////////////////////////////////////////////////////////////////////


    /** Create user access group
     * Group doesn't exist in the DB.
     * Group must be created (not null) or expected UserAccessGroupAlreadyExistException exception */
    @Test
    public void testCreateUserAccessGroup(){

        String name = "testNameGroup1";
        boolean isAdmin = true;
        try {
            UserAccessGroup userAccessGroup = userDao.createUserAccessGroup(name, isAdmin);
            assertNotNull("Return value (userAccessGroup) shouldn't be null!", userAccessGroup);

            UserAccessGroup currentGroup = userDao.getUserAccessGroup(name);
            assertEquals("Incorrect name for new user access group", name, currentGroup.getName());
            assertEquals("Incorrect IsAdmin for new user access group", isAdmin, currentGroup.getIsAdmin());

        } catch (UserAccessGroupAlreadyExistException e) {
            fail(String.format("User access group with name %s already exists. Check test data.", name));
        }

        name = "testNameGroup2";
        isAdmin = false;
        try {
            UserAccessGroup userAccessGroup = userDao.createUserAccessGroup(name, isAdmin);
            assertNotNull("Return value (userAccessGroup) shouldn't be null!", userAccessGroup);

            UserAccessGroup currentGroup = userDao.getUserAccessGroup(name);
            assertEquals("Incorrect name for new user access group", name, currentGroup.getName());
            assertEquals("Incorrect IsAdmin for new user access group", isAdmin, currentGroup.getIsAdmin());

        } catch (UserAccessGroupAlreadyExistException e) {
            fail(String.format("User access group with name %s already exists. Check test data.", name));
        }
    }

    /** Create user access group
     * Group already exists in the DB.
     * Expected UserAccessGroupAlreadyExistException exception */
    @Test(expected = UserAccessGroupAlreadyExistException.class)
    public void testCreateUserAccessGroupException() throws UserAccessGroupAlreadyExistException {
        userDao.createUserAccessGroup("admin", true);
    }

    /** Create user access group
     * Null arguments passing
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserAccessGroupTestForNullArguments1() throws UserAccessGroupAlreadyExistException{
        // name == null; isAdmin == true
        userDao.createUserAccessGroup(null, true);
    }

    /** Create user access group
     * Null arguments passing
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserAccessGroupTestForNullArguments2() throws UserAccessGroupAlreadyExistException {
        // name == null; isAdmin == false
        userDao.createUserAccessGroup(null, false);
    }

    /** Get user access group from DB
     * Group already exists in the DB.
     * Group must be create (not null) or expected UserAccessGroupNotFoundException exception */
    @Test
    public void testGetUserAccessGroup(){

        String testName = "admin";

        UserAccessGroup userAccessGroup = userDao.getUserAccessGroup(testName);
        assertNotNull("Return value (userAccessGroup) shouldn't be null!", userAccessGroup);
        assertEquals("Incorrect user access group", testName, userAccessGroup.getName());
    }

    /** Get user access group
     * Group doesn't exist in the DB. */
    @Test
    public void testGetUserAccessGroupNullIfNotFound() {
        UserAccessGroup currentGroup = userDao.getUserAccessGroup("IllegalNameOfGroup");
        assertNull(currentGroup);
    }

    /** Get user access group
     * Null arguments passing */
    @Test
    public void testGetUserAccessGroupTestForNullArguments() {
        UserAccessGroup currentGroup = userDao.getUserAccessGroup(null);
        assertNull(currentGroup);
    }

    /** Delete user access group from DB
     * Group already exists in the DB
     * Group must be delete or expected UserAccessGroupNotFoundException exception*/
    @Test
    public void testDeleteUserAccessGroup(){

        String testName = "admin";
        try {
            UserAccessGroup group = userDao.getUserAccessGroup(testName);
            userDao.deleteUserAccessGroup(group);

            UserAccessGroup currentGroup = userDao.getUserAccessGroup(testName);
            assertNull(currentGroup);
        } catch (UserAccessGroupNotFoundException e) {
            fail(String.format("User access group with name %s was not found", testName));
        }
    }

    /** Delete user access group from DB
     * Group doesn't exist in the DB.
     * Expected UserAccessGroupNotFoundException exception */
    @Test(expected = UserAccessGroupNotFoundException.class)
    public void testDeleteUserAccessGroupException() throws UserAccessGroupNotFoundException {
        userDao.deleteUserAccessGroup(new UserAccessGroup("IllegalNameOfGroup", false));
    }

    /** Delete user access group from DB
     * Null arguments passing
     * Expected UserAccessGroupNotFoundException exception */
    @Test(expected = UserAccessGroupNotFoundException.class)
    public void testDeleteUserAccessGroupTestForNullArguments() throws UserAccessGroupNotFoundException {
        userDao.deleteUserAccessGroup(null);
    }
}

