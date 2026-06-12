package org.example.examrecap.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "game_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Column
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true)
  private String name;
}
