package com.javamonkeys.dao.game;

import com.javamonkeys.dao.user.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
@Transactional
public class GameDaoTest {

    @Inject
    IGameDao gameDao;
    @Inject
    IUserDao userDao;

    private String currentUserEmail = "sirosh@javamonkeys.com";
    private User currentUser;

    private User getUserForServiceUse(String email){

        return userDao.getUserByEmail(email);
    }

    @Test
    public void testCreateGame(){

        currentUser = getUserForServiceUse(currentUserEmail);

        Game game = gameDao.createGame(currentUser, true, 500);
        assertNotNull("Return value (Game) can't be null!", game);
    }

    @Test
    public void testGetGame(){

        Game game = gameDao.getGame(1);
        assertNotNull("Return value (Game) can't be null!", game);
    }

    @Test
    public void testGetListGames1(){

        currentUser = getUserForServiceUse(currentUserEmail);

        ArrayList<Game> listGames = gameDao.getListGames(currentUser);
        assertNotNull("Return value (Game) can't be null!", listGames);
    }

    @Test
    public void testGetListGames2(){

        ArrayList<Game> listGames = gameDao.getListGames(GameStatus.IN_PROGRESS);
        assertNotNull("Return value (Game) can't be null!", listGames);
    }

    @Test
    @Ignore
    public void testSaveTurn(){

        Game game = gameDao.getGame(2);
        String str = game.getMoveText();

        try {
            gameDao.saveTurn(2, "2.d3 d6");
        } catch (GameNotFoundException e) {
            e.printStackTrace();
        }

        game = gameDao.getGame(2);

        assertEquals((str + "2.d3 d6"), game.getMoveText());
    }
}
