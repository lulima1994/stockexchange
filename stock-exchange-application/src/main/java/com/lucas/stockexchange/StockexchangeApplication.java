package com.lucas.stockexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StockexchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockexchangeApplication.class, args);
    }

}
