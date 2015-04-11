package com.javamonkeys.api;

import com.javamonkeys.dao.game.Game;
import com.javamonkeys.dao.game.GameDao;
import com.javamonkeys.dao.game.GameNotFoundException;
import com.javamonkeys.dao.game.IGameDao;
import com.javamonkeys.dao.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

//import javax.inject.Inject;



@RestController
@RequestMapping("/game")
public class GameService {
//    @Autowired
//    private IGameDao gameDao;

    @ResponseBody
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Game getGame() {
        return new Game(new User("email","no password"));
    }

    @ResponseBody
    @RequestMapping(value = "/turn", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public boolean saveTurn(@RequestBody TurnJson turn) {
        GameDao gameDao = new GameDao();

        try {
            gameDao.saveTurn(turn.id, turn.move);
        } catch (GameNotFoundException e) {
            return false;
        } catch (Exception e) {
            return false;
        }

        return true;
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

class TurnJson {
    int id;
    String move;
}


