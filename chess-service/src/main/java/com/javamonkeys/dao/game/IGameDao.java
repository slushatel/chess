package com.javamonkeys.dao.game;

import com.javamonkeys.dao.user.User;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public interface IGameDao {

    public Game getGame(int id);

    public Game createGame(User user, Boolean isWhite, long gameLength);

    public Game updateGame(Game game) throws GameNotFoundException;

    public void deleteGame(Game game) throws GameNotFoundException;

    public void deleteGame(int id) throws GameNotFoundException;

    public void saveTurn(int id, String turn) throws GameNotFoundException;

    public ArrayList<Game> getListGames(User author);

    public ArrayList<Game> getListGames(GameStatus status);

    public ArrayList<Turn> getGamesTurns(Game game);
}