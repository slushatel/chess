package com.javamonkeys.api;

import com.javamonkeys.dao.game.Game;
import com.javamonkeys.dao.user.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ose on 4/3/15.
 */
@RestController
@RequestMapping("/game")
public class GameService {

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Game getGame() {

        return new Game(new User("email","no password"));
    }
}
