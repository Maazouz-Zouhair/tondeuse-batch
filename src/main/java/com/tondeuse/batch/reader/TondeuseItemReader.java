package com.tondeuse.batch.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.tondeuse.model.Pelouse;
import com.tondeuse.model.Tondeuse;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class TondeuseItemReader implements ItemReader<Tondeuse> {

    @Value("${tondeuse.input.file}")
    private Resource inputFile;

    private BufferedReader reader;
    private Pelouse pelouse;
    private static final Logger logger = LoggerFactory.getLogger(TondeuseItemReader.class);

    @PostConstruct
    public void init() {
        resetReader();
    }

    private void resetReader() {
        try {
            reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream()));
            String line = reader.readLine();
            String[] lawnDimensions = line.split(" ");
            pelouse = new Pelouse();
            pelouse.setWidth(Integer.parseInt(lawnDimensions[0]));
            pelouse.setHeight(Integer.parseInt(lawnDimensions[1]));
            logger.debug("Pelouse dimensions: width={}, height={}", pelouse.getWidth(), pelouse.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error initializing TondeuseItemReader", e);
        }
    }

    @Override
    public Tondeuse read() throws Exception {
        String line = reader.readLine();
        if (line == null) {
            return null;
        }

        String[] tondeusePosition = line.split(" ");
        Tondeuse tondeuse = new Tondeuse();
        tondeuse.setX(Integer.parseInt(tondeusePosition[0]));
        tondeuse.setY(Integer.parseInt(tondeusePosition[1]));
        tondeuse.setDirection(tondeusePosition[2].charAt(0));

        line = reader.readLine();
        tondeuse.setInstructions(line);

        logger.info("Read tondeuse: " + tondeuse.getX() + " " + tondeuse.getY() + " " + tondeuse.getDirection() + " " + tondeuse.getInstructions());

        return tondeuse;
    }

    public void reset() {
        resetReader();
    }
}
