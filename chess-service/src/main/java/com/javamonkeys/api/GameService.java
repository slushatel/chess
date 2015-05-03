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
import java.util.List;

//import com.fasterxml.jackson.annotation.*;

import javax.inject.Inject;


@RestController
@RequestMapping("/game")
public class GameService implements IGameService {

    @Inject
    private GameDao gameDao;

    @Inject
    private UserDao userDao;

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
        User user = userDao.getUserById(userId);
        Game g = gameDao.createGame(user, createGameRequest.isWhite, createGameRequest.gameLength);

//        if (userPlayWhite) {
//            g.setWhite(user);
//        } else {
//            g.setBlack(user);
//        }
//        try {
//            gameDao.updateGame(g);
//        } catch (GameNotFoundException e) {
//            e.printStackTrace();
//            return createRespEntity(null, e.getMessage());
//        }
        CreateGameResponse resp = new CreateGameResponse(g.getId());
        return createRespEntity(resp, "");
    }

    @Transactional
    @RequestMapping(value = "/user-games/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<Game>> getGamesByUserId(@PathVariable(value = "userId") String userId) {
        User user = userDao.getUserById(userId);
        Session hsf = hibernateSessionFactory.getCurrentSession();
        Query query = hsf.createQuery(
                "from Game where author = :user");
        query.setParameter("user", user);
        List<Game> list = query.list();

        return createRespEntity(list, "");
    }

    @Transactional
    @RequestMapping(value = "/connect/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<CreateGameResponse> connectToGame(@PathVariable("gameId") int gameId) {
        Game g = gameDao.getGame(gameId);

        String userId = RequestInfo.userId2;
        User user = userDao.getUserById(userId);

        if (g.getBlack() == null) {
            g.setBlack(user);
        }else{
            g.setWhite(user);
        };



        try {
            gameDao.updateGame(g);
        } catch (GameNotFoundException e) {
            e.printStackTrace();
        }

//        Session hsf = hibernateSessionFactory.getCurrentSession();
//        Query query = hsf.createQuery(
//                "from Game where status = :status");
//        query.setParameter("status", GameStatus.NEW);
//        query.setMaxResults(1);
//        List<Game> list = query.list();
//        if (list.size() == 0){
//            return createRespEntity(null, "");
//        }

        CreateGameResponse resp = new CreateGameResponse(g.getId());
        return createRespEntity(resp, "");
    }

    @Transactional
    @RequestMapping(value = "/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<Game> getGame(@PathVariable(value = "gameId") int gameId) {
        Game g = gameDao.getGame(gameId);
        return createRespEntity(g, "");
    }

    @Transactional
    @RequestMapping(value = "/turn", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveTurn(@RequestBody Turn turn) {
        try {
            gameDao.saveTurn(turn.gameId, turn.fen);
        } catch (GameNotFoundException e) {
            return createRespEntity(null, e.getMessage());
        } catch (Exception e) {
            return createRespEntity(null, e.getMessage());
        }

        return createRespEntity(true, "");
    }

    @Transactional
//    @ResponseBody
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteGame(@PathVariable(value = "id") int id) {
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

    public static class Turn {

        public int gameId;
        public String fen;
        public String pgn;

        public Turn() {
        }

        public String getFen() {
            return fen;
        }

        public void setFen(String fen) {
            this.fen = fen;
        }

        public String getPgn() {
            return pgn;
        }

        public void setPgn(String pgn) {
            this.pgn = pgn;
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

        public CreateGameRequest setIsWhite(boolean isWhite) {
            this.isWhite = isWhite;
            return this;
        }

        public int getGameLength() {
            return gameLength;
        }

        public CreateGameRequest setGameLength(int gameLength) {
            this.gameLength = gameLength;
            return this;
        }
    }

    public static class CreateGameResponse {

        public int gameId;

        public CreateGameResponse() {}

        public CreateGameResponse(int gameId) {
            this.gameId = gameId;
        }

        public int getGameId() {
            return gameId;
        }

        public void setGameId(int gameId) {
            this.gameId = gameId;
        }
    }

}


