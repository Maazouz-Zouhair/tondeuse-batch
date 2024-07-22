package com.tondeuse.batch.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.tondeuse.model.Tondeuse;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class TondeuseItemReaderTest {

    @Autowired
    private TondeuseItemReader tondeuseItemReader;

    @Value("${tondeuse.input.file}")
    private Resource inputFile;

    @BeforeEach
    public void setUp() {
        tondeuseItemReader.init();
    }

    @Test
    public void testRead() throws Exception {
        Tondeuse tondeuse1 = tondeuseItemReader.read();
        assertNotNull(tondeuse1);
        assertEquals(1, tondeuse1.getX());
        assertEquals(2, tondeuse1.getY());
        assertEquals('N', tondeuse1.getDirection());
        assertEquals("GAGAGAGAA", tondeuse1.getInstructions());

        Tondeuse tondeuse2 = tondeuseItemReader.read();
        assertNotNull(tondeuse2);
        assertEquals(3, tondeuse2.getX());
        assertEquals(3, tondeuse2.getY());
        assertEquals('E', tondeuse2.getDirection());
        assertEquals("AADAADADDA", tondeuse2.getInstructions());

        Tondeuse tondeuse3 = tondeuseItemReader.read();
        assertNull(tondeuse3); // No more tondeuses to read
    }
}
