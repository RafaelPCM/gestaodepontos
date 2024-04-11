package com.logiquesistemas.gestaodepontos.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.logiquesistemas.gestaodepontos.enums.WorkdayType;

@ExtendWith(MockitoExtension.class)
public class WorkdayTypeTest {

    @Test
    public void testProperties() {
        WorkdayType workdayType = WorkdayType.SIX_HOUR_CONTINUOUS;
        
        assertEquals("6 horas contínuas", workdayType.getDescription());
        assertEquals(6, workdayType.getTotalWorkHours());
        assertEquals(0, workdayType.getLunchBreakDuration());
    }
}

