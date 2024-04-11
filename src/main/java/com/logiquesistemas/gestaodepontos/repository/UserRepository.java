package com.logiquesistemas.gestaodepontos.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logiquesistemas.gestaodepontos.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findUserByCpf(String cpf);

  User findByCpf(String cpf);
}
