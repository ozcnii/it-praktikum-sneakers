package com.example.sneakers.features.user;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.sneakers.features.order.Order;
import com.example.sneakers.features.order.OrderRepository;
import com.example.sneakers.features.order.OrderService;
import com.example.sneakers.features.sneaker.entities.Sneaker;
import com.example.sneakers.features.sneaker.repositories.SneakerRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/user/sneakers")
@Controller
public class UserSneakersController {

  @Autowired
  SneakerRepository sneakerRepository;

  @Autowired
  OrderService orderService;

  @Autowired
  OrderRepository orderRepository;

  @GetMapping
  public String index(Model model) {

    List<Sneaker> sneakers = sneakerRepository.findAll();
    model.addAttribute("sneakers", sneakers);
    return "user/sneakers/index";
  }

  @PostMapping("/order")
  public ModelAndView placeOrder(@RequestParam("sneakerId") Long sneakerId, @RequestParam("quantity") int quantity) {
    Sneaker sneaker = sneakerRepository.findById(sneakerId)
        .orElseThrow(() -> new RuntimeException("Sneaker not found"));

    Order order = orderService.createOrder(sneaker, sneaker.getSupplier().getId(), quantity);

    ModelAndView modelAndView = new ModelAndView("orderConfirmation");
    modelAndView.addObject("order", order);
    return modelAndView;
  }
}
