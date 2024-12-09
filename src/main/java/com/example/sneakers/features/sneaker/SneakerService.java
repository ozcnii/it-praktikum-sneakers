package com.example.sneakers.features.sneaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sneakers.features.sneaker.dtos.SneakerSupplierDTO;
import com.example.sneakers.features.sneaker.entities.Sneaker;
import com.example.sneakers.features.sneaker.repositories.SneakerRepository;

import java.util.List;

@Service
public class SneakerService {

    @Autowired
    private SneakerRepository sneakerRepository;

    public List<SneakerSupplierDTO> getAllSneakersWithSuppliers() {
        return sneakerRepository.findAllSneakersWithSuppliers();
    }

    public List<Sneaker> findAll() {
        return sneakerRepository.findAll();
    }
}
