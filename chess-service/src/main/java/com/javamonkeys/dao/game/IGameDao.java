package com.javamonkeys.dao.game;

import com.javamonkeys.dao.user.User;

import java.util.Date;

public interface IGameDao {

    public Game getGame(int id, User white, User black) throws GameNotFoundException;

    public Game newGame(Date matchDate, int id, User white, User black);

    public Game saveGame(int id, User white, User black);

    public void saveTurn(User Player, Pieces piece, String startPos, String finalPos);

}