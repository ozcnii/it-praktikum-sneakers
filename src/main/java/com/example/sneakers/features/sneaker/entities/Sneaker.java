package com.example.sneakers.features.sneaker.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.sneakers.features.user.UserAccount;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sneaker {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String brand;
  @Min(value = 0, message = "Цена не может быть отрицательной")
  private BigDecimal price;
  private String description;
  private String imageUrl;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  @ManyToOne
  private UserAccount supplier;
}
