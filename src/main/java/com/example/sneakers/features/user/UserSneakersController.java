package com.example.sneakers.features.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.sneakers.features.order.OrderRepository;
import com.example.sneakers.features.sneaker.entities.Sneaker;
import com.example.sneakers.features.sneaker.repositories.SneakerRepository;

@RequestMapping("/user/sneakers")
@Controller
public class UserSneakersController {

  @Autowired
  SneakerRepository sneakerRepository;

  @Autowired
  OrderRepository orderRepository;

  @GetMapping
  public String index(Model model) {

    List<Sneaker> sneakers = sneakerRepository.findAll();
    model.addAttribute("sneakers", sneakers);
    return "user/sneakers/index";
  }
}
