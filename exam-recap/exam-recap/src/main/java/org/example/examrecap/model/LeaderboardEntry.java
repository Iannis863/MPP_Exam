package org.example.examrecap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardEntry {
    private String username;
    private long score;
    private long draws;
    private LocalDateTime timestamp;
}