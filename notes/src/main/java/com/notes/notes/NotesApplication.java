package com.notes.notes;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotesApplication {

    public static void main(String[] args) {
        // Cargar el .env que está en la raíz del proyecto
        Dotenv dotenv = Dotenv.configure()
                .directory("./notes")
                .ignoreIfMissing()
                .load();

        // Inyectar las variables como propiedades del sistema
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        // Ahora sí, arrancamos Spring Boot
        SpringApplication.run(NotesApplication.class, args);
    }
}
