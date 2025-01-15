package com.luigivismara.serviceuser;

import com.luigivismara.modeldomain.annotation.CommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@CommonConfig(basePackages = "com.luigivismara.*")
@EnableCaching
public class ServiceUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceUserApplication.class, args);
	}

}
