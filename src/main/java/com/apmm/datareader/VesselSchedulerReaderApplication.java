package com.apmm.datareader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VesselSchedulerReaderApplication {

	@GetMapping("/hello")
	public String message(){
		return "hello vessel scheduler reader";
	}
	public static void main(String[] args) {
		SpringApplication.run(VesselSchedulerReaderApplication.class, args);
	}

}
