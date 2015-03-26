package com.javamonkeys.dao.user;

import org.junit.Before;
import org.junit.BeforeClass;
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
public class TestUserDao {

    @Inject
    IUserDao userDao;

    private String currentUserEmail = "filippov@javamonkeys.com";

    @Test
    public void testCreateUser1(){

        String email = "banana1@gmail.com";
        UserAccessGroup userAccessGroup = null;

        try {
            userAccessGroup = userDao.getUserAccessGroup("admin");
        }catch (UserAccessGroupNotFoundException e){
            fail(String.format("Incorrect test data. User access group with name %s was not found", "admin"));
        }

        try {
            User user = userDao.createUser(email, "12345", userAccessGroup);
            assertNotNull(user);
        } catch (UserAlreadyExistException e) {
            fail(String.format("User with email %s is already exist", email));
        }
    }

    @Test
    public void testCreateUser2(){

        String email = "banana2@gmail.com";
        UserAccessGroup userAccessGroup = null;

        try {
            userAccessGroup = userDao.getUserAccessGroup("admin");
        }catch (UserAccessGroupNotFoundException e){
            fail(String.format("Incorrect test data. User access group with name %s was not found", "admin"));
        }

        try {
            User user = userDao.createUser(email, "12345", userAccessGroup, new Date());
            assertNotNull(user);
        } catch (UserAlreadyExistException e) {
            fail(String.format("User with email %s is already exist", email));
        }
    }

    @Test
    public void testGetUser(){

        try {
            User user = userDao.getUser(currentUserEmail);
            assertNotNull(user);
        } catch (UserNotFoundException e) {
            fail(String.format("User with email %s was not found", currentUserEmail));
        }
    }

    @Test
    public void testDeleteUser(){

        try {
            User currentUser = userDao.getUser(currentUserEmail);
            userDao.deleteUser(currentUser);
        } catch (UserNotFoundException e) {
            fail(String.format("User with email %s was not found", currentUserEmail));
        }
    }

}

