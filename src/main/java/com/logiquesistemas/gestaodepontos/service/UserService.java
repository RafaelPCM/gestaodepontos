package com.logiquesistemas.gestaodepontos.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.logiquesistemas.gestaodepontos.enums.UserType;
import com.logiquesistemas.gestaodepontos.model.User;
import com.logiquesistemas.gestaodepontos.repository.UserRepository;
import com.logiquesistemas.gestaodepontos.utils.ValidarCPF;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public Optional<User> findUserByCpf(String cpf) {
    return userRepository.findUserByCpf(cpf);
  }

  public User save(User user) {
    PasswordEncoder passwordEncoder = passwordEncoder();
    if (ValidarCPF.iscpf(user.getCpf())) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      return userRepository.save(user);
    } else {
      throw new InvalidDataException("CPF inválido");
    }
    
  }

  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  public User update(Long id, User user) {
    User existingUser = userRepository
      .findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado")
      );
    PasswordEncoder passwordEncoder = passwordEncoder();

    if (ValidarCPF.iscpf(user.getCpf())) {
      existingUser.setCpf(user.getCpf());
      existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
      existingUser.setFullname(user.getFullname());
      existingUser.setUserType(user.getUserType());
      existingUser.setWorkdays(user.getWorkdays());
      return userRepository.save(existingUser);
    } else {
      throw new InvalidDataException("CPF inválido");
    }
  }

  public void delete(Long id) {
    User user = userRepository
      .findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado")
      );
    userRepository.delete(user);
  }

  public boolean isAdmin(Long userId) {
    User user = userRepository.findById(userId).orElse(null);
    return user != null && user.getUserType() == UserType.ADMIN;
  }
}
