package com.example.sneakers.features.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.sneakers.features.sneaker.entities.Sneaker;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
@AllArgsConstructor
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Sneaker sneaker;

  private Long supplierId;
  private int quantity;
  private BigDecimal totalPrice;
  private LocalDateTime orderDate;
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus = OrderStatus.PENDING;
}
