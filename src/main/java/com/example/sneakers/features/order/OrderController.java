package com.example.sneakers.features.order;

import com.example.sneakers.features.sneaker.entities.Sneaker;
import com.example.sneakers.features.sneaker.repositories.SneakerRepository;
import com.example.sneakers.features.user.UserAccount;

import jakarta.validation.constraints.Max;
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

  @Autowired
  private OrderItemRepository orderItemRepository;

  @PostMapping("/create")
  public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest request,
      @AuthenticationPrincipal UserAccount currentUser) {
    Order order = new Order();
    order.setUser(currentUser);
    order.setOrderDate(LocalDateTime.now());
    order.setTotalPrice(BigDecimal.ZERO);
    orderRepository.save(order);

    for (CreateOrderItemRequest itemRequest : request.getItems()) {
      Optional<Sneaker> optionalSneaker = sneakerRepository.findById(itemRequest.getSneakerId());

      if (optionalSneaker.isPresent()) {
        Sneaker sneaker = optionalSneaker.get();
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setSneaker(sneaker);
        orderItem.setQuantity(itemRequest.getQuantity());
        orderItem.setPrice(sneaker.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
        orderItemRepository.save(orderItem);

        order.setTotalPrice(order.getTotalPrice().add(orderItem.getPrice()));
      } else {
        return ResponseEntity.badRequest().body("Кроссовок с ID " + itemRequest.getSneakerId() + " не найден");
      }
    }

    orderRepository.save(order);
    return ResponseEntity.ok("Заказ успешно создан");
  }

  @GetMapping
  public ResponseEntity<List<Order>> getUserOrders(@AuthenticationPrincipal UserAccount currentUser) {
    List<Order> orders = orderRepository.findAllByUser(currentUser);
    return ResponseEntity.ok(orders);
  }

  public static class CreateOrderRequest {
    @NotNull(message = "Список элементов заказа не должен быть пустым")
    private List<CreateOrderItemRequest> items;

    public List<CreateOrderItemRequest> getItems() {
      return items;
    }

    public void setItems(List<CreateOrderItemRequest> items) {
      this.items = items;
    }
  }

  public static class CreateOrderItemRequest {
    @NotNull(message = "ID обуви не должен быть равен нулю")
    private Long sneakerId;

    @Min(value = 1, message = "Количество должно быть не менее 1")
    @Max(value = 10, message = "Количество не должно превышать 10")
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
