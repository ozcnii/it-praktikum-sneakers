package com.example.sneakers.features.sneaker.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import com.example.sneakers.features.user.UserAccount;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierSneaker {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "supplier_id")
  private UserAccount supplier;

  @ManyToOne
  @JoinColumn(name = "sneaker_id")
  private Sneaker sneaker;

  private LocalDateTime createdAt;
}
