package com.tondeuse.batch.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.tondeuse.batch.processor.TondeuseItemProcessor;
import com.tondeuse.batch.reader.TondeuseItemReader;
import com.tondeuse.batch.writer.TondeuseItemWriter;
import com.tondeuse.model.Tondeuse;


@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(BatchConfig.class);

    @Bean
    public Job processTondeuseJob(JobBuilderFactory jobBuilderFactory,
                               StepBuilderFactory stepBuilderFactory,
                               TondeuseItemReader reader,
                               TondeuseItemProcessor processor,
                               TondeuseItemWriter writer) {

    	logger.debug("Configuring job");
    	
        Step step = stepBuilderFactory.get("tondeuse-step")
                .<Tondeuse, Tondeuse>chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
        
        logger.debug("Job configuration complete");

        return jobBuilderFactory.get("tondeuse-job")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }
    
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .build();
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        // Override to use the custom data source
        super.setDataSource(dataSource);
    }

}
