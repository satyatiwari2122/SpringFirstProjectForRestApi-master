package com.springfirstproject.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringFirstProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringFirstProjectApplication.class, args);
    }

}
