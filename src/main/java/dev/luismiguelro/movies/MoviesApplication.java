package dev.luismiguelro.movies;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.charset.Charset;

@SpringBootApplication
public class MoviesApplication {
	static Dotenv dotenv = Dotenv.load();
	private static final String ngrokAuthToken = dotenv.get("NGROK_AUTHTOKEN");

	public static void main(String[] args) {
		// Inicia la aplicación Spring Boot
		SpringApplication.run(MoviesApplication.class, args);
	}

}

