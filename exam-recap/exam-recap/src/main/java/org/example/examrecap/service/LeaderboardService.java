package org.example.examrecap.service;

import org.example.examrecap.model.Game;
import org.example.examrecap.model.GameStatus;
import org.example.examrecap.model.LeaderboardEntry;
import org.example.examrecap.model.User;
import org.example.examrecap.repo.GameRepo;
import org.example.examrecap.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    private final GameRepo gameRepo;
    private final UserRepo userRepo;

    public LeaderboardService(GameRepo gameRepo, UserRepo userRepo) {
        this.gameRepo = gameRepo;
        this.userRepo = userRepo;
    }

    public List<LeaderboardEntry> getLeaderboard() {
        List<Game> completedGames = gameRepo.findAllByStatusOrderByScoreDescDrawsDescTimestampDesc(GameStatus.FINISHED);
        
        List<User> users = userRepo.findAll();
        Map<Long, String> userNames = users.stream().collect(Collectors.toMap(User::getId, User::getName));
        
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        for (Game game : completedGames) {
            String username = userNames.get(game.getUserId());
            if (username == null) {
                username = userRepo.findById(game.getUserId()).map(User::getName).orElse("Unknown");
            }
            leaderboard.add(new LeaderboardEntry(
                    username,
                    game.getScore(),
                    game.getDraws(),
                    game.getTimestamp()
            ));
        }
        
        return leaderboard;
    }
}