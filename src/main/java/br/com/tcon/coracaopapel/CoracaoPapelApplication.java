package br.com.tcon.coracaopapel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "br.com.tcon.coracaopapel.modelo.dominio")
public class CoracaoPapelApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoracaoPapelApplication.class, args);
	}

}
