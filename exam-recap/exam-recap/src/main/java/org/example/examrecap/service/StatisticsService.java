package org.example.examrecap.service;

import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.examrecap.model.*;
import org.example.examrecap.repo.GameRepo;
import org.example.examrecap.repo.MoveRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {
  private final static Logger logger = LogManager.getLogger(StatisticsService.class);
  private final MoveRepo moveRepo;
  private final GameRepo gameRepo;

  public StatisticsService(MoveRepo moveRepo, GameRepo gameRepo) {
    this.moveRepo = moveRepo;
    this.gameRepo = gameRepo;
  }

  public Statistics getGlobalStatistics() {
    logger.info("Getting global statistics");
    Statistics statistics = new Statistics();
    List<Game> games = gameRepo.findAll();
    statistics.setTotalGames(games.size());
    long maxScore = 0;
    for (Game game : games) {
      if (game.getScore() > maxScore) {
        maxScore = game.getScore();
      }
    }
    statistics.setMaxScore(maxScore);
    return statistics;
  }

  public Statistics getStatistics(long userId) {
    logger.info("Getting statistics for user {}", userId);
    Statistics statistics = new Statistics();
    List<Game> games = gameRepo.findAllByUserId(userId);
    statistics.setTotalGames(games.size());
    long maxScore = 0;
    for (Game game : games) {
      if (game.getScore() > maxScore) {
        maxScore = game.getScore();
      }
    }
    statistics.setMaxScore(maxScore);
    logger.info("Statistics for user {}: {}", userId, statistics);
    return statistics;
  }

  @Transactional
  public void deleteUserStatistics(long userId) {
    logger.info("Deleting statistics for user {}", userId);
    List<Game> games = gameRepo.findAllByUserId(userId);
    List<Long> gameIds = games.stream().map(Game::getId).toList();
    moveRepo.deleteAllByGameIdIn(gameIds);
    gameRepo.deleteAllById(gameIds);
    logger.info("Statistics for user {} deleted", userId);
  }
}