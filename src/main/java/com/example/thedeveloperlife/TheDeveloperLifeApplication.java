package com.example.thedeveloperlife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TheDeveloperLifeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TheDeveloperLifeApplication.class, args);
    }

}
