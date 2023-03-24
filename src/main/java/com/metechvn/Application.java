package com.metechvn;

import luongdev.cqrs.EnableCqrsBus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableCaching
@EnableCqrsBus
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = {"luongdev.*", "com.metechvn.*"})
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {

    }
}
