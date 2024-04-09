package com.logiquesistemas.gestaodepontos.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.logiquesistemas.gestaodepontos.enums.UserType;
import com.logiquesistemas.gestaodepontos.model.User;
import com.logiquesistemas.gestaodepontos.repository.UserRepository;
import com.logiquesistemas.gestaodepontos.security.UserPrincipal;
// import com.logiquesistemas.gestaodepontos.service.CPFValidationService;
import com.logiquesistemas.gestaodepontos.service.UserService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserControllerTest {

  @Autowired
  private UserController userController;

  @Autowired
  private UserService userService;

  @MockBean
  private UserRepository userRepository;

  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Test
  public void testGetAllUsers_ShouldReturnAllUsers() {
    List<User> mockUsers = Arrays.asList(new User(), new User());

    Mockito.when(userRepository.findAll()).thenReturn(mockUsers);

    List<User> actualUsers = userController.getAllUsers();

    assertEquals(mockUsers, actualUsers);
  }

  @Test
  public void testGetUserByCpf_ShouldReturnUser() {
    String cpf = "54681600059";
    User mockUser = User
      .builder()
      .cpf("54681600059")
      .password("password")
      .fullname("John Doe")
      .userType(UserType.COMMON)
      .build();

    Mockito
      .when(userRepository.findUserByCpf(cpf))
      .thenReturn(Optional.of(mockUser));

    Optional<User> actualUser = userController.getUserByCpf(cpf);

    assertTrue(actualUser.isPresent());
    assertEquals(mockUser, actualUser.get());
  }

  @Test
  public void testGetUserByCpf_ShouldReturnEmptyOptional_WhenUserNotFound() {
    String cpf = "54681600059";

    Mockito
      .when(userRepository.findUserByCpf(cpf))
      .thenReturn(Optional.empty());

    Optional<User> actualUser = userController.getUserByCpf(cpf);

    assertFalse(actualUser.isPresent());
  }

  @Test
  public void testRegisterUser_ShouldRegisterUser_WhenAdmin() {
    User admin = User.builder()
        .cpf("54681600059")
        .password("password")
        .fullname("Billy Doe")
        .userType(UserType.ADMIN)
        .build();
    User newUser = User.builder()
        .cpf("33687090035")
        .password("password")
        .fullname("John Doe")
        .userType(UserType.COMMON)
        .build();
  

    Authentication mockAuthentication = Mockito.mock(Authentication.class);
    UserPrincipal mockPrincipal = Mockito.mock(UserPrincipal.class);
    Mockito.when(mockPrincipal.getUserType()).thenReturn(UserType.ADMIN);
    Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockPrincipal);
    SecurityContextHolder.getContext().setAuthentication(mockAuthentication);
  
    Mockito.when(userService.save(newUser)).thenReturn(newUser);
  
    User registeredUser = userController.registerUser(newUser);
  
    assertEquals(newUser, registeredUser);
  }

  @Test
  public void testRegisterUser_ShouldThrowException_WhenNotAdmin() {
    // String userCpf = "33687090035";
    User newUser = User
      .builder()
      .cpf("33687090035")
      .password("password")
      .fullname("John Doe")
      .userType(UserType.COMMON)
      .build();

    // String cpfuser = "98765432100";
    User user = User
      .builder()
      .cpf("98765432100")
      .password("password")
      .fullname("John Doe")
      .userType(UserType.COMMON)
      .build();

    Authentication mockAuthentication = Mockito.mock(Authentication.class);
    UserPrincipal mockPrincipal = Mockito.mock(UserPrincipal.class);
    Mockito.when(mockPrincipal.getUsername()).thenReturn(user.getCpf());
    Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockPrincipal);
    SecurityContextHolder.getContext().setAuthentication(mockAuthentication);

    assertThrows(UnauthorizedUserException.class, () -> userController.registerUser(newUser));

  } 

}
