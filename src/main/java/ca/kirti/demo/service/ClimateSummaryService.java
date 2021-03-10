package ca.kirti.demo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import ca.kirti.demo.model.ClimateSummary;
import ca.kirti.demo.model.ClimateSummaryIdentity;
import ca.kirti.demo.model.PageableFilterData;
import ca.kirti.demo.repositories.ClimateSummaryRepository;
@Service
public class ClimateSummaryService {
	
	private static final Logger log = LoggerFactory.getLogger(ClimateSummaryService.class);

	@Autowired 
	private ClimateSummaryRepository climateRepo;
	
	@Autowired
	ResourceLoader resourceLoader;

	private final  DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	private String csvFileName = File.separator+"target"+File.separator+"classes"+File.separator+"eng-climate-summary.csv";

	
	public List<ClimateSummary> findAll() {
		return climateRepo.findAll();
	}
	

	/**
	 * Find all recored for given page number and given page size.
	 * @param pageNum
	 * @param pageSize
	 * @param sortField
	 * @param sortDir
	 * @return
	 */
	public Page<ClimateSummary> findAll(int pageNum, int pageSize, String sortField, String sortDir) {
		
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, 
				sortDir.equals("asc") ? Sort.by(sortField).ascending()
									  : Sort.by(sortField).descending()
		);
		
		return climateRepo.findAll(pageable);
	}

	/**
	 * Get the pageable filtered data
	 * @param filterData
	 * @return
	 */
	public Page<ClimateSummary> getPageableFilteredData(PageableFilterData filterData, Pageable pageable ) {
    	log.debug("filterData.getDateFrom()"+filterData.getDateFrom() +"pazeSize:"+filterData.getPageSize() +"SortField " +filterData.getSortField());
		if ((filterData.getDateFrom() != null  || filterData.getDateTo() != null) ) {
			return this.climateRepo.findByDatesBetween(filterData.getDateFrom(),filterData.getDateTo(), pageable);
		} else {
			log.debug("filterData find all");
			return this.climateRepo.findAll(pageable);
		}
	}

	/**
	 * find data in given date range
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<ClimateSummary> findByDatesBetween(Date dateFrom, Date dateTo) {
    	log.debug("dateTo"+dateTo +"dateFrom " +dateFrom);
		try {
			List<ClimateSummary> list = this.climateRepo.findByDatesBetween(dateFrom, dateTo);
			return list;
		} catch (Exception e) {
			log.error("Error while finding data for given data range");
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
	
	/**
	 * 
	 * @param id
	 * 			is the composite primary key
	 * @return
	 */
	public ClimateSummary findById(ClimateSummaryIdentity id) throws RuntimeException{
		return climateRepo.findById(id).get();
	}
	
	/**
	 * Read data from the CSv file and save in to H2 memory database
	 */
	public void saveClimateSummaryData() {
		
		FileReader fileReader;
		try {
			File file = ResourceUtils.getFile("classpath:eng-climate-summary.csv");
			String current = new java.io.File( "." ).getCanonicalPath();
			
			if(file.exists()) {
				csvFileName = file.getCanonicalPath();
			} else {
				csvFileName = current + csvFileName;
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		CSVParser csvParser = null;
		try {
			fileReader = new FileReader(csvFileName);

			BufferedReader bufferReader = new BufferedReader(fileReader);
			csvParser = new CSVParser(bufferReader,
					CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

			//Get the content present in the CSV File 
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			climateRepo.deleteAllInBatch();
			for (CSVRecord csvRecord : csvRecords) {
				float mean_Temp = 0.0f;
				float highest_Monthly_Maxi_Temp=0.0f;
				float lowest_Monthly_Min_Temp =0.0f;
				if(!csvRecord.get(3).isEmpty()) mean_Temp =  Float.parseFloat(csvRecord.get(3));
				if(!csvRecord.get(4).isEmpty()) highest_Monthly_Maxi_Temp =  Float.parseFloat(csvRecord.get(4));
				if(!csvRecord.get(5).isEmpty()) lowest_Monthly_Min_Temp =  Float.parseFloat(csvRecord.get(5));

				Date climateDate = null;
				String str_climateDate = csvRecord.get(2);
				try {
					climateDate = formatter.parse(str_climateDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				ClimateSummary  climateSummary = new ClimateSummary (
						csvRecord.get(0), csvRecord.get(1),						
						climateDate,
						mean_Temp, 
						highest_Monthly_Maxi_Temp, 
						lowest_Monthly_Min_Temp);

				climateRepo.save(climateSummary);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			//close the parser
			if(csvParser != null && !csvParser.isClosed()) {
				try {
					csvParser.close();
				} catch (IOException e) {
					log.error("Error while closing the parser");
				}
			}
		}

	}


	/**
	 * Check if file exists or not and also check for a valid CSV file or not.
	 * @param fileName
	 * @return
	 */
	public boolean isValidCSVFile(String fileName) {
		File file = new File(fileName);
		if (file.exists() ) {
			FileNameMap fileNameMap = URLConnection.getFileNameMap();
			String contentType = null;
			try
			{
				contentType = fileNameMap.getContentTypeFor(URLEncoder.encode(file.getAbsolutePath(), "UTF-8"));
			} catch (UnsupportedEncodingException e)  {
				e.printStackTrace();
			}
			log.debug("fileType:"+contentType);
			if (contentType != null && (contentType.equals("text/csv")
					|| contentType.equals("application/vnd.ms-excel")) ) {
				return true;
			} else {
				System.out.println("fileType:"+contentType);
			}
		} else {
			//TODO file doesn't exists
		}
		return false;
	}



	//TODO for more keyword and daterange search
//	public Page<ClimateSummary> getFilteredClimateData(PageableFilterData filter) {
//		log.debug("getFilteredClimateData called" +filter.toString());
//    	Pageable pageable = PageRequest.of(filter.getPageNo()-1, filter.getPageSize(),
//    			filter.getSortDir().equals("asc") ? Sort.by(filter.getSortField()).ascending()
//						  : Sort.by(filter.getSortField()).descending());
//
////		 Page<ClimateSummary> climateData = this.climateRepo.findAll(pageable);
//    	CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//    	CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);
//    	Root<Item> itemRoot = criteriaQuery.from(Item.class);
//		 Page<ClimateSummary> climateData = climateRepo.findAll((Specification<ClimateSummary>) (root, cq, cb) -> {
//			Predicate p = cb.conjunction();
//			log.info("getClimateData Specification called");
//			if (Objects.nonNull(filter.getDateFrom()) && Objects.nonNull(filter.getDateTo()) && filter.getDateFrom().before(filter.getDateTo())) {
//				p = cb.and(p, cb.between(root.get("climateDate"), filter.getDateFrom(), filter.getDateTo()));
//			}
//			if (!StringUtils.hasLength(filter.getKeyword())) {
//				p = cb.or(p, cb.like(root.get("stationName"), "%" + filter.getKeyword() + "%"));
//
//				//check for valid float or not
//				try{
//					float temp = Float.parseFloat(filter.getKeyword());
//					p = cb.or(p, cb.like(root.get("meanTemp"), "%" + temp + "%"));
//				}catch(NumberFormatException e){
//					//not float
//					log.info("not a valid float");
//				}
//			} else {
//				log.info("no keyword");
//			}
//			cq.orderBy(cb.desc(root.get("stationName")), cb.asc(root.get("climeDate")));
//			return p;
//		}, pageable);
//		 return climateData;
//	}
	
}
