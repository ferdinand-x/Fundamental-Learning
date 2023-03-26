package com.paradise.multithread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URL;

/**
 * @author PARADISE
 */
@SpringBootApplication
public class MultiThreadApplication {


    public static void main(String[] args) throws IOException {
        SpringApplication.run(MultiThreadApplication.class, args);
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/");
        URL url = resource.getURL();
        System.out.println(url.getPath());
        String property = System.getProperty("user.dir");
        System.err.println(property);
    }

}
