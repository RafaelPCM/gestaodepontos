package com.logiquesistemas.gpjt.controller;

import com.logiquesistemas.gpjt.enums.UserType;
import com.logiquesistemas.gpjt.model.User;
import com.logiquesistemas.gpjt.security.UserPrincipal;
import com.logiquesistemas.gpjt.service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  public List<User> getAllUsers() {
    return userService.findAll();
  }

  @GetMapping("/{cpf}")
  public Optional<User> getUserByUsername(@PathVariable String cpf) {
    return userService.findUserByCpf(cpf);
  }

  @PostMapping
  public User registerUser(@RequestBody User user) {
    // Access authenticated user details from SecurityContextHolder
    Authentication authentication = SecurityContextHolder
      .getContext()
      .getAuthentication();
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

    // Verifica se o usuário logado é um administrador
    if (userPrincipal.getUserType().equals(UserType.ADMIN)) {
      return userService.save(user);
    } else {
      throw new UnauthorizedUserException(
        "Apenas administradores podem cadastrar usuários comuns."
      );
    }
  }

  @PutMapping("/{id}")
  public User updateUser(@PathVariable Long id, @RequestBody User user) {
    return userService.update(id, user);
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    userService.delete(id);
  }
}
