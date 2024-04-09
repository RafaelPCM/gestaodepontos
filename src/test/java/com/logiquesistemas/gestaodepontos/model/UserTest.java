package com.logiquesistemas.gestaodepontos.model;

import static org.junit.jupiter.api.Assertions.assertEquals;


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
        User user1 = User.builder()
                .id(1L)
                .cpf("12345678900")
                .fullname("John Doe")
                .userType(UserType.ADMIN)
                .workdayType(WorkdayType.EIGHT_HOUR_WITH_BREAK)
                .build();
        
        User user2 = User.builder()
                .id(1L)
                .cpf("12345678900")
                .fullname("John Doe")
                .userType(UserType.ADMIN)
                .workdayType(WorkdayType.EIGHT_HOUR_WITH_BREAK)
                .build();
        
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }
    
}
