package org.example.examrecap.ctrl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.examrecap.model.Game;
import org.example.examrecap.model.LoginResponse;
import org.example.examrecap.model.User;
import org.example.examrecap.service.GameService;
import org.example.examrecap.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
  private static final Logger logger = LogManager.getLogger(UserController.class);
  private final UserService userService;
  private final GameService gameService;

  public UserController(UserService userService, GameService gameService) {
    this.userService = userService;
    this.gameService = gameService;
  }

  @PostMapping
  public LoginResponse login(@RequestBody String name) {
    logger.info("Login request received with body: [{}]", name);
    if (name.startsWith("\"") && name.endsWith("\"")) {
      name = name.substring(1, name.length() - 1);
      logger.info("Trimmed quotes from username: [{}]", name);
    }
    User user = userService.login(name);
    Game game = gameService.getActiveOrStartNewGame(user.getId());
    logger.info("User {} logged in, active game: {}", user.getName(), game.getId());
    return new LoginResponse(user, game);
  }
}
