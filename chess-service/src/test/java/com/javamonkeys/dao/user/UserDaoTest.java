package com.javamonkeys.dao.user;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
public class UserDaoTest {

    @Inject
    IUserDao userDao;

    private String currentUserEmail = "filippov@javamonkeys.com";

    /** Create user (1 constructor)
    * User doesn't exist in the DB.
    * User must be created (not null) or expected UserAlreadyExistException exception */
    @Test
    public void testCreateUser1(){

        String email = "banana1@gmail.com";
        UserAccessGroup userAccessGroup = new UserAccessGroup("admin", true);
        userAccessGroup.setId(1);

        try {
            User user = userDao.createUser(email, "12345", userAccessGroup);
            assertNotNull("Return value (USER) can't be null!", user);
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
        UserAccessGroup userAccessGroup = new UserAccessGroup("admin", true);
        userAccessGroup.setId(1);

        try {
            User user = userDao.createUser(email, "12345", userAccessGroup, new Date());
            assertNotNull("Return value (USER) can't be null!", user);
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

        try {
            User user = userDao.getUser(currentUserEmail);
            assertNotNull("Return value (USER) can't be null!",user);
        } catch (UserNotFoundException e) {
            fail(String.format("User with email %s was not found. Check test data.", currentUserEmail));
        }
    }

    /** Get user
     * User doesn't exist in the DB.
     * Expected UserNotFoundException exception */
    @Test(expected = UserNotFoundException.class)
    public void testGetUserException() throws UserNotFoundException {
        userDao.getUser("EmptyEmail@javamonkey.com");
    }

    /** Get user
     * Null arguments passing
     * Expected UserNotFoundException exception */
    @Test(expected = UserNotFoundException.class)
    public void testGetUserTestForNullArguments() throws UserNotFoundException {
        userDao.getUser(null);
    }

    /** Delete user from DB
     * User already exists in the DB
     * User must be delete or expected UserNotFoundException exception*/
    @Test
    public void testDeleteUser(){

        try {
            User currentUser = userDao.getUser(currentUserEmail);
            userDao.deleteUser(currentUser);
        } catch (UserNotFoundException e) {
            fail(String.format("User with email %s was not found", currentUserEmail));
        }
    }

    /** Delete user from DB
     * User doesn't exist in the DB.
     * Expected UserNotFoundException exception */
    @Test(expected = UserNotFoundException.class)
    public void testDeleteUserException() throws UserNotFoundException {
        userDao.deleteUser(getUserForServiceUse("EmptyEmail@javamonkey.com"));
    }

    // service method for get entity without necessity to catch the exception
    private User getUserForServiceUse(String email){
        try {
            return userDao.getUser(email);
        }catch (UserNotFoundException e){
            return null;
        }
    }

    /** Delete user from DB
     * Null arguments passing
     * Expected UserNotFoundException exception */
    @Test(expected = UserNotFoundException.class)
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
        try {
            UserAccessGroup userAccessGroup = userDao.createUserAccessGroup(name, true);
            assertNotNull("Return value (userAccessGroup) can't be null!", userAccessGroup);
        } catch (UserAccessGroupAlreadyExistException e) {
            fail(String.format("User access group with name %s already exists. Check test data.", name));
        }

        name = "testNameGroup2";
        try {
            UserAccessGroup userAccessGroup = userDao.createUserAccessGroup(name, false);
            assertNotNull("Return value (userAccessGroup) can't be null!", userAccessGroup);
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
    @Test
    public void testCreateUserAccessGroupTestForNullArguments(){

        // name == null
        try {
            userDao.createUserAccessGroup(null, true);
            userDao.createUserAccessGroup(null, false);
        } catch (IllegalArgumentException e){
            // ok
        } catch (Throwable e){
            // other types of exceptions
            fail("Method should return IllegalArgumentException (argument \"name\" is null)");
        }
    }

    /** Get user access group from DB
     * Group already exists in the DB.
     * Group must be create (not null) or expected UserAccessGroupNotFoundException exception */
    @Test
    public void testGetUserAccessGroup(){

        String testName = "admin";

        try {
            UserAccessGroup userAccessGroup = userDao.getUserAccessGroup(testName);
            assertNotNull("Return value (userAccessGroup) can't be null!", userAccessGroup);
        } catch (UserAccessGroupNotFoundException e) {
            fail(String.format("User access group with name %s was not found. Check test data.", testName));
        }
    }

    /** Get user access group
     * Group doesn't exist in the DB.
     * Expected UserAccessGroupNotFoundException exception */
    @Test(expected = UserAccessGroupNotFoundException.class)
    public void testGetUserAccessGroupException() throws UserAccessGroupNotFoundException {
        userDao.getUserAccessGroup("IllegalNameOfGroup");
    }

    /** Get user access group
     * Null arguments passing
     * Expected UserAccessGroupNotFoundException exception */
    @Test(expected = UserAccessGroupNotFoundException.class)
    public void testGetUserAccessGroupTestForNullArguments() throws UserAccessGroupNotFoundException {
        userDao.getUserAccessGroup(null);
    }

    /** Delete user access group from DB
     * Group already exists in the DB
     * Group must be delete or expected UserAccessGroupNotFoundException exception*/
    @Test
    public void testDeleteUserAccessGroup(){

        String testName = "admin";
        try {
            UserAccessGroup currentGroup = userDao.getUserAccessGroup(testName);
            userDao.deleteUserAccessGroup(currentGroup);
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

