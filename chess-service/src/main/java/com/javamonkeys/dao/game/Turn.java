package com.javamonkeys.dao.game;

import com.javamonkeys.dao.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Turns")
public class Turn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "turnDate")
    private Date turnDate;

    @Column(name = "piece")
    @Enumerated(EnumType.STRING)
    private Pieces piece;

    @Column(name = "startPosition")
    private String startPosition;

    @Column(name = "endPosition")
    private String endPosition;

    @Column(name = "fen")
    private String fen;

    public Turn() {

    }

    public Turn(Game game, User user, Date turnDate, Pieces piece, String startPosition, String endPosition, String fen) {
        this.game = game;
        this.user = user;
        this.turnDate = turnDate;
        this.piece = piece;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.fen = fen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Enumerated(EnumType.STRING)
    public Date getTurnDate() {
        return turnDate;
    }

    @Enumerated(EnumType.STRING)
    public void setTurnDate(Date turnDate) {
        this.turnDate = turnDate;
    }

    public Pieces getPiece() {
        return piece;
    }

    public void setPiece(Pieces piece) {
        this.piece = piece;
    }

    public String getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public String getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(String endPosition) {
        this.endPosition = endPosition;
    }

    public String getFen() {
        return fen;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }
}
