package com.example.sneakers.features.authentication;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sneakers.features.authentication.dtos.LoginUserDto;
import com.example.sneakers.features.authentication.dtos.RegisterUserDto;
import com.example.sneakers.features.user.UserAccount;
import com.example.sneakers.features.user.exceptions.UserAlreadyExistsException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RequestMapping("/auth/web")
@Controller
public class AuthenticationWebController {
  private final JwtService jwtService;

  private final AuthenticationService authenticationService;

  public AuthenticationWebController(JwtService jwtService, AuthenticationService authenticationService) {
    this.jwtService = jwtService;
    this.authenticationService = authenticationService;
  }

  @GetMapping("/register")
  public String registerForm(Model model) {
    model.addAttribute("user", new UserAccount());
    return "/auth/register";
  }

  @PostMapping("/register")
  public String register(@Valid RegisterUserDto registerUserDto, BindingResult bindingResult,
      RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {

      StringBuilder errorMessage = new StringBuilder("Registration failed: ");
      for (FieldError error : bindingResult.getFieldErrors()) {
        errorMessage.append(error.getField()).append(" ").append(error.getDefaultMessage()).append(". ");
      }
      redirectAttributes.addFlashAttribute("error", errorMessage.toString());
      return "redirect:/auth/web/register";
    }

    try {
      authenticationService.signup(registerUserDto);
      redirectAttributes.addFlashAttribute("success", "Registration successful! Please log in.");
      return "redirect:/auth/web/login";
    } catch (UserAlreadyExistsException e) {
      redirectAttributes.addFlashAttribute("error", e.getMessage());
      return "redirect:/auth/web/register";
    } catch (Exception e) {
      e.printStackTrace();
      redirectAttributes.addFlashAttribute("error", "An unexpected error occurred. Please try again.");
      return "redirect:/auth/web/register";
    }
  }

  @GetMapping("/login")
  public String loginForm() {
    return "/auth/login";
  }

  @PostMapping("/login")
  public String login(@Valid LoginUserDto loginUserDto, BindingResult bindingResult,
      RedirectAttributes redirectAttributes, HttpServletResponse response) {

    if (bindingResult.hasErrors()) {
      StringBuilder errorMessages = new StringBuilder("Validation failed: ");
      bindingResult.getFieldErrors().forEach(
          error -> errorMessages.append(error.getField()).append(" ").append(error.getDefaultMessage()).append("; "));
      redirectAttributes.addFlashAttribute("error", errorMessages.toString());
      return "redirect:/auth/web/login";
    }

    try {
      UserAccount authenticatedUser = authenticationService.authenticate(loginUserDto);
      String jwtToken = jwtService.generateToken(authenticatedUser);

      ResponseCookie cookie = ResponseCookie.from("authToken", jwtToken)
          .httpOnly(true)
          .path("/")
          .maxAge(24 * 60 * 60)
          .build();

      response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

      if (authenticatedUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
        return "redirect:/admin/index";
      } else if (authenticatedUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_SUPPLIER"))) {
        return "redirect:/supplier/index";
      }

      return "redirect:/users";
    } catch (AuthenticationException e) {
      redirectAttributes.addFlashAttribute("error", "Invalid login: " + e.getMessage());
      return "redirect:/auth/web/login";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", "An unexpected error occurred. Please try again later.");
      return "redirect:/auth/web/login";
    }
  }
}
