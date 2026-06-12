package org.example.examrecap.ctrl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.examrecap.model.Game;
import org.example.examrecap.model.GameStatus;
import org.example.examrecap.model.Move;
import org.example.examrecap.service.GameService;
import org.example.examrecap.service.MoveService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("move")
public class MoveController {
  private static final Logger logger = LogManager.getLogger(MoveController.class);
  private final MoveService moveService;
  private final GameService gameService;

  public MoveController(MoveService moveService, GameService gameService) {
    this.moveService = moveService;
    this.gameService = gameService;
  }

  @PostMapping
  public Object saveMove(@RequestBody Move move) {
    int outcome = moveService.saveMove(move.getGameId(), move.getMoveType());
    logger.info("Processed move for gameId {} with outcome {}", move.getGameId(), outcome);
    
    Game updatedGame = gameService.getGameById(move.getGameId());
    
    if (updatedGame != null && updatedGame.getStatus() == GameStatus.FINISHED) {
      Game newGame = gameService.startNewGame(updatedGame.getUserId());
      logger.info("Game ID {} was loss, starting new game ID {}", move.getGameId(), newGame.getId());
      return new MoveResponse(outcome, newGame);
    }
    
    return new MoveResponse(outcome, updatedGame);
  }
  
  public static class MoveResponse {
    public int outcome;
    public Game game;
    
    public MoveResponse(int outcome, Game game) {
        this.outcome = outcome;
        this.game = game;
    }
  }
}