package com.reading.msnotificacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsNotificacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsNotificacaoApplication.class, args);
	}

}
