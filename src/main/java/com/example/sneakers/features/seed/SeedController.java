package com.example.sneakers.features.seed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seed")
public class SeedController {
  @Autowired
  private SeedService seedService;

  @GetMapping
  public ResponseEntity<String> seedDatabase() {
    try {
      seedService.seedData();
      return ResponseEntity.ok("готово");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ошибка: " + e.getMessage());
    }
  }
}
