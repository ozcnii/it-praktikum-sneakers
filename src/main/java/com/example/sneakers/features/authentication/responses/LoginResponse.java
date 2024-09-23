package com.example.sneakers.features.authentication.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
  private String token;

  private long expiresIn;
}