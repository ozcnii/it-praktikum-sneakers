package com.example.sneakers.features.sneaker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sneakers.features.sneaker.entities.Sneaker;

public interface SneakerRepository extends JpaRepository<Sneaker, Long>, SneakerRepositoryCustom {
}