package com.javamonkeys.dao.game;

import com.javamonkeys.dao.user.*;
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
public class GameDaoTest {

    @Inject
    IGameDao gameDao;

    private String currentUserEmail = "sirosh@javamonkeys.com";

    @Test
    public void testCreateGame(){

        User currentUser;
        UserDao userDao = new UserDao();

        try {
            currentUser = userDao.getUser(currentUserEmail);
        }catch (UserNotFoundException e){
            return;
        }

        Game game = gameDao.createGame(currentUser);
        assertNotNull("Return value (Game) can't be null!", game);
    }

}
