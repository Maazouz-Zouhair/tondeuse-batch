package com.tondeuse.batch.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.tondeuse.model.Tondeuse;

public class TondeuseItemProcessorTest {

    private TondeuseItemProcessor processor = new TondeuseItemProcessor();

    @Test
    public void testProcess() throws Exception {
        Tondeuse tondeuse = new Tondeuse();
        tondeuse.setX(1);
        tondeuse.setY(2);
        tondeuse.setDirection('N');
        tondeuse.setInstructions("GAGAGAGAA");

        Tondeuse processedTondeuse = processor.process(tondeuse);

        assertEquals(1, processedTondeuse.getX());
        assertEquals(3, processedTondeuse.getY());
        assertEquals('N', processedTondeuse.getDirection());
    }
}
