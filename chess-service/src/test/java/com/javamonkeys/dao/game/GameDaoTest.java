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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
public class GameDaoTest {

    @Inject
    IGameDao gameDao;

    private String currentUserEmail = "sirosh@javamonkeys.com";
    private User currentUser;

    private User getUserForServiceUse(String email){

        UserDao userDao = new UserDao();

        try {
            return userDao.getUser(email);
        }catch (UserNotFoundException e){
            return null;
        }
    }

    @Test
    public void testCreateGame(){

        currentUser = getUserForServiceUse(currentUserEmail);

        Game game = gameDao.createGame(currentUser);
        assertNotNull("Return value (Game) can't be null!", game);
    }

    @Test
    public void testGetGame(){

        Game game = gameDao.getGame(1);
        assertNotNull("Return value (Game) can't be null!", game);
    }

    @Test
    public void testSaveTurn(){

        Game game = gameDao.getGame(2);
        String str = game.getMoveText();

        gameDao.saveTurn(2, "2.d3 d6");

        game = gameDao.getGame(2);

        assertEquals((str + "2.d3 d6"), game.getMoveText());
    }
}
