package com.example.mydeveloperlife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MyDeveloperLifeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyDeveloperLifeApplication.class, args);
    }

}
