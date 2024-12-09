package com.example.sneakers.features.order;

import com.example.sneakers.features.sneaker.entities.Sneaker;
import com.example.sneakers.features.sneaker.repositories.SneakerRepository;
import com.example.sneakers.features.user.UserAccount;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  @Autowired
  private SneakerRepository sneakerRepository;

  @Autowired
  private OrderRepository orderRepository;

  @PostMapping("/create")
  public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest request,
      @AuthenticationPrincipal UserAccount currentUser) {
    Optional<Sneaker> optionalSneaker = sneakerRepository.findById(request.getSneakerId());

    if (optionalSneaker.isPresent()) {
      Sneaker sneaker = optionalSneaker.get();
      Order order = new Order();
      order.setSneaker(sneaker);
      order.setQuantity(request.getQuantity());
      order.setTotalPrice(sneaker.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
      order.setUser(currentUser);
      order.setOrderDate(LocalDateTime.now());
      orderRepository.save(order);
      return ResponseEntity.ok("Заказ успешно создан");
    } else {
      return ResponseEntity.badRequest().body("Кроссовок не найден");
    }
  }

  @GetMapping
  public ResponseEntity<List<Order>> getUserOrders(@AuthenticationPrincipal UserAccount currentUser) {
    List<Order> orders = orderRepository.findAllByUser(currentUser);
    return ResponseEntity.ok(orders);
  }

  public static class CreateOrderRequest {
    @NotNull(message = "ID обуви не должен быть равен нулю")
    private Long sneakerId;

    @Min(value = 1, message = "Количество должно быть не менее 1")
    private int quantity;

    public Long getSneakerId() {
      return sneakerId;
    }

    public void setSneakerId(Long sneakerId) {
      this.sneakerId = sneakerId;
    }

    public int getQuantity() {
      return quantity;
    }

    public void setQuantity(int quantity) {
      this.quantity = quantity;
    }
  }
}
