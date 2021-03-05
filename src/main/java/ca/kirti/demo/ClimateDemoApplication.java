package ca.kirti.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ca.kirti.demo.service.ClimateSummaryService;

@SpringBootApplication
public class ClimateDemoApplication implements CommandLineRunner {

	@Autowired
	private ClimateSummaryService csService;
	
	public static void main(String[] args) {
		SpringApplication.run(ClimateDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		csService.saveClimateSummaryData();
	}

}
