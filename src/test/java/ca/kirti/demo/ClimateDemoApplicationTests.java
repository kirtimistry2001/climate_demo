package ca.kirti.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ca.kirti.demo.controllers.ClimateController;
import ca.kirti.demo.model.ClimateSummary;
import ca.kirti.demo.repositories.ClimateSummaryRepository;
import ca.kirti.demo.service.ClimateSummaryService;

@SpringBootTest
class ClimateDemoApplicationTests {
	@Autowired
	private ClimateController controller;
	
	@Autowired 
	private ClimateSummaryService service;
	
	@MockBean
	private ClimateSummaryRepository repository;

	
	@Test
	private void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	public void getAllClimateSummariesTest() {
		when(controller.findAll()).thenReturn(
				Stream.of(new ClimateSummary("CHEMAINUS","BC",new Date(), 15.1f,26.5f,7.0f), 
						new ClimateSummary("LAKE COWICHAN","BC",new Date(), 13.8f,31.0f,3.0f)).collect(Collectors.toList()));
		 Assertions.assertEquals(2,service.findAll().size());
		
	}
}
