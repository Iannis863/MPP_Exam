package org.example.examrecap.repo;

import org.example.examrecap.model.Game;
import org.example.examrecap.model.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepo extends JpaRepository<Game, Long> {
  List<Game> findAllByStatusAndUserId(GameStatus status, long userId);

  List<Game> findAllByUserId(long userId);

  List<Game> findAllByStatusOrderByScoreDescDrawsDescTimestampDesc(GameStatus status);
}