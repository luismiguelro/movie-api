package dev.luismiguelro.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoviesApplication {
	public static void main(String[] args) {
		// Punto de interrupción o impresión de información
		System.out.println("Iniciando la aplicación Spring Boot...");
		SpringApplication.run(MoviesApplication.class, args);
	}
}
