package com.example.sneakers.features.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sneakers.features.supplier.SupplierRequestService;
import com.example.sneakers.features.supplier.entities.SupplierRequest;

@RequestMapping("/user")
@Controller
public class UserController {

  @Autowired
  SupplierRequestService supplierRequestService;

  @GetMapping("/me")
  public ResponseEntity<UserAccount> authenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    UserAccount currentUser = (UserAccount) authentication.getPrincipal();
    return ResponseEntity.ok(currentUser);
  }

  @GetMapping
  public String index(Model model) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return "redirect:/login";
    }

    UserAccount currentUser = (UserAccount) authentication.getPrincipal();

    SupplierRequest supplierRequest = supplierRequestService.getRequest(currentUser.getId());

    model.addAttribute("username", currentUser.getUsername());
    model.addAttribute("status", supplierRequest.getStatus());

    return "/user/index";
  }

  @GetMapping("/supplier-request")
  public String getMethodName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return "redirect:/login";
    }

    UserAccount currentUser = (UserAccount) authentication.getPrincipal();

    supplierRequestService.saveRequest(currentUser.getId());

    return "redirect:/user";
  }
}
