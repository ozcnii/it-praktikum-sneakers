package com.example.sneakers.features.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {
  Optional<UserAccount> findByUsername(String username);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  Optional<UserAccount> findByEmail(String email);
}