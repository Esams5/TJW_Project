package com.example.tjwproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class TjwProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TjwProjectApplication.class, args);
    }
}
