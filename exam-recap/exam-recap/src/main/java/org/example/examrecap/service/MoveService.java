package org.example.examrecap.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.examrecap.model.Game;
import org.example.examrecap.model.GameStatus;
import org.example.examrecap.model.Move;
import org.example.examrecap.repo.GameRepo;
import org.example.examrecap.repo.MoveRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class MoveService {
  private static final Logger logger = LogManager.getLogger(MoveService.class);
  private final MoveRepo moveRepo;
  private final GameRepo gameRepo;

  public MoveService(MoveRepo moveRepo, GameRepo gameRepo) {
    this.moveRepo = moveRepo;
    this.gameRepo = gameRepo;
  }

  public int saveMove(long gameId, int moveType) {
    Optional<Game> optionalGame = gameRepo.findById(gameId);
    if (optionalGame.isEmpty()) {
      return -1;
    }
    Game game = optionalGame.get();

    int outcome;
    Random random = new Random();
    int compMove = random.nextInt(3) + 1;
    logger.info("Game ID {}: Move: {}, Comp move: {}", gameId, moveType, compMove);
    
    if (compMove == moveType) {
      outcome = 1; // Draw
      game.setDraws(game.getDraws() + 1);
      logger.info("Game ID {}: Draw. Total draws: {}", gameId, game.getDraws());
    } else if ((moveType == 1 && compMove == 3) ||
               (moveType == 2 && compMove == 1) ||
               (moveType == 3 && compMove == 2)) {
      outcome = 2; // Win
      game.setScore(game.getScore() + 1);
      logger.info("Game ID {}: Win. Total score: {}", gameId, game.getScore());
    } else {
      outcome = 3; // Loss
      game.setStatus(GameStatus.FINISHED);
      logger.info("Game ID {}: Loss. Game finished with score: {}, draws: {}", gameId, game.getScore(), game.getDraws());
    }
    game.setTimestamp(LocalDateTime.now());
    gameRepo.save(game);

    Move move = new Move();
    move.setGameId(gameId);
    move.setMoveType(moveType);
    moveRepo.save(move);
    logger.info("Saved move {}, computer move {}, outcome {} ", move, compMove, outcome);
    
    return outcome;
  }
}