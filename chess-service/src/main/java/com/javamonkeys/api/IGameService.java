package com.javamonkeys.api;

import com.javamonkeys.dao.game.Game;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IGameService {
//    @Transactional
//    @RequestMapping(value = "/user-games/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
//    ResponseEntity<List<Game>> getGamesByUserId(@PathVariable(value = "userId") String userId);
//
//    @Transactional
//    @RequestMapping(value = "/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
//    ResponseEntity<GameService.Resp<Game>> getGame(@PathVariable(value = "gameId") int gameId);
//
//    @Transactional
//    @RequestMapping(value = "/new-game", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
//    ResponseEntity<GameService.Resp<Game>> getNewGame(@RequestParam(value = "userPlayWhite") boolean userPlayWhite);
//
//    @Transactional
//    @RequestMapping(value = "/turn", method = RequestMethod.POST)
//    ResponseEntity<GameService.Resp<Boolean>> saveTurn(@RequestBody GameService.Turn turn);
//
//    @Transactional
//    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
//    ResponseEntity<GameService.Resp<Boolean>> deleteGame(@PathVariable(value = "id") int id);
}
