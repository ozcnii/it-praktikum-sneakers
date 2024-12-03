package com.example.sneakers.features.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sneakers.features.sneaker.entities.Sneaker;

@Service
@Transactional
public class OrderService {
  @Autowired
  private OrderRepository orderRepository;

  public Order createOrder(Sneaker sneaker, Long supplierId, int quantity) {
    BigDecimal totalPrice = sneaker.getPrice().multiply(BigDecimal.valueOf(quantity));

    Order order = new Order();
    order.setSneaker(sneaker);
    order.setSupplierId(supplierId);
    order.setQuantity(quantity);
    order.setTotalPrice(totalPrice);
    order.setOrderDate(LocalDateTime.now());

    return orderRepository.save(order);

  }
}
