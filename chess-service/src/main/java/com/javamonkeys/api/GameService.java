package com.javamonkeys.api;

import com.javamonkeys.dao.game.*;
import com.javamonkeys.dao.user.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.fasterxml.jackson.annotation.*;

import javax.inject.Inject;


@RestController
@RequestMapping("/api/game")
public class GameService implements IGameService {

    @Inject
    private GameDao gameDao;

    @Inject
    private UserDao userDao;

    @Inject
    private TurnDao turnDao;

    @Inject
    private SessionFactory hibernateSessionFactory;

    //    @Inject
    //    RequestInfo requestInfo;
    static class RequestInfo {
        public static final String userId = "1";
        public static final String userId2 = "2";
    }

    ;

    //    @ResponseBody
    //    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    //    public Game getGameExample() {
    //        return new Game(new User("email", "no password"));
    //    }

    private <T> ResponseEntity<T> createRespEntity(T res, String err) {
        ResponseEntity<T> result = new ResponseEntity<T>(res, HttpStatus.OK);
//        Resp<T> response = new Resp<T>(res, err);
//        ResponseEntity<Resp<T>> result = new ResponseEntity<Resp<T>>(response, HttpStatus.OK);
        return result;
    }

    @Transactional
    @RequestMapping(value = "/new-game", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<CreateGameResponse> createGame(@RequestBody CreateGameRequest createGameRequest) {
        String userId = RequestInfo.userId;
        User user = userDao.getUserById(Integer.parseInt(userId));
        Game g = gameDao.createGame(user, createGameRequest.isWhite, createGameRequest.gameLength);

        CreateGameResponse resp = new CreateGameResponse(g.getId(), createGameRequest.isWhite);
        return createRespEntity(resp, "");
    }

//    @Transactional
//    @RequestMapping(value = "/user-games/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
//    public ResponseEntity<List<Game>> getGamesByUserId(@PathVariable(value = "userId") String userId) {
//        User user = userDao.getUserById(userId);
//        Session hsf = hibernateSessionFactory.getCurrentSession();
//        Query query = hsf.createQuery(
//                "from Game where author = :user");
//        query.setParameter("user", user);
//        List<Game> list = query.list();
//
//        return createRespEntity(list, "");
//    }

    @Transactional
    @RequestMapping(value = "/connect/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<CreateGameResponse> connectToGame(@PathVariable("gameId") int gameId) {
        Game g = gameDao.getGame(gameId);

        String userId = RequestInfo.userId2;
        User user = userDao.getUserById(Integer.parseInt(userId));

        boolean isWhite = true;
        if (g.getBlack() == null) {
            g.setBlack(user);
            isWhite = false;
        }else{
            g.setWhite(user);
        };

        try {
            gameDao.updateGame(g);
        } catch (GameNotFoundException e) {
            e.printStackTrace();
        }

        CreateGameResponse resp = new CreateGameResponse(g.getId(), isWhite);
        return createRespEntity(resp, "");
    }

    @Transactional
    @RequestMapping(value = "/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<GetGameResponse> getGame(@PathVariable(value = "gameId") int gameId) {
        Game g = gameDao.getGame(gameId);
        Turn lt = turnDao.getLastTurn(g);
        String fen = null;
        if (lt != null){
            fen = lt.getFen();
        }
        GetGameResponse resp = new GetGameResponse(g.getId(), fen);
        return createRespEntity(resp, "");
    }

    public static class GetGameResponse{
        public int gameId;
        public String fen;

        public GetGameResponse(int gameId, String fen) {
            this.gameId = gameId;
            this.fen = fen;
        }

        public int getGameId() {
            return gameId;
        }

        public void setGameId(int gameId) {
            this.gameId = gameId;
        }

        public GetGameResponse() {
        }

        public String getFen() {
            return fen;
        }

        public void setFen(String fen) {
            this.fen = fen;
        }
    }

    @Transactional
    @RequestMapping(value = "/turn", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveTurn(@RequestBody TurnRequest turnRequest) {
        Game game = gameDao.getGame(turnRequest.gameId);
        User user = null;
        if (turnRequest.userId != null) {
            user = userDao.getUserById(Integer.parseInt(turnRequest.userId));
        }

        Turn turn = new Turn(game, user, new Date(), null, turnRequest.startPosition, turnRequest.endPosition, turnRequest.fen);

        turnDao.saveTurn(turn);

        if (turnRequest.isGameOver()){
            game.setStatus(GameStatus.FINISHED);
        }

        try {
            gameDao.updateGame(game);
        } catch (GameNotFoundException e) {
            e.printStackTrace();
        }

        return createRespEntity(true, "");
    }

    @Transactional
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteGame(@PathVariable(value = "id") Integer id) {
        try {
            gameDao.deleteGame(id);
        } catch (GameNotFoundException e) {
            return createRespEntity(null, e.getMessage());
        } catch (Exception e) {
            return createRespEntity(null, e.getMessage());
        }
        return createRespEntity(true, "");
    }

    public static class Resp<T> {
        T value;
        String error;

        public Resp() {
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        Resp(T value, String error) {
            this.value = value;
            this.error = error;
        }
    }

    public static class TurnRequest {

        public int gameId;
        public String userId;
        public String startPosition;
        public String endPosition;
        public String fen;
        public boolean gameOver;

        public boolean isGameOver() {
            return gameOver;
        }

        public void setGameOver(boolean gameOver) {
            this.gameOver = gameOver;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public TurnRequest() {
        }

        public String getFen() {
            return fen;
        }

        public void setFen(String fen) {
            this.fen = fen;
        }

        public int getGameId() {
            return gameId;
        }

        public void setGameId(int gameId) {
            this.gameId = gameId;
        }
    }

    public static class CreateGameRequest {

        public boolean isWhite;
        public int gameLength;

        public CreateGameRequest() {}

        public boolean isWhite() {
            return isWhite;
        }

        public void setIsWhite(boolean isWhite) {
            this.isWhite = isWhite;
        }

        public int getGameLength() {
            return gameLength;
        }

        public void setGameLength(int gameLength) {
            this.gameLength = gameLength;
        }
    }

    public static class CreateGameResponse {

        public int gameId;
        public boolean isWhite;

        public CreateGameResponse() {}

        public CreateGameResponse(int gameId, boolean isWhite) {
            this.gameId = gameId;
            this.isWhite  = isWhite;
        }

        public boolean isWhite() {
            return isWhite;
        }

        public void setIsWhite(boolean isWhite) {
            this.isWhite = isWhite;
        }

        public int getGameId() {
            return gameId;
        }

        public void setGameId(int gameId) {
            this.gameId = gameId;
        }
    }

}


