package com.javamonkeys.api;

import com.javamonkeys.dao.game.Game;
import com.javamonkeys.dao.game.GameDao;
import com.javamonkeys.dao.game.GameNotFoundException;
import com.javamonkeys.dao.user.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by ose on 4/3/15.
 */
@RestController
@RequestMapping("/game")
public class GameService {

//    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/get-game", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Game getGame() {
        return new Game(new User("email","no password"));
    }


    @RequestMapping(value = "/save-turn", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean saveTurn(@RequestParam(value = "id") int id, @RequestParam(value = "turn") String turn) {
        GameDao gameDao = new GameDao();

       try {
           gameDao.saveTurn(id, turn);
       } catch (GameNotFoundException e){
         return false;
       }catch(Exception e){
           return false;
       }

        return true;
    }

    @RequestMapping(value = "/delete-game", produces = MediaType.APPLICATION_JSON_VALUE)
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


