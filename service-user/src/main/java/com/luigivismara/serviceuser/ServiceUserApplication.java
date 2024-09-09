package com.luigivismara.serviceuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages ="com.luigivismara.modeldomain.*")
@EnableJpaRepositories(basePackages = "com.luigivismara.modeldomain.repository")
@EntityScan(basePackages = "com.luigivismara.modeldomain.entity")
public class ServiceUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceUserApplication.class, args);
	}

}
