package com.example.sneakers.features.authentication.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
  @NotBlank(message = "Username is required")
  @NotNull
  @Email
  private String email;

  @NotBlank(message = "Username is required")
  @NotNull
  private String password;

  @NotBlank(message = "Username is required")
  @NotNull
  private String username;
}