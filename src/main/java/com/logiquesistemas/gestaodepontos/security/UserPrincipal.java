package com.logiquesistemas.gestaodepontos.security;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.logiquesistemas.gestaodepontos.enums.UserType;
import com.logiquesistemas.gestaodepontos.model.User;

@Getter
public class UserPrincipal {

  private String cpf;
  private String password;
  private UserType userType;

  private Collection<? extends GrantedAuthority> authorities;

  private UserPrincipal(User user) {
    this.cpf = user.getCpf();
    this.password = user.getPassword();
    this.userType = user.getUserType();

    // Caso não queira que o UserType seja Enum e sim uma class
    // this.authorities = user.getUserType().stream().map(role -> {
    //     return new SimpleGrantedAuthority("ROLE_".concat(role.getName()));
    // }).collect(Collectors.toList());

    this.authorities =
      Stream
        .of(user.getUserType()) // Stream directly from the enum value
        .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.name())))
        .collect(Collectors.toList());
  }

  public static UserPrincipal create(User user) {
    return new UserPrincipal(user);
  }
}
