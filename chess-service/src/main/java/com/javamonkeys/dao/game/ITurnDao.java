package com.javamonkeys.dao.game;

public interface ITurnDao {

    public void SaveTurn(Turn turn);

    public Turn getLastTurn(Game game);

}
