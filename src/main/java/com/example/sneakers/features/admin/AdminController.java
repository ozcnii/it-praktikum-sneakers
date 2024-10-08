package com.example.sneakers.features.admin;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sneakers.features.user.UserAccount;

@Controller
@RequestMapping("/admin")
public class AdminController {

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
}