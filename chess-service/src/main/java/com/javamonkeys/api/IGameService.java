package com.javamonkeys.api;

import com.javamonkeys.dao.game.Game;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IGameService {
    @Transactional
    @ResponseBody
    @RequestMapping(value = "/user-games/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    ResponseEntity<GameService.resp<List<Game>>> getGamesByUserId(@PathVariable(value = "userId") int userId);

    @Transactional
    @ResponseBody
    @RequestMapping(value = "/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    ResponseEntity<GameService.resp<Game>> getGame(@PathVariable(value = "gameId") int gameId);

    @Transactional
    @ResponseBody
    @RequestMapping(value = "/new-game", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    ResponseEntity<GameService.resp<Game>> getNewGame(@RequestParam(value = "userPlayWhite") boolean userPlayWhite);

    @Transactional
    @ResponseBody
    @RequestMapping(value = "/turn", method = RequestMethod.POST)
    ResponseEntity<GameService.resp<Boolean>> saveTurn(@RequestBody GameService.Turn turn);

    @Transactional
    @ResponseBody
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    ResponseEntity<GameService.resp<Boolean>> deleteGame(@PathVariable(value = "id") int id);
}
