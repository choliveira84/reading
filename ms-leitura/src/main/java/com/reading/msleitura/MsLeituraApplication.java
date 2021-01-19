package com.reading.msleitura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsLeituraApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsLeituraApplication.class, args);
	}

}
