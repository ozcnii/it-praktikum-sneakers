package com.example.sneakers.features.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sneakers.features.sneaker.SneakerService;
import com.example.sneakers.features.sneaker.dtos.SneakerSupplierDTO;
import com.example.sneakers.features.user.UserAccount;

@Controller
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  private SneakerService sneakerService;

  @GetMapping
  public String index(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return "redirect:/login";
    }

    UserAccount currentUser = (UserAccount) authentication.getPrincipal();

    model.addAttribute("username", currentUser.getUsername());
    return "/admin/index";
  }

  @GetMapping("/sneakers")
  public List<SneakerSupplierDTO> getAllSneakersWithSuppliers() {
    return sneakerService.getAllSneakersWithSuppliers();
  }
}
