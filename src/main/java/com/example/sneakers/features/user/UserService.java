package com.example.sneakers.features.user;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<UserAccount> getAllUsers() {
    return userRepository.findAll();
  }

  public UserAccount getUserById(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  public UserAccount saveUser(UserAccount user) {
    return userRepository.save(user);
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  public List<UserAccount> getSuppliers() {
    return userRepository.findAllByRole(Role.ROLE_SUPPLIER);
  }
}