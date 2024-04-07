package com.logiquesistemas.gpjt.service;

import com.logiquesistemas.gpjt.enums.UserType;
import com.logiquesistemas.gpjt.model.User;
import com.logiquesistemas.gpjt.repository.UserRepository;
import com.logiquesistemas.gpjt.utils.ValidarCPF;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  public User update(Long id, User user) {
    User existingUser = userRepository
      .findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado")
      ); //new ResourceNotFoundException("User", "id", id));
    PasswordEncoder passwordEncoder = passwordEncoder();

    if (ValidarCPF.iscpf(user.getCpf())) {
      existingUser.setCpf(user.getCpf());
      existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
      existingUser.setFullname(user.getFullname());
      existingUser.setUserType(user.getUserType());
      // existingUser.setWorkdays(user.getWorkdays());
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
