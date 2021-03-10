package ca.kirti.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import ca.kirti.demo.model.ClimateSummary;
import ca.kirti.demo.repositories.ClimateSummaryRepository;
import ca.kirti.demo.service.ClimateSummaryService;

@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)//will drop the database after each test
@AutoConfigureTestDatabase (replace = Replace.ANY)
public class ClimateSummaryTest {
	@Autowired
	private  ClimateSummaryRepository climateRepo;

	@MockBean
	private ClimateSummaryService service;

	@Test
	void createClimateSummaryData() {
		String sDate1="03/12/1998";
		Date date1;
		try {
			date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
			ClimateSummary climateData1 = new ClimateSummary("CHEMAINUS","ON",date1, 15.1f,26.5f,7.0f);
			ClimateSummary savedData1 = climateRepo.save(climateData1);
			assertNotNull(savedData1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}


}
