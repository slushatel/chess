package com.javamonkeys.dao.game;

import com.javamonkeys.dao.user.User;

import java.util.Date;


public class Game {

    private int id;
    Date matchDate;
    private String tournament;
    private String round;
    private User white;
    private User black;
    private String result;
    private String moveText;

    public Game(Date matchDate, User white, User black, int id) {
        this.matchDate = matchDate;
        this.white = white;
        this.black = black;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public User getWhite() {
        return white;
    }

    public void setWhite(User white) {
        this.white = white;
    }

    public User getBlack() {
        return black;
    }

    public void setBlack(User black) {
        this.black = black;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMoveText() {
        return moveText;
    }

    public void setMoveText(String moveText) {
        this.moveText = moveText;
    }
}