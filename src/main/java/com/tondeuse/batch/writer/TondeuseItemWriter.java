package com.tondeuse.batch.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.tondeuse.model.Tondeuse;

import java.util.List;

@Component
public class TondeuseItemWriter implements ItemWriter<Tondeuse> {

    private static final Logger logger = LoggerFactory.getLogger(TondeuseItemWriter.class);

    @Override
    public void write(List<? extends Tondeuse> items) throws Exception {
        for (Tondeuse tondeuse : items) {
            logger.debug("Writing tondeuse: x={}, y={}, direction={}", tondeuse.getX(), tondeuse.getY(), tondeuse.getDirection());

            System.out.println(tondeuse.getX() + " " + tondeuse.getY() + " " + tondeuse.getDirection());
        }
    }
}
