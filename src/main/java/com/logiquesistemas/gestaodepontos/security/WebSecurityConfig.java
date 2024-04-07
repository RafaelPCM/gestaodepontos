package com.logiquesistemas.gestaodepontos.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.logiquesistemas.gestaodepontos.enums.UserType;
import com.logiquesistemas.gestaodepontos.model.User;
import com.logiquesistemas.gestaodepontos.repository.UserRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
// @EnableGlobalMethodSecurity(prePostEnabled = true) // Enable method-level security annotations
public class WebSecurityConfig {

  private final CustomBasicAuthenticationFilter customBasicAuthenticationFilter;

  @Autowired
  private UserRepository userRepository;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // Cria um usuario administrador quando a aplicacao e iniciada (se ainda nao existir)
    User adminUser = userRepository.findByCpf("00000000000");
    if (adminUser == null) {
      PasswordEncoder passwordEncoder = passwordEncoder();
      adminUser = new User();
      adminUser.setCpf("00000000000");
      adminUser.setPassword(passwordEncoder.encode("123"));
      adminUser.setUserType(UserType.ADMIN);
      adminUser.setFullname("Admin User");
      userRepository.save(adminUser);
      System.out.println("Usuário administrador criado com sucesso.");
      System.out.println(
        adminUser.getPassword() + " ---------> Senha Criptografada"
      );
      System.out.println("Usuário(cpf) ---------> 00000000000");
      System.out.println("Senha ---------> 123");
    }

    User commonUser = userRepository.findByCpf("12312312312");
    if (commonUser == null) {
      PasswordEncoder passwordEncoder = passwordEncoder();
      commonUser = new User();
      commonUser.setCpf("12312312312");
      commonUser.setPassword(passwordEncoder.encode("123"));
      commonUser.setUserType(UserType.COMMON);
      commonUser.setFullname("Common User");
      userRepository.save(commonUser);
      System.out.println("Usuário administrador criado com sucesso.");
      System.out.println(
        commonUser.getPassword() + " ---------> Senha Criptografada"
      );
      System.out.println("Usuário(cpf) ---------> 12312312312");
      System.out.println("Senha ---------> 123");
    }

    return http
      .csrf(csrf -> csrf.disable())
      .sessionManagement(session ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .authorizeHttpRequests(request ->
        request
          .requestMatchers(HttpMethod.POST, "/user/**")
          .permitAll()
          .anyRequest()
          .authenticated()
      )
      .addFilterBefore(
        customBasicAuthenticationFilter,
        UsernamePasswordAuthenticationFilter.class
      )
      .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
