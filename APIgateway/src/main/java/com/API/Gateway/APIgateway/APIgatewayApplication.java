package com.API.Gateway.APIgateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class APIgatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(APIgatewayApplication.class, args);
	}
}