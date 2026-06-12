package org.example.examrecap.service;

import org.example.examrecap.model.Game;
import org.example.examrecap.model.GameStatus;
import org.example.examrecap.repo.GameRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
  private final GameRepo gameRepo;

  public GameService(GameRepo gameRepo) {
    this.gameRepo = gameRepo;
  }

  public Game getGameById(long id) {
      return gameRepo.findById(id).orElse(null);
  }

  public Game getActiveOrStartNewGame(long userId) {
    List<Game> activeGames = gameRepo.findAllByStatusAndUserId(GameStatus.STARTED, userId);
    if (!activeGames.isEmpty()) {
      return activeGames.get(0);
    }
    return startNewGame(userId);
  }

  public Game startNewGame(long userId) {
    Game game = new Game();
    game.setUserId(userId);
    game.setScore(0);
    game.setDraws(0);
    game.setStatus(GameStatus.STARTED);
    game.setTimestamp(LocalDateTime.now());
    return gameRepo.save(game);
  }

  public Game updateGame(Game game) {
    game.setTimestamp(LocalDateTime.now());
    return gameRepo.save(game);
  }
}