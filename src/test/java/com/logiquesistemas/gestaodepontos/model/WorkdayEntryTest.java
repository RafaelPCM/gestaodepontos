package com.logiquesistemas.gestaodepontos.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WorkdayEntryTest {

    @Mock
    private Workday workday;
    
    @Test
    public void testSetterAndGetters() {
        WorkdayEntry entry = new WorkdayEntry();
        LocalDateTime dateTime = LocalDateTime.now();
        entry.setWorkday(workday);
        entry.setDateTimeRecordEntry(dateTime);
        
        assertEquals(workday, entry.getWorkday());
        assertEquals(dateTime, entry.getDateTimeRecordEntry());
    }
}
