package scg.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class ScgApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScgApplication.class, args);
    }

}
