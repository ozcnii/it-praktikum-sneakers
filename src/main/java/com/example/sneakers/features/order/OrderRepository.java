package com.example.sneakers.features.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sneakers.features.user.UserAccount;

public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findAllByUser(UserAccount user);
}
