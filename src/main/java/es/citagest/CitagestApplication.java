package es.citagest;

import es.citagest.catalog.desk.repository.DeskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CitagestApplication {
	@Autowired
	private DeskRepository languageRepository;

	public static void main(String[] args) {
		SpringApplication.run(CitagestApplication.class, args);
	}
}
