package com.example.sneakers.features.sneaker.repositories;

import java.util.List;

import com.example.sneakers.features.sneaker.dtos.SneakerSupplierDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class SneakerRepositoryImpl implements SneakerRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SneakerSupplierDTO> findAllSneakersWithSuppliers() {
        String query = "SELECT new com.example.demo.dto.SneakerSupplierDTO(" +
                "s.id, s.name, s.brand, s.price, s.description, s.imageUrl, " +
                "u.id, u.username, u.email) " +
                "FROM Sneaker s " +
                "JOIN SupplierSneaker ss ON s.id = ss.sneaker.id " +
                "JOIN UserAccount u ON ss.supplier.id = u.id " +
                "ORDER BY s.id";

        TypedQuery<SneakerSupplierDTO> typedQuery = entityManager.createQuery(query, SneakerSupplierDTO.class);
        return typedQuery.getResultList();
    }
}
