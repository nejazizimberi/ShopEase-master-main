package com.ShopEase.ShopEase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.ShopEase.ShopEase.Model")
public class ShopEaseApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShopEaseApplication.class, args);
	}
}
