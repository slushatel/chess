package com.javamonkeys.dao.user;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created by ose on 3/24/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
public class UserDaoTest {

    @Inject
    IUserDao userDao;

    @Test
    public void exampleTestForUserDao (){

        String email = "some.email@gmail.com";

        try {
            User user = userDao.getUser(email);
            assertNotNull(user);
            // TODO add other assertions
        } catch (UserNotFoundException e) {
            fail(String.format("User with email %s was not found", email));
        }

    }
}

