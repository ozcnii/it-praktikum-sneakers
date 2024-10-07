package com.example.sneakers.features.admin;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

  @GetMapping
  public String index(Model model) {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    String username = ((UserDetails) principal).getUsername();

    model.addAttribute("username", username);
    return "/admin/index";
  }
}