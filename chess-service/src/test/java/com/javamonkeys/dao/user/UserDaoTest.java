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

    //////////////////////////////////////////////////////////////////////////////////////
    //  USER TESTS
    //////////////////////////////////////////////////////////////////////////////////////

    /** Create user (1 constructor)
    * User doesn't exist in DB.
    * User should be created (not null) or expected UserAlreadyExistException exception */
    @Test
    public void testCreateUser1(){

        String email = "banana1@gmail.com";
        String password = "12345";

        UserAccessGroup userAccessGroup = new UserAccessGroup("admin", true);
        userAccessGroup.setId(1);

        try {
            User user = userDao.createUser(email, password, userAccessGroup);
            assertNotNull("Return value (USER) shouldn't be null!", user);

            User currentUser = userDao.getUserByEmail(email);
            assertEquals("Incorrect email for new user", email, currentUser.getEmail());
            assertEquals("Incorrect password for new user", password, currentUser.getPassword());
            assertEquals("Incorrect user access group for new user", userAccessGroup.getId(), currentUser.getUserAccessGroup().getId());
            assertEquals("Incorrect user access group for new user", userAccessGroup.getName(), currentUser.getUserAccessGroup().getName());

        } catch (UserAlreadyExistException e) {
            fail(String.format("User with email %s already exists. Check test data.", email));
        }
    }

    /** Create user (1 constructor)
     * User already exists in DB.
     * Expected UserAlreadyExistException exception */
    @Test(expected = UserAlreadyExistException.class)
    public void testCreateUser1Exception() throws UserAlreadyExistException {

        UserAccessGroup userAccessGroup = new UserAccessGroup("admin", true);
        userAccessGroup.setId(1);

        userDao.createUser("serdyukov@javamonkeys.com", "12345", userAccessGroup);
    }

    /** Create user (1 constructor)
     * Null arguments passed
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
     * User doesn't exist in DB.
     * User should be created (not null) or expected UserAlreadyExistException exception */
    @Test
    public void testCreateUser2(){

        String email = "banana2@gmail.com";
        String password = "12345";

        UserAccessGroup userAccessGroup = new UserAccessGroup("admin", true);
        userAccessGroup.setId(1);

        try {
            User user = userDao.createUser(email, password, userAccessGroup, new Date());
            assertNotNull("Return value (USER) shouldn't be null!", user);

            User currentUser = userDao.getUserByEmail(email);
            assertEquals("Incorrect email for new user", email, currentUser.getEmail());
            assertEquals("Incorrect password for new user", password, currentUser.getPassword());
            assertEquals("Incorrect user access group for new user", userAccessGroup.getId(), currentUser.getUserAccessGroup().getId());
            assertEquals("Incorrect user access group for new user", userAccessGroup.getName(), currentUser.getUserAccessGroup().getName());

        } catch (UserAlreadyExistException e) {
            fail(String.format("User with email %s already exists. Check test data.", email));
        }
    }

    /** Create user (2 constructor)
     * User already exists in DB.
     * Expected UserAlreadyExistException exception */
    @Test(expected = UserAlreadyExistException.class)
    public void testCreateUser2Exception() throws UserAlreadyExistException {
        UserAccessGroup userAccessGroup = new UserAccessGroup("admin", true);
        userAccessGroup.setId(1);
        userDao.createUser("serdyukov@javamonkeys.com", "12345", userAccessGroup, new Date());
    }

    /** Create user (2 constructor)
     * Null arguments passed
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

    /** Get user by Email from DB
     * User already exists in DB.
     * User should be get (not null) or expected UserNotFoundException exception */
    @Test
    public void testGetUserByEmail(){

        String email = "filippov@javamonkeys.com";
        String password = "12345";

        User user = userDao.getUserByEmail(email);
        assertNotNull("Return value (USER) shouldn't be null!",user);
        assertEquals("Incorrect user", email, user.getEmail());
        assertEquals("Incorrect user", password, user.getPassword());

    }

    /** Get user by Email
     * User doesn't exist in DB. */
    @Test
    public void testGetUserByEmailNullIfNotFound() {
        User currentUser = userDao.getUserByEmail("EmptyEmail@javamonkey.com");
        assertNull(currentUser);
    }

    /** Get user ny Email
     * Null arguments passed */
    @Test
    public void testGetUserByEmailTestForNullArguments() {
        User currentUser = userDao.getUserByEmail(null);
        assertNull(currentUser);
    }

    /** Get user by token from DB
     * User already exists in DB.
     * User should be get (not null) or expected UserNotFoundException exception */
    @Test
    public void testGetUserByToken(){

        String token = "54947df8-0e9e-4471-a2f9-9af509fb5889";
        String email = "sirosh@javamonkeys.com";

        User user = userDao.getUserByToken(token);
        assertNotNull("Return value (USER) shouldn't be null!",user);
        assertEquals("Incorrect user", email, user.getEmail());
        assertEquals("Incorrect user", token, user.getToken());
    }

    /** Get user by token
     * User doesn't exist in DB. */
    @Test
    public void testGetUserByTokenNullIfNotFound() {
        User currentUser = userDao.getUserByToken("incorrect-token");
        assertNull(currentUser);
    }

    /** Get user by token
     * Null arguments passed */
    @Test
    public void testGetUserByTokenTestForNullArguments() {
        User currentUser = userDao.getUserByToken(null);
        assertNull(currentUser);
    }

    /** Delete user from DB
     * User already exists in DB
     * User should be delete or expected UserNotFoundException exception*/
    @Test
    public void testDeleteUser(){

        String email = "DeleteUser@javamonkeys.com";

        try {
            User user = userDao.getUserByEmail(email);
            userDao.deleteUser(user);

            User currentUser = userDao.getUserByEmail(email);
            assertNull(currentUser);
        } catch (UserNotFoundException e) {
            fail(String.format("User with email %s was not found", email));
        }
    }

    /** Delete user from DB
     * User doesn't exist in DB.
     * Expected UserNotFoundException exception */
    @Test(expected = UserNotFoundException.class)
    public void testDeleteUserException() throws UserNotFoundException {
        userDao.deleteUser(new User("",""));
    }

    /** Delete user from DB
     * Null arguments passed
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUserTestForNullArguments() throws UserNotFoundException {
        userDao.deleteUser(null);
    }

    /** Update user data
     * User already exists in DB
     * User should be updated or expected UserNotFoundException exception*/
    @Test
    public void testUpdateUser(){

        String email = "UpdateUser@javamonkeys.com";

        try {
            User user = userDao.getUserByEmail(email);

            //String newEmail = "UpdateUser2@javamonkeys.com";
            String newPassword = "111";
            Date newBirthDate = new Date();
            String newToken = "new token";
            UserAccessGroup newGroup = userDao.getUserAccessGroup("user");

            //assertNotEquals(newEmail, user.getEmail());
            assertNotEquals(newPassword, user.getPassword());
            assertNotEquals(newBirthDate, user.getBirthDate());
            assertNotEquals(newToken, user.getToken());
            assertNotEquals(newGroup, user.getUserAccessGroup());

            //user.setEmail(newEmail);
            user.setPassword(newPassword);
            user.setBirthDate(newBirthDate);
            user.setToken(newToken);
            user.setUserAccessGroup(newGroup);

            userDao.updateUser(user);

            User currentUser = userDao.getUserByEmail(email);
            assertNotNull(currentUser);

            //assertEquals(newEmail, user.getEmail());
            assertEquals(newPassword, user.getPassword());
            assertEquals(newBirthDate, user.getBirthDate());
            assertEquals(newToken, user.getToken());
            assertEquals(newGroup, user.getUserAccessGroup());

        } catch (UserNotFoundException e) {
            fail(String.format("User with email %s was not found", email));
        }
    }

    /** Update user
     * User doesn't exist in DB.
     * Expected UserNotFoundException exception */
    @Test(expected = UserNotFoundException.class)
    public void testUpdateUserException() throws UserNotFoundException {
        userDao.updateUser(new User("", ""));
    }

    /** Update user
     * Null arguments passed
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserTestForNullArguments() throws UserNotFoundException {
        userDao.updateUser(null);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //  USER ACCESS GROUPS TESTS
    //////////////////////////////////////////////////////////////////////////////////////


    /** Create user access group
     * Group doesn't exist in DB.
     * Group should be created (not null) or expected UserAccessGroupAlreadyExistException exception */
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
     * Group already exists in DB.
     * Expected UserAccessGroupAlreadyExistException exception */
    @Test(expected = UserAccessGroupAlreadyExistException.class)
    public void testCreateUserAccessGroupException() throws UserAccessGroupAlreadyExistException {
        userDao.createUserAccessGroup("admin", true);
    }

    /** Create user access group
     * Null arguments passed
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserAccessGroupTestForNullArguments1() throws UserAccessGroupAlreadyExistException{
        // name == null; isAdmin == true
        userDao.createUserAccessGroup(null, true);
    }

    /** Create user access group
     * Null arguments passed
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserAccessGroupTestForNullArguments2() throws UserAccessGroupAlreadyExistException {
        // name == null; isAdmin == false
        userDao.createUserAccessGroup(null, false);
    }

    /** Get user access group from DB
     * Group already exists in DB.
     * Group should be create (not null) or expected UserAccessGroupNotFoundException exception */
    @Test
    public void testGetUserAccessGroup(){

        String testName = "admin";

        UserAccessGroup userAccessGroup = userDao.getUserAccessGroup(testName);
        assertNotNull("Return value (userAccessGroup) shouldn't be null!", userAccessGroup);
        assertEquals("Incorrect user access group", testName, userAccessGroup.getName());
    }

    /** Get user access group
     * Group doesn't exist in DB. */
    @Test
    public void testGetUserAccessGroupNullIfNotFound() {
        UserAccessGroup currentGroup = userDao.getUserAccessGroup("IllegalNameOfGroup");
        assertNull(currentGroup);
    }

    /** Get user access group
     * Null arguments passed */
    @Test
    public void testGetUserAccessGroupTestForNullArguments() {
        UserAccessGroup currentGroup = userDao.getUserAccessGroup(null);
        assertNull(currentGroup);
    }

    /** Delete user access group from DB
     * Group already exists in DB
     * Group should be delete or expected UserAccessGroupNotFoundException exception*/
    @Test
    public void testDeleteUserAccessGroup(){

        String testName = "groupForDelete";
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
     * Group doesn't exist in DB.
     * Expected UserAccessGroupNotFoundException exception */
    @Test(expected = UserAccessGroupNotFoundException.class)
    public void testDeleteUserAccessGroupException() throws UserAccessGroupNotFoundException {
        userDao.deleteUserAccessGroup(new UserAccessGroup("IllegalNameOfGroup", false));
    }

    /** Delete user access group from DB
     * Null arguments passed
     * Expected UserAccessGroupNotFoundException exception */
    @Test(expected = UserAccessGroupNotFoundException.class)
    public void testDeleteUserAccessGroupTestForNullArguments() throws UserAccessGroupNotFoundException {
        userDao.deleteUserAccessGroup(null);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //  REGISTER / LOGIN / LOGOUT TESTS
    //////////////////////////////////////////////////////////////////////////////////////

    /** Login operation
     * User already exists in DB.
     * Token should be created (not null) */
    @Test
    public void testLogin() {

        String email = "serdyukov@javamonkeys.com";
        String password = "12345";

        try {

            User currentUserByEmail = userDao.getUserByEmail(email);
            if(currentUserByEmail==null)
                fail("User with email: " + email + " was not found. Check test data.");

            String oldToken = currentUserByEmail.getToken();
            String newToken = userDao.login(email, password);

            assertNotNull(newToken);
            assertNotEquals(oldToken, newToken);

            User currentUserByToken = userDao.getUserByToken(newToken);
            assertEquals(newToken, currentUserByToken.getToken());

        } catch (IncorrectUserCredentials e) {
            fail("User with email " + email + " and password " + password + " was not found");
        }

    }

    /** Login operation (incorrect email)
     * User doesn't exist in DB.
     * Expected IncorrectUserCredentials exception */
    @Test(expected = IncorrectUserCredentials.class)
    public void testLoginExceptionIncorrectEmail() throws IncorrectUserCredentials {
        userDao.login("empty-incorrect-email", "12345");
    }

    /** Login operation (incorrect password)
     * User already exists in DB, password - incorrect.
     * Expected IncorrectUserCredentials exception */
    @Test(expected = IncorrectUserCredentials.class)
    public void testLoginExceptionIncorrectPassword() throws IncorrectUserCredentials {
        userDao.login("serdyukov@javamonkeys.com", "incorrect password");
    }

    /** Login operation (null email)
     * User doesn't exist in DB.
     * Expected IncorrectUserCredentials exception */
    @Test(expected = IncorrectUserCredentials.class)
    public void testLoginTestForNullEmail() throws IncorrectUserCredentials {
        userDao.login(null, "12345");
    }

    /** Login operation (null password)
     * User already exists in DB.
     * Expected IncorrectUserCredentials exception */
    @Test(expected = IncorrectUserCredentials.class)
    public void testLoginTestForNullPassword() throws IncorrectUserCredentials {
        userDao.login("serdyukov@javamonkeys.com", null);
    }

    /** Logout operation
     * User already exists in DB.
     * Token should be deleted from user */
    @Test
    public void testLogout() {

        String email = "serdyukov@javamonkeys.com";

        User currentUser = userDao.getUserByEmail(email);
        if (currentUser == null)
            fail("User with email " + email + " was not found. Check test data");

        String currentToken = currentUser.getToken();
        assertNotNull("Current token should be not null. Check test data", currentToken);

        try {
            userDao.logout(currentUser);

            currentUser = userDao.getUserByEmail(email);
            assertNull(currentUser.getToken());

        } catch (UserNotFoundException e){
            fail("User with email " + email + " was not found. Check test data");
        }
    }

    /** Logout operation (incorrect user)
     * User doesn't exist in DB.
     * Expected UserNotFoundException exception */
    @Test(expected = UserNotFoundException.class)
    public void testLogoutException() throws UserNotFoundException {
        userDao.logout(new User("test", "test"));
    }

    /** Logout operation (null user)
     * User doesn't exist in DB.
     * Expected UserNotFoundException exception */
    @Test(expected = UserNotFoundException.class)
    public void testLogoutTestForNullArguments() throws UserNotFoundException {
        userDao.logout(null);
    }
}

