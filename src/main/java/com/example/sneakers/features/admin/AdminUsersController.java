package com.example.sneakers.features.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sneakers.features.user.Role;
import com.example.sneakers.features.user.UserAccount;
import com.example.sneakers.features.user.UserService;

@Controller
@RequestMapping("/admin/users")
public class AdminUsersController {
  @Autowired
  private UserService userService;

  @GetMapping
  public String listUsers(Model model) {
    List<UserAccount> users = userService.getAllUsers();
    model.addAttribute("users", users);
    return "admin/users/users";
  }

  @GetMapping("/edit/{id}")
  public String editUser(@PathVariable Long id, Model model) {
    UserAccount user = userService.getUserById(id);
    model.addAttribute("user", user);
    model.addAttribute("roles", Role.values());
    return "admin/users/edit-user";
  }

  @PostMapping("/save")
  public String saveUser(@ModelAttribute UserAccount user) {
    userService.saveUser(user);
    return "redirect:/admin/users";
  }

  @GetMapping("/delete/{id}")
  public String deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return "redirect:/admin/users";
  }
}