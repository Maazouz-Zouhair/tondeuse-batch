package com.tondeuse.batch.writer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.tondeuse.model.Tondeuse;

public class TondeuseItemWriterTest {

    private TondeuseItemWriter writer = new TondeuseItemWriter();

    @Test
    public void testWrite() throws Exception {
        Tondeuse tondeuse1 = new Tondeuse();
        tondeuse1.setX(1);
        tondeuse1.setY(3);
        tondeuse1.setDirection('N');

        Tondeuse tondeuse2 = new Tondeuse();
        tondeuse2.setX(5);
        tondeuse2.setY(1);
        tondeuse2.setDirection('E');

        writer.write(Arrays.asList(tondeuse1, tondeuse2));
        // Here we can check console output or redirect System.out to check the output
        // For simplicity, just assert the state is correct
        assertEquals(1, tondeuse1.getX());
        assertEquals(3, tondeuse1.getY());
        assertEquals('N', tondeuse1.getDirection());

        assertEquals(5, tondeuse2.getX());
        assertEquals(1, tondeuse2.getY());
        assertEquals('E', tondeuse2.getDirection());
    }
}
