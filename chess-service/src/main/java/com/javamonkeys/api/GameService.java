package com.javamonkeys.api;

import com.javamonkeys.dao.game.Game;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ose on 4/3/15.
 */
@RestController
@RequestMapping("/chess/game")
public class GameService{

    @RequestMapping("/{id}")
    public Game getGame(@RequestParam("id") String gameId) {

        return new Game();
    }
}
