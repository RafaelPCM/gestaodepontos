package com.logiquesistemas.gestaodepontos.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

import com.logiquesistemas.gestaodepontos.enums.UserType;

import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "cpf")
  private String cpf;

  @Column(name = "password")
  private String password;

  @Column(name = "fullname")
  private String fullname;

  private UserType userType;
  // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  // private List<Workday> workdays;
}
