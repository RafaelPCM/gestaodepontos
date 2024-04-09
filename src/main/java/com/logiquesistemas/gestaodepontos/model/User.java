package com.logiquesistemas.gestaodepontos.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logiquesistemas.gestaodepontos.enums.UserType;
import com.logiquesistemas.gestaodepontos.enums.WorkdayType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
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

  private WorkdayType workdayType;
  
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Workday> workdays;
}
