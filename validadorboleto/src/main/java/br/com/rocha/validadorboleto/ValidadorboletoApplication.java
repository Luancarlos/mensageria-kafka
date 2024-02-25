package br.com.rocha.validadorboleto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class ValidadorboletoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValidadorboletoApplication.class, args);
	}

}
