package com.wanted.spendtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpendTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpendTrackerApplication.class, args);
    }

}
