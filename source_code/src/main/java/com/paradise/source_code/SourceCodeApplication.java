package com.paradise.source_code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author PARADISE
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SourceCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SourceCodeApplication.class, args);
    }

}
