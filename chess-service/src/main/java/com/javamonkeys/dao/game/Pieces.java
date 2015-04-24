package com.javamonkeys.dao.game;

public enum Pieces {

    KING ("K"),
    QUEEN("Q"),
    ROOK("R"),
    BISHOP("B"),
    KNIGHT("N"),
    PAWN("p");

    private String shortName;

    Pieces(String shortName){
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }
}
