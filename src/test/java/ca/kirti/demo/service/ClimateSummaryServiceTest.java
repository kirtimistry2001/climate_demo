package ca.kirti.demo.service;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ca.kirti.demo.model.ClimateSummary;
import ca.kirti.demo.repositories.ClimateSummaryRepository;

@DataJpaTest
@AutoConfigureTestDatabase (replace = Replace.NONE)
class ClimateSummaryServiceTest {

    @Autowired
    private  ClimateSummaryRepository climateRepo;

    @MockBean
    private ClimateSummaryService service;


    /**
     * @throws java.lang.Exception
     */
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    	

    }
    
    void saveClimateSummaryData() {

        String sDate1="03/12/1998";
        String sDate2="22/12/1998";
        String sDate3="31/10/1998";
        Date date1;
        Date date2;
        Date date3;
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
            date2=new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);
            date3=new SimpleDateFormat("dd/MM/yyyy").parse(sDate3);
            ClimateSummary climateData1 = new ClimateSummary("CHEMAINUS","ON",date1, 15.1f,26.5f,7.0f);
            ClimateSummary climateData2 = new ClimateSummary("LAKE COWICHAN","BC",date2, 13.8f,31.0f,3.0f);
            ClimateSummary climateData3 = new ClimateSummary("KEY LAKE","SK",date3, 2.8f,19.3f,-3.0f);
            if (climateData1 != null) {
            	ClimateSummary savedData1 = climateRepo.save(climateData1);
            	assertNotNull(savedData1);
            }
            if (climateData2 != null) {
            	ClimateSummary savedData2 = climateRepo.save(climateData2);
            	assertNotNull(savedData2);
            }
            if (climateData3 != null) {
            	ClimateSummary savedData3 = climateRepo.save(climateData3);
            	assertNotNull(savedData3);
            }
            Assertions.assertEquals(3,climateRepo.findAll().size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

   
    /**
	 * Test method for {@link ca.kirti.demo.service.ClimateSummaryServiceImpl#findAllClimateSummaryData()}.
	 */
	@Test
	void testFindAllClimateSummaryData() {
        saveClimateSummaryData();
        Assertions.assertEquals(3,climateRepo.findAll().size());
	}


    @Test
    void findByDatesBetween() {
        saveClimateSummaryData();
        Assertions.assertEquals(3,climateRepo.findAll().size());
         //date for range
        String fromDateStr="01/12/1998";
        String toDateStr="31/12/1998";
        Date fromDate;
        Date toDate;
        try {
            fromDate=new SimpleDateFormat("dd/MM/yyyy").parse(fromDateStr);
            toDate=new SimpleDateFormat("dd/MM/yyyy").parse(toDateStr);

        Assertions.assertEquals(2,
        		climateRepo.findByDatesBetween(fromDate,toDate).size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findById() {
        saveClimateSummaryData();
        Assertions.assertEquals(3,climateRepo.findAll().size());
         //date for range
        String fromDateStr="01/12/1998";
        String toDateStr="31/12/1998";
        Date fromDate;
        Date toDate;
        try {
            fromDate=new SimpleDateFormat("dd/MM/yyyy").parse(fromDateStr);
            toDate=new SimpleDateFormat("dd/MM/yyyy").parse(toDateStr);
            
        Assertions.assertEquals(2,
        		climateRepo.findByDatesBetween(fromDate,toDate).size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



}