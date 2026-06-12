package org.example.examrecap.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "games")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Game {
  @Column
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column
  private long userId;
  @Column
  private long score;
  @Column
  private long draws;
  @Column
  @Enumerated(EnumType.STRING)
  private GameStatus status;
  @Column
  private LocalDateTime timestamp;
}