package com.apmm.datareader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class VesselSchedulerReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(VesselSchedulerReaderApplication.class, args);
	}

}
