package org.example.examrecap.ctrl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.examrecap.model.Game;
import org.example.examrecap.service.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("game")
public class GameController {
  private static final Logger logger = LogManager.getLogger(GameController.class);
  private final GameService gameService;

  public GameController(GameService gameService) {
    this.gameService = gameService;
  }

  @GetMapping("{userId}")
  public Game getGame(@PathVariable Long userId) {
    Game game = gameService.getActiveOrStartNewGame(userId);
    logger.info("User {} retrieved active game or started new game {}", userId, game);
    return game;
  }

  @PutMapping
  public Game updateGame(@RequestBody Game game) {
    Game finishedGame = gameService.updateGame(game);
    logger.info("User {} updated game {}", game.getUserId(), finishedGame);
    return finishedGame;
  }
}