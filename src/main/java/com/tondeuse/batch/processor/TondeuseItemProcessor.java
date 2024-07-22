package com.tondeuse.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.tondeuse.model.Tondeuse;

@Component
public class TondeuseItemProcessor implements ItemProcessor<Tondeuse, Tondeuse> {

    private static final Logger logger = LoggerFactory.getLogger(TondeuseItemProcessor.class);

    @Override
    public Tondeuse process(Tondeuse tondeuse) throws Exception {
    	logger.debug("Processing tondeuse: x={}, y={}, direction={}",
                tondeuse.getX(), tondeuse.getY(), tondeuse.getDirection());
        for (char instruction : tondeuse.getInstructions().toCharArray()) {
            switch (instruction) {
                case 'G':
                    turnLeft(tondeuse);
                    break;
                case 'D':
                    turnRight(tondeuse);
                    break;
                case 'A':
                    moveForward(tondeuse);
                    break;
            }
        }
        
        logger.debug("Processed tondeuse: x={}, y={}, direction={}",
                tondeuse.getX(), tondeuse.getY(), tondeuse.getDirection());
        
        return tondeuse;
    }

    private void turnLeft(Tondeuse tondeuse) {
        switch (tondeuse.getDirection()) {
            case 'N':
                tondeuse.setDirection('W');
                break;
            case 'E':
                tondeuse.setDirection('N');
                break;
            case 'S':
                tondeuse.setDirection('E');
                break;
            case 'W':
                tondeuse.setDirection('S');
                break;
        }
        
        logger.debug("Turned left: new direction={}", tondeuse.getDirection());
    }

    private void turnRight(Tondeuse tondeuse) {
        switch (tondeuse.getDirection()) {
            case 'N':
                tondeuse.setDirection('E');
                break;
            case 'E':
                tondeuse.setDirection('S');
                break;
            case 'S':
                tondeuse.setDirection('W');
                break;
            case 'W':
                tondeuse.setDirection('N');
                break;
        }
        logger.debug("Turned right: new direction={}", tondeuse.getDirection());
    }

    private void moveForward(Tondeuse tondeuse) {
        switch (tondeuse.getDirection()) {
            case 'N':
                tondeuse.setY(tondeuse.getY() + 1);
                break;
            case 'E':
                tondeuse.setX(tondeuse.getX() + 1);
                break;
            case 'S':
                tondeuse.setY(tondeuse.getY() - 1);
                break;
            case 'W':
                tondeuse.setX(tondeuse.getX() - 1);
                break;
        }
        logger.debug("Moved forward: new position x={}, y={}", tondeuse.getX(), tondeuse.getY());
    }
}
