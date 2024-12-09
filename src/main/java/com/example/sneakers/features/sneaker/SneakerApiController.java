package com.example.sneakers.features.sneaker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sneakers.features.sneaker.entities.Sneaker;

@RequestMapping("/api")
@RestController()
public class SneakerApiController {

  @Autowired
  private SneakerService sneakerService;

  @GetMapping("/sneakers")
  public List<Sneaker> getAllSneakers() {
    return sneakerService.findAll();
  }
}
