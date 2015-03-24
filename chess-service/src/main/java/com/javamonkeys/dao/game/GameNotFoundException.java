package com.javamonkeys.dao.game;

public class GameNotFoundException extends Exception{

    public GameNotFoundException(String msg) {
        super(msg);
    }

    public GameNotFoundException(){
        super("Game not found!");
    }


}
