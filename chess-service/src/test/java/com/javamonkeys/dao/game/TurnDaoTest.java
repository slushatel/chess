package com.javamonkeys.dao.game;

import com.javamonkeys.dao.user.IUserDao;
import com.javamonkeys.dao.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
@Transactional
public class TurnDaoTest {

    @Inject
    IUserDao userDao;
    @Inject
    IGameDao gameDao;
    @Inject
    ITurnDao turnDao;

    private String currentUserEmail = "sirosh@javamonkeys.com";

    private User getUserForServiceUse(String email){

        return userDao.getUserByEmail(email);
    }

    private Game getGameForServiceUse(int id){

        return gameDao.getGame(id);
    }

    @Test
    public void testSaveTurn(){

        User currentUser = getUserForServiceUse(currentUserEmail);
        Game currentGame = getGameForServiceUse(2);

        Turn turn = new Turn(currentGame, currentUser, new Date(),Pieces.PAWN, "e2","e4","0505");
        turnDao.SaveTurn(turn);
        assertNotNull("Return value (Game) can't be null!", turn);
    }

}
