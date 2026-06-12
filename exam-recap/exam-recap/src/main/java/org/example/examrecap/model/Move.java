package org.example.examrecap.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "moves")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Move {
  @Column
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column
  private long gameId;
  @Column
  private int moveType;
}
