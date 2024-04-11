package com.logiquesistemas.gestaodepontos.model;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.logiquesistemas.gestaodepontos.enums.UserType;
import com.logiquesistemas.gestaodepontos.enums.WorkdayType;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    private Workday workday;
    
    @Test
    public void testEqualsHashCode() {

        Long id = 1L;
        String cpf = "12345678900";
        String password = "password123";
        String fullName = "John Doe";
        UserType userType = UserType.ADMIN;
        WorkdayType workdayType = WorkdayType.EIGHT_HOUR_WITH_BREAK;


        User user1 = User.builder()
                .id(1L)
                .cpf("12345678900")
                .fullName("John Doe")
                .userType(UserType.ADMIN)
                .workdayType(WorkdayType.EIGHT_HOUR_WITH_BREAK)
                .build();
        
        User user2 = User.builder()
                .id(1L)
                .cpf("12345678900")
                .fullName("John Doe")
                .userType(UserType.ADMIN)
                .workdayType(WorkdayType.EIGHT_HOUR_WITH_BREAK)
                .build();
        
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotNull(user1);
        assertEquals(id, user1.getId());
        assertEquals(cpf, user1.getCpf());
        assertEquals(fullName, user1.getFullName());
        assertEquals(userType, user1.getUserType());
        assertEquals(workdayType, user1.getWorkdayType());
    }
    
}
