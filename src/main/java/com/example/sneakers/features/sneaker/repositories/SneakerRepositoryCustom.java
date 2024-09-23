package com.example.sneakers.features.sneaker.repositories;

import java.util.List;

import com.example.sneakers.features.sneaker.dtos.SneakerSupplierDTO;

public interface SneakerRepositoryCustom {
  List<SneakerSupplierDTO> findAllSneakersWithSuppliers();
}