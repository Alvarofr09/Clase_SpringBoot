package com.notes.notes;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotesApplication {

	public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory("./notes").load();
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });

        SpringApplication.run(NotesApplication.class, args);
	}

}
