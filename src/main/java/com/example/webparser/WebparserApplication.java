package com.example.webparser;

import com.example.webparser.service.ParserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class WebparserApplication {

    public static void main(String[] args) throws InterruptedException, IOException {
        SpringApplication.run(WebparserApplication.class, args);

        ParserService.parse();

    }

}
