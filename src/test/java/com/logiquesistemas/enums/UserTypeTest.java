package com.logiquesistemas.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.logiquesistemas.gestaodepontos.enums.UserType;

@ExtendWith(MockitoExtension.class)
public class UserTypeTest {

    @Test
    public void testDescriptionGetter() {
        UserType adminType = UserType.ADMIN;
        String expectedDescription = "Administrador";
        
        assertEquals(expectedDescription, adminType.getDescription());
    }
}
