package com.example.sneakers.features.admin;

import com.example.sneakers.features.order.Order;
import com.example.sneakers.features.order.OrderRepository;
import com.example.sneakers.features.order.OrderStatus;
import com.example.sneakers.features.user.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

  @Autowired
  private OrderRepository orderRepository;

  @GetMapping
  public String getAllOrders(Model model) {
    List<Order> orders = orderRepository.findAll();
    model.addAttribute("orders", orders);
    return "admin/orders_list";
  }

  @PostMapping("/{orderId}/status")
  public String updateOrderStatus(
      @PathVariable Long orderId,
      @RequestParam String orderStatus,
      Model model) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new IllegalArgumentException("Заказ с ID " + orderId + " не найден"));
    order.setOrderStatus(OrderStatus.valueOf(orderStatus));
    orderRepository.save(order);
    return "redirect:/admin/orders";
  }
}
