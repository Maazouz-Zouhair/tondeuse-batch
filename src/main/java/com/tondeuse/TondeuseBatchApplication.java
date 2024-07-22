package com.tondeuse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.tondeuse.batch.config.BatchConfig;

@SpringBootApplication
@Import({BatchConfig.class})
public class TondeuseBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(TondeuseBatchApplication.class, args);
    }
}
