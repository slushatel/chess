package com.javamonkeys.api;

import com.javamonkeys.dao.game.Game;
import com.javamonkeys.dao.game.GameDao;
import com.javamonkeys.dao.game.GameNotFoundException;
import com.javamonkeys.dao.game.IGameDao;
import com.javamonkeys.dao.user.User;
import com.javamonkeys.dao.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

//import javax.inject.Inject;



@RestController
@RequestMapping("/game")
public class GameService {
//    @Autowired
//    private IGameDao gameDao;

//    @ResponseBody
//    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
//    public Game getGameExample() {
//        return new Game(new User("email", "no password"));
//    }

    @ResponseBody
    @RequestMapping(value = "/user-games/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ArrayList<Game> getGamesByUserId(@RequestParam(value = "userId") int userId) {
        GameDao gameDao = new GameDao();

        //return gameDao.getGamesByUserId(userId);
        return new ArrayList<Game>();
    }

    @ResponseBody
    @RequestMapping(value = "/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Game getGame(@RequestParam(value = "gameId") int gameId) {
        GameDao gameDao = new GameDao();
        return gameDao.getGame(gameId);
    }

    @ResponseBody
    @RequestMapping(value = "/new-game", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Game getNewGame(@RequestParam(value = "userToken") String userToken) {
        UserDao userDao = new UserDao();
        User user = userDao.getUserByToken(userToken);
        return new Game(user);
    }

    @ResponseBody
    @RequestMapping(value = "/turn", method = RequestMethod.POST)
    public ResponseEntity<resp<Game>> saveTurn(@RequestBody TurnJson turn) {
        GameDao gameDao = new GameDao();

        try {
            //gameDao.saveTurn(turn.gameId, turn.fen, turn.pgn);
            Game game = gameDao.getGame(turn.gameId);
            resp<Game> resp1 = new resp<Game>(game, "");
            return new ResponseEntity<resp<Game>>(resp1, HttpStatus.OK);
//        } catch (GameNotFoundException e) {
//            return null;
        } catch (Exception e) {
            return null;
        }

    }

    class resp<T>{
        T value;
        String error;
        resp(T value, String error){
            this.value = value;
            this.error = error;
        }
    }

    class TurnJson {
        int gameId;
        String fen;
        String pgn;
    }

    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public @ResponseBody String deleteGame(@RequestParam(value = "id") int id) {
        GameDao gameDao = new GameDao();

        try {
            gameDao.deleteGame(id);
        } catch (GameNotFoundException e){
            return e.getMessage();
        }catch(Exception e){
            return "unknown exception founded";
        }
        return "the game with id " + id + " has been deleted";
    }

}




