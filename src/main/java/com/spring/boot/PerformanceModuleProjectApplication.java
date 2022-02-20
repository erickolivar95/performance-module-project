package com.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class PerformanceModuleProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PerformanceModuleProjectApplication.class, args);
	}
	
	@Bean
	public OpenAPI openApiConfig() {
		return new OpenAPI().info(apiInfo());
	}
	
	public Info apiInfo() {
		Contact contact = new Contact();
		contact.setName("Erick Olivar");
		contact.setUrl("https://theksquaregroup.com");
		contact.setEmail("Erick.Olivar@theksquaregroup.com");
		Info info = new Info();
		info.title("Performance module API")
		.description("Live Code System Swagger Open API")
		.contact(contact)
		.version("v1.0.0");
		return info;
	}

}
