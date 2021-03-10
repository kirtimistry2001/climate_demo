package ca.kirti.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.kirti.demo.controllers.ClimateController;

@SpringBootTest
public class SmokeTest {

	@Autowired
	private ClimateController controller;
	
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
}
