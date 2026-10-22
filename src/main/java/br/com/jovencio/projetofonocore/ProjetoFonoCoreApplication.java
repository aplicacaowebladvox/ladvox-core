package br.com.jovencio.projetofonocore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class ProjetoFonoCoreApplication {

	public static void main( String[] args ) {
		SpringApplication.run( ProjetoFonoCoreApplication.class, args );
	}

}
