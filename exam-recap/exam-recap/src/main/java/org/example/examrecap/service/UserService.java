package org.example.examrecap.service;

import org.example.examrecap.model.User;
import org.example.examrecap.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
  private final UserRepo userRepo;

  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  public User login(String name) {
    Optional<User> userByName = userRepo.findByName(name);
    if (userByName.isPresent()) {
      return userByName.get();
    } else {
      User user = new User(0, name);
      user = userRepo.save(user);
      return user;
    }
  }
}
