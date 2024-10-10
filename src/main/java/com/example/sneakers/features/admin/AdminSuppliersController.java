package com.example.sneakers.features.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.sneakers.features.user.UserService;

@Controller
@RequestMapping("/admin/suppliers")
public class AdminSuppliersController {

  @Autowired
  private UserService usersService;

  @GetMapping
  public String index(Model model) {
    var suppliers = usersService.getSuppliers();

    model.addAttribute("suppliers", suppliers);

    return "admin/suppliers/index";
  }
}
