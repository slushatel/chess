package com.javamonkeys.dao.game;

public interface ITurnDao {

    public void saveTurn(Turn turn);

    public Turn getLastTurn(Game game);

}
