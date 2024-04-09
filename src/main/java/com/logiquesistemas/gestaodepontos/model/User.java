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
import java.util.Objects;


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

  

  @Override
  public int hashCode() {
      return Objects.hash(id, cpf, password, fullname, userType, workdayType);
  }

  @Override
  public boolean equals(Object o) {
      if (o == this) return true;
      if (!(o instanceof User)) {
          return false;
      }
      User other = (User) o;
      return Objects.equals(id, other.id) &&
            Objects.equals(cpf, other.cpf) &&
            Objects.equals(password, other.password) &&
            Objects.equals(fullname, other.fullname) &&
            Objects.equals(userType, other.userType) &&
            Objects.equals(workdayType, other.workdayType);
  }
}
