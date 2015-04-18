package com.javamonkeys.api;

import com.javamonkeys.dao.game.Game;
import com.javamonkeys.dao.game.GameDao;
import com.javamonkeys.dao.game.GameNotFoundException;
import com.javamonkeys.dao.game.IGameDao;
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

import com.fasterxml.jackson.annotation.*;

import javax.inject.Inject;


@RestController
@RequestMapping("/game")
public class GameService {

    @Inject
    private GameDao gameDao;

    @Inject
    private UserDao userDao;

    @Inject
    private SessionFactory hibernateSessionFactory;

    //    @Inject
    //    RequestInfo requestInfo;
    static class requestInfo {
        public static final int userId = 1;
    }

    ;

    //    @ResponseBody
    //    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    //    public Game getGameExample() {
    //        return new Game(new User("email", "no password"));
    //    }

    private <T> ResponseEntity<resp<T>> CreateRespEntity(T res, String err) {
        resp<T> response = new resp<T>(res, err);
        ResponseEntity<resp<T>> result = new ResponseEntity<resp<T>>(response, HttpStatus.OK);
        return result;
    }

    @Transactional
    @ResponseBody
    @RequestMapping(value = "/user-games/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<resp<List<Game>>> getGamesByUserId(@PathVariable(value = "userId") int userId) {
        //        String email = "98765";
        //        if (userDao.getUserByEmail(email) == null) {
        //            try {
        //                UserAccessGroup gr;
        //                try {
        //                    gr = userDao.createUserAccessGroup("gr1", true);
        //                } catch (UserAccessGroupAlreadyExistException e){
        //                    gr = userDao.getUserAccessGroup("gr1");
        //                };
        //
        //                userDao.createUser(email, "pass", gr);
        //            } catch (Exception e) {
        //                e.printStackTrace();
        //            }
        //        }
        //        User u = userDao.getUserByEmail(email);
        //        Game g = gameDao.createGame(u);
        //        g.setWhite(u);
        //        try {
        //            gameDao.updateGame(g);
        //        } catch (GameNotFoundException e) {
        //            e.printStackTrace();
        //        }

        User user = userDao.getUserById(userId);
        Session hsf = hibernateSessionFactory.getCurrentSession();
        Query query = hsf.createQuery(
                "from Game where author = :user");
        query.setParameter("user", user);
        List<Game> list = query.list();

        return CreateRespEntity(list, "");
    }

    @Transactional
    @ResponseBody
    @RequestMapping(value = "/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<resp<Game>> getGame(@PathVariable(value = "gameId") int gameId) {
        Game g = gameDao.getGame(gameId);
        return CreateRespEntity(g, "");
    }

    @Transactional
    @ResponseBody
    @RequestMapping(value = "/new-game", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<resp<Game>> getNewGame(@RequestParam(value = "userPlayWhite") boolean userPlayWhite) {
        int userId = requestInfo.userId;
        User user = userDao.getUserById(userId);
        Game g = gameDao.createGame(user);
        if (userPlayWhite) {
            g.setWhite(user);
        } else {
            g.setBlack(user);
        }
        try {
            gameDao.updateGame(g);
        } catch (GameNotFoundException e) {
            e.printStackTrace();
            return CreateRespEntity(null, e.getMessage());
        }
        return CreateRespEntity(g, "");
    }

    @Transactional
    @ResponseBody
    @RequestMapping(value = "/turn", method = RequestMethod.POST)
    public ResponseEntity<resp<Boolean>> saveTurn(@RequestBody Turn turn) {
        try {
            gameDao.saveTurn(turn.gameId, turn.fen);
        } catch (GameNotFoundException e) {
            return CreateRespEntity(null, e.getMessage());
        } catch (Exception e) {
            return CreateRespEntity(null, e.getMessage());
        }

        return CreateRespEntity(true, "");
    }

    @Transactional
    @ResponseBody
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public ResponseEntity<resp<Boolean>> deleteGame(@PathVariable(value = "id") int id) {
        try {
            gameDao.deleteGame(id);
        } catch (GameNotFoundException e) {
            return CreateRespEntity(null, e.getMessage());
        } catch (Exception e) {
            return CreateRespEntity(null, e.getMessage());
        }
        return CreateRespEntity(true, "");
    }

    public static class resp<T> {
        T value;
        String error;

        public resp() {
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

        resp(T value, String error) {
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
}


