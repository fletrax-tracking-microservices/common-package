package com.fletrax.tracking.commonpackage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CommonPackageApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonPackageApplication.class, args);
	}

}
