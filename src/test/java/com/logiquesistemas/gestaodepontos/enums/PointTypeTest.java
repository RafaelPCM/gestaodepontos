package com.logiquesistemas.gestaodepontos.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PointTypeTest {

    @Test
    public void testDescriptionGetter() {
        PointType entry = PointType.ENTRY;
        PointType exit = PointType.EXIT;

        assertEquals("Entrada", entry.getDescription());
        assertEquals("Saida", exit.getDescription());
    }
}