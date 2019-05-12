package com.example.demo;

import com.example.demo.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AppConfig.class)
@Slf4j
public class DomClickApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomClickApplication.class, args);
    }

}
