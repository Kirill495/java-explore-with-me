package ru.practicum.ewm.main_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.practicum.ewm.*")
public class MainServiceApp {
   public static void main(String[] args) {
      SpringApplication.run(MainServiceApp.class, args);
   }
}
