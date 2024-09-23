package com.example.sneakers.features.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sneakers.features.authentication.dtos.LoginUserDto;
import com.example.sneakers.features.authentication.dtos.RegisterUserDto;
import com.example.sneakers.features.user.UserAccount;
import com.example.sneakers.features.user.UserRepository;
import com.example.sneakers.features.user.exceptions.UserAlreadyExistsException;

@Service
public class AuthenticationService {
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  public AuthenticationService(
      UserRepository userRepository,
      AuthenticationManager authenticationManager,
      PasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public UserAccount signup(RegisterUserDto input) {

    if (userRepository.findByUsername(input.getUsername()).isPresent()) {
      throw new UserAlreadyExistsException("Username already exists");
    }

    if (input.getEmail() != null && userRepository.findByEmail(input.getEmail()).isPresent()) {
      throw new UserAlreadyExistsException("Email already exists");
    }

    UserAccount user = new UserAccount();
    user.setUsername(input.getUsername());
    user.setEmail(input.getEmail());
    user.setPassword(passwordEncoder.encode(input.getPassword()));
    return userRepository.save(user);
  }

  public UserAccount authenticate(LoginUserDto input) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            input.getUsername(),
            input.getPassword()));

    return userRepository.findByUsername(input.getUsername())
        .orElseThrow();
  }
}
