package com.javamonkeys.dao.game;

import com.javamonkeys.dao.user.User;

import java.util.Date;

public interface IGameDao {

    public Game getGame(int id);

    public Game createGame(User author);

    public Game updateGame(Game currentGame);

    public void saveTurn(int id, String turn);

}