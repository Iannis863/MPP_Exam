package org.example.examrecap.ctrl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.examrecap.model.Statistics;
import org.example.examrecap.service.StatisticsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("stats")
public class StatisticsController {
  private final static Logger logger = LogManager.getLogger(StatisticsController.class);

  private final StatisticsService statisticsService;

  public StatisticsController(StatisticsService statisticsService) {
    this.statisticsService = statisticsService;
  }

  @GetMapping
  public Statistics getGlobalStatistics() {
    return statisticsService.getGlobalStatistics();
  }

  @GetMapping("{userId}")
  public Statistics getStatistics(@PathVariable long userId) {
    return statisticsService.getStatistics(userId);
  }

  @DeleteMapping("{userId}")
  public void deleteStatistics(@PathVariable long userId) {
    statisticsService.deleteUserStatistics(userId);
  }
}