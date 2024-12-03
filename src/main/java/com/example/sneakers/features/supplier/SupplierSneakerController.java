package com.example.sneakers.features.supplier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sneakers.features.sneaker.entities.Sneaker;
import com.example.sneakers.features.sneaker.repositories.SneakerRepository;
import com.example.sneakers.features.user.UserAccount;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/supplier/sneakers")
public class SupplierSneakerController {

  @Autowired
  private SneakerRepository sneakerRepository;

  @GetMapping
  public String getSneakers(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return "redirect:/login";
    }

    UserAccount currentUser = (UserAccount) authentication.getPrincipal();

    List<Sneaker> sneakers = sneakerRepository.findBySupplier(currentUser);
    model.addAttribute("sneakers", sneakers);
    return "supplier/sneakers_list";
  }

  @GetMapping("/create")
  public String showCreateSneakerForm(Model model) {
    model.addAttribute("sneaker", new Sneaker());
    return "supplier/create_sneaker";
  }

  @PostMapping("/create")
  public String createSneaker(@ModelAttribute Sneaker sneaker) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return "redirect:/login";
    }

    UserAccount currentUser = (UserAccount) authentication.getPrincipal();

    sneaker.setSupplier(currentUser);
    sneakerRepository.save(sneaker);
    return "redirect:/supplier/sneakers/create?success";
  }

  @GetMapping("/edit/{id}")
  public String showEditSneakerForm(@PathVariable Long id, Model model) {
    Optional<Sneaker> sneakerOptional = sneakerRepository.findById(id);
    if (sneakerOptional.isPresent()) {
      model.addAttribute("sneaker", sneakerOptional.get());
      return "supplier/edit_sneaker";
    }
    return "redirect:/supplier/sneakers?error";
  }

  @PostMapping("/edit/{id}")
  public String editSneaker(@PathVariable Long id, @Valid @ModelAttribute Sneaker sneaker, BindingResult result) {
    if (result.hasErrors()) {
      return "supplier/edit_sneaker";
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return "redirect:/login";
    }

    UserAccount currentUser = (UserAccount) authentication.getPrincipal();

    sneaker.setSupplier(currentUser);
    sneaker.setId(id);
    sneakerRepository.save(sneaker);

    return "redirect:/supplier/sneakers?success";
  }

  @GetMapping("/delete/{id}")
  public String deleteSneaker(@PathVariable Long id) {
    sneakerRepository.deleteById(id);
    return "redirect:/supplier/sneakers?deleted";
  }
}