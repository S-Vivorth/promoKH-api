package com.kit.promokhapi;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class  PromokhApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(PromokhApiApplication.class, args);
    }
}
