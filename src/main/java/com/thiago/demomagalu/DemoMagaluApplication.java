package com.thiago.demomagalu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DemoMagaluApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoMagaluApplication.class, args);
	}

}
