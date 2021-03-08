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
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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
import ca.kirti.demo.repositories.ClimateSummaryRepository;
@Service
public class ClimateSummaryService {

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
	 * Get the pageable data
	 * @param fromDate
	 * @param toDate
	 * @param keyword
	 * @param pageNo
	 * @param pageSize
	 * @param sortField
	 * @param sortDir
	 * @return
	 */
	public Page<ClimateSummary> findPaginated(Date fromDate, Date toDate, String keyword, int pageNo, int pageSize, String sortField, String sortDir) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize,
				sortDir.equals("asc") ? Sort.by(sortField).ascending()
						  : Sort.by(sortField).descending());
		return this.climateRepo.findAll(pageable);
	}
	
	/**
	 * 
	 * @param id
	 * 			is the composite primary key
	 * @return
	 */
	public ClimateSummary findById(ClimateSummaryIdentity id) {
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
		
		try {
			fileReader = new FileReader(csvFileName);

			BufferedReader bufferReader = new BufferedReader(fileReader);
			CSVParser csvParser = new CSVParser(bufferReader,
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
			System.out.println("fileType:"+contentType);
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

}
