package org.example.examrecap.ctrl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.examrecap.model.LeaderboardEntry;
import org.example.examrecap.service.LeaderboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("leaderboard")
public class LeaderboardController {
    private static final Logger logger = LogManager.getLogger(LeaderboardController.class);
    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @GetMapping
    public List<LeaderboardEntry> getLeaderboard() {
        logger.info("Fetching leaderboard");
        return leaderboardService.getLeaderboard();
    }
}