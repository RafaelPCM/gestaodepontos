package com.logiquesistemas.gestaodepontos.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WorkdayTest {

    @Mock
    private User user;
    
    @Test
    public void testSetterAndGetters() {
        Workday workday = new Workday();
        workday.setUser(user);
        
        assertEquals(user, workday.getUser());
    }
}