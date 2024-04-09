package com.logiquesistemas.gestaodepontos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.logiquesistemas.gestaodepontos.enums.UserType;
import com.logiquesistemas.gestaodepontos.model.User;
import com.logiquesistemas.gestaodepontos.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
      }
      
    @Test
    public void testFindAll_ShouldReturnAllUsers() {
        List<User> mockUsers = Arrays.asList(new User(), new User());

        Mockito.when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> actualUsers = userService.findAll();

        assertEquals(mockUsers, actualUsers);
    }

    @Test
    public void testFindUserByCpf_ShouldReturnUser() {
        String cpf = "54681600059";

        User mockUser = User.builder()
                    .cpf("54681600059")
                    .password("password")
                    .fullname("John Doe")
                    .userType(UserType.COMMON)
                    .build();


        Mockito.when(userRepository.findUserByCpf(cpf)).thenReturn(Optional.of(mockUser));

        Optional<User> actualUser = userService.findUserByCpf(cpf);

        assertTrue(actualUser.isPresent());
        assertEquals(mockUser, actualUser.get());
    }

    @Test
    public void testFindUserByCpf_ShouldReturnEmptyOptional_WhenUserNotFound() {
        String cpf = "12345678900";

        Mockito.when(userRepository.findUserByCpf(cpf)).thenReturn(Optional.empty());

        Optional<User> actualUser = userService.findUserByCpf(cpf);

        assertFalse(actualUser.isPresent());
    }

    @Test
    public void testSave_ShouldSaveUser_WithValidCPF() {
        String cpf = "12013543077";
        String password = "password";
        String fullname = "Jane Doe";
        UserType userType = UserType.COMMON;

        User newUser = User.builder()
                    .cpf("12013543077")
                    .password("password")
                    .fullname("Jane Doe")
                    .userType(UserType.COMMON)
                    .build();

        Mockito.when(userService.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());

        Mockito.verify(userRepository).save(newUser);

    }

    @Test
    public void testSave_ShouldThrowException_WithInvalidCPF() {
        String cpf = "55555555555";
        String password = "password";
        String fullname = "Jane Doe";
        UserType userType = UserType.COMMON;


        User newUser = User.builder()
                    .cpf("55555555555")
                    .password("password")
                    .fullname("Jane Doe")
                    .userType(UserType.COMMON)
                    .build();
                    
        

        Mockito.when(userService.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());

        assertThrows(InvalidDataException.class, () -> userService.save(newUser));

    }

    // PS: vai dar erro pois as senhas sao criptografadas entao vai dar valor diferente.
    @Test
    public void testUpdate_ShouldUpdateUser() {
        Long userId = 1L;
    
        String oldCpf = "26182608056";
        String oldPassword = "old_password";
        String oldFullname = "John Doe";
        UserType oldUserType = UserType.ADMIN;
    
        User existingUser = User.builder()
            .id(userId) // Set ID for the existing user
            .cpf(oldCpf)
            .password(oldPassword)
            .fullname(oldFullname)
            .userType(oldUserType)
            .build();
    
        String newCpf = "26182608056";
        String newPassword = "new_password";
        String encodedPassword = userService.passwordEncoder().encode(newPassword);
        String newFullname = "John Doe Updated";
        UserType newUserType = UserType.ADMIN;
        User updatedUser = User.builder()
            .id(userId)
            .cpf(newCpf)
            .password(encodedPassword)
            .fullname(newFullname)
            .userType(newUserType)
            .build();
    
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        
        assertEquals(updatedUser, existingUser);
    
        Mockito.verify(userRepository).save(updatedUser);
    }
}
