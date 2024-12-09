package com.example.sneakers.features.user;

import com.example.sneakers.features.order.Order;
import com.example.sneakers.features.order.OrderRepository;
import com.example.sneakers.features.sneaker.entities.Sneaker;
import com.example.sneakers.features.sneaker.repositories.SneakerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/user/orders")
public class UserOrderController {

  @Autowired
  private SneakerRepository sneakerRepository;

  @Autowired
  private OrderRepository orderRepository;

  @GetMapping("/create/{sneakerId}")
  public String showCreateOrderForm(@PathVariable Long sneakerId, Model model) {
    Sneaker sneaker = sneakerRepository.findById(sneakerId).orElse(null);
    model.addAttribute("sneaker", sneaker);
    model.addAttribute("order", new Order());
    return "user/order/create";
  }

  @PostMapping("/create")
  public String createOrder(@ModelAttribute Order order,
      @RequestParam Long sneakerId,
      @AuthenticationPrincipal UserAccount currentUser) {
    Sneaker sneaker = sneakerRepository.findById(sneakerId).orElse(null);
    if (sneaker != null) {
      order.setSneaker(sneaker);
      order.setTotalPrice(sneaker.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())));
      order.setUser(currentUser);
      order.setOrderDate(LocalDateTime.now());
      orderRepository.save(order);
    }
    return "redirect:/user/orders/success";
  }

  @GetMapping("/success")
  public String orderSuccess() {
    return "user/order/success";
  }

  @GetMapping
  public String showUserOrders(@AuthenticationPrincipal UserAccount currentUser, Model model) {
    List<Order> orders = orderRepository.findAllByUser(currentUser);
    model.addAttribute("orders", orders);
    return "user/order/list";
  }
}
