package com.example.sneakers.features.sneaker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sneakers.features.sneaker.entities.Sneaker;
import com.example.sneakers.features.user.UserAccount;

public interface SneakerRepository extends JpaRepository<Sneaker, Long>, SneakerRepositoryCustom {
  List<Sneaker> findBySupplier(UserAccount supplier);
}