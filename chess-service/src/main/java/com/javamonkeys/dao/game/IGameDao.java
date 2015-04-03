package com.javamonkeys.dao.game;

import com.javamonkeys.dao.user.User;

import java.util.Date;

public interface IGameDao {

    public Game getGame(int id);

    public Game createGame(User author);

    public Game updateGame(Game game) throws GameNotFoundException;

    public void deleteGame(Game game) throws GameNotFoundException;

    public void deleteGame(int id) throws GameNotFoundException;

    public void saveTurn(int id, String turn) throws GameNotFoundException;

}