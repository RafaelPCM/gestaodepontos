package com.logiquesistemas.gestaodepontos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logiquesistemas.gestaodepontos.model.LoginRequest;
import com.logiquesistemas.gestaodepontos.model.User;
import com.logiquesistemas.gestaodepontos.repository.UserRepository;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {

  @Autowired
  private UserRepository userRepository;

  @PostMapping
  public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
    String cpf = loginRequest.getCpf();
    String password = loginRequest.getPassword();

    // Busca o usuário pelo CPF no banco de dados
    User user = userRepository.findByCpf(cpf);

    if (user == null) {
      return new ResponseEntity<>("User does not exist", HttpStatus.UNAUTHORIZED);
    }

    // Verifica se a senha fornecida corresponde à senha armazenada
    if (passwordEncoder().matches(password, user.getPassword())) {
      return new ResponseEntity<>("Login successful!", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
    }
  }

  // Método para obter o codificador de senha
  private PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
