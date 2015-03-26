package com.javamonkeys.dao.game;

import com.javamonkeys.dao.user.User;
import java.util.Date;

public class GameDao implements IGameDao{
    @Override
    public Game getGame(int id, User white, User black) throws GameNotFoundException {

        // TODO: create method
        Game testGame = new Game(new Date(),white,black,id);
        return testGame;
    }

    @Override
    public Game newGame(Date matchDate, int id, User white, User black) {
        // TODO: create method
        Game testGame = new Game(new Date(),white,black,id);
        return testGame;
    }

    @Override
    public Game saveGame(int id, User white, User black) {
        // TODO: create method
        Game testGame = new Game(new Date(),white,black,id);
        return testGame;
    }

    @Override
    public void saveTurn(User Player, Pieces piece, String startPos, String finalPos) {
        // TODO: create method
        }
}
