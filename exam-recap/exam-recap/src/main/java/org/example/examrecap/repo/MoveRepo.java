package org.example.examrecap.repo;

import org.example.examrecap.model.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MoveRepo extends JpaRepository<Move, Long> {
  List<Move> findAllByGameId(long gameId);

  void deleteAllByGameIdIn(Collection<Long> gameIds);
}
