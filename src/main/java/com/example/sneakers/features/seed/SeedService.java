package com.example.sneakers.features.seed;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sneakers.features.authentication.AuthenticationService;
import com.example.sneakers.features.authentication.dtos.RegisterUserDto;
import com.example.sneakers.features.sneaker.entities.Sneaker;
import com.example.sneakers.features.sneaker.repositories.SneakerRepository;
import com.example.sneakers.features.user.Role;
import com.example.sneakers.features.user.UserAccount;
import com.example.sneakers.features.user.UserRepository;

@Service
public class SeedService {
  @Autowired
  private UserRepository userAccountRepository;
  @Autowired
  private SneakerRepository sneakerRepository;
  @Autowired
  private AuthenticationService authenticationService;

  public void seedData() {
    if (userAccountRepository.findByEmail("user@user.com").isEmpty()) {

      var dto = new RegisterUserDto();
      dto.setUsername("user");
      dto.setPassword("user");
      dto.setEmail("user@user.com");

      authenticationService.signup(dto);
    }

    if (userAccountRepository.findByEmail("admin@admin.com").isEmpty()) {

      var dto = new RegisterUserDto();
      dto.setUsername("admin");
      dto.setPassword("admin");
      dto.setEmail("admin@admin.com");

      authenticationService.signup(dto);
    }

    if (userAccountRepository.findByEmail("supplier@supplier.com").isEmpty()) {

      var dto = new RegisterUserDto();
      dto.setUsername("supplier");
      dto.setPassword("supplier");
      dto.setEmail("supplier@supplier.com");

      authenticationService.signup(dto);

      Optional<UserAccount> userAccountOptional = userAccountRepository.findByEmail("supplier@supplier.com");

      if (userAccountOptional.isPresent()) {
        var userAccount = userAccountOptional.get();
        userAccount.setRole(Role.ROLE_SUPPLIER);
        userAccountRepository.save(userAccount);
      }
    }

    Optional<UserAccount> supplier = userAccountRepository.findByEmail("supplier@supplier.com");

    if (supplier.isEmpty()) {
      System.out.println("@@@@@@@@@@@@@@@@ empty");
      return;
    }

    Sneaker sneaker1 = Sneaker.builder().name("Air Max 90").brand("Nike").price(new BigDecimal("9999.99"))
        .description("Classic style sneakers.")
        .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRiNXqeu3QUnVCaVzcBS6qf1k0gZRcZyjr1QQ&s")
        .supplier(supplier.get())
        .build();

    Sneaker sneaker2 = Sneaker.builder().name("Gel Lyte III").brand("Asics").price(new BigDecimal("8999.99"))
        .description("Retro running shoes.")
        .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRvPyLaOfeiLDF4YY7Ob4LqWsrMBIyRSAItQ&s")
        .supplier(supplier.get())
        .build();

    Sneaker sneaker3 = Sneaker.builder().name("Yeezy Boost 350").brand("Adidas").price(new BigDecimal("19999.99"))
        .description("Stylish and comfortable.")
        .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTN1lAygzGaqHl48i3oWWfj-JiatVpnC3-vgQ&s")
        .supplier(supplier.get()).build();

    sneakerRepository.save(sneaker1);
    sneakerRepository.save(sneaker2);
    sneakerRepository.save(sneaker3);

  }
}
