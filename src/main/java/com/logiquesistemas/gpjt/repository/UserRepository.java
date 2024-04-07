package com.logiquesistemas.gpjt.repository;

import com.logiquesistemas.gpjt.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findUserByCpf(String cpf);

  User findByCpf(String cpf);
}
