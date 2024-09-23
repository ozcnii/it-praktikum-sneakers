package com.example.sneakers.features.sneaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sneakers.features.sneaker.dtos.SneakerSupplierDTO;

import io.swagger.annotations.Api;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Api(value = "User Controller", description = "Operations pertaining to users")
public class SneakerController {

    @Autowired
    private SneakerService sneakerService;

    @GetMapping("/sneakers")
    public List<SneakerSupplierDTO> getAllSneakersWithSuppliers() {
        return sneakerService.getAllSneakersWithSuppliers();
    }
}
