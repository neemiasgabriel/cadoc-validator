package com.picpay.cadocvalidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CadocValidatorApplication {
  public static void main(String[] args) {
    SpringApplication.run(CadocValidatorApplication.class, args);
  }
}
