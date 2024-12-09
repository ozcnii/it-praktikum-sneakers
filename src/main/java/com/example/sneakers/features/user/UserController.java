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

import com.example.sneakers.features.supplier.SupplierRequestService;
import com.example.sneakers.features.supplier.entities.RequestStatus;
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

    if (supplierRequest == null) {
      model.addAttribute("status", null);
    } else {
      var status = supplierRequest.getStatus();
      var statusMessage = "Завяка на роль поставщика: ";

      if (status == RequestStatus.APPROVED) {
        statusMessage += "в ожидании подтверждения";
      } else if (status == RequestStatus.REJECTED) {
        statusMessage += "отклонено";
      } else if (status == RequestStatus.PENDING) {
        statusMessage += "в ожидании";
      } else {
        statusMessage += "?";
      }

      model.addAttribute("status", statusMessage);
    }

    model.addAttribute("username", currentUser.getUsername());

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
