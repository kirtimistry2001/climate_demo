package ca.kirti.demo.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.kirti.demo.model.ClimateSummary;
import ca.kirti.demo.model.ClimateSummaryIdentity;
import ca.kirti.demo.model.PageableFilterData;
import ca.kirti.demo.service.ClimateSummaryService;



@Controller
@CrossOrigin(origins ="http://localhost:8080")
@RequestMapping({ "/", "/index" })
public class ClimateController {
	
	private static final Logger log = LoggerFactory.getLogger(ClimateController.class);

	@Autowired
	private ClimateSummaryService csService;
	
	@GetMapping
	public String getClimateSummary(Model model) {
		//default pagination and sorting field with ascending sort direction
		PageableFilterData defaultfilter  = new PageableFilterData(null, null, "",
				10, 1,"stationName", "asc");
		return FilterClimateData(defaultfilter, model);		
	}


	/**
	 * Get the climate detail for given composite key by passing parameter
	 * @param stationName
	 * @param province
	 * @param climateDate
	 * @return
	 */
	@GetMapping("/detail/{stationName}/{province}/{climateDate}")
	public ModelAndView showClimateDetail(@PathVariable(name = "stationName") String stationName,
			@PathVariable(name = "province") String province,
			@PathVariable(name = "climateDate") String climateDate) {
		Date date= null;
		ModelAndView mav = new ModelAndView("detail");
		String message = "";
		log.debug("stationName: "+stationName + "province: " +province +"date :"+ climateDate);
		if (stationName.isEmpty() || province.isEmpty() || climateDate.isEmpty()) {
			message = "Climate date,station Name and Province cannot be empty to find unique data/record";
		}
		if(!climateDate.isEmpty()) {
			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
				date = df.parse(climateDate);
			} catch (ParseException e) {
				message = "Given Climate date is not a valid date.";
				mav.addObject("message", message);
				e.printStackTrace();
			}
		}
		ClimateSummaryIdentity id = new ClimateSummaryIdentity(stationName,province,date);
		ClimateSummary climateDetail = null;
		try {
			climateDetail = csService.findById(id);
		} catch(Exception e) {
			message = message + "Cannot find detail for given information!";
			mav.addObject("message", message);
		}
		mav.addObject("climateDetail", climateDetail);
		return mav;
	}


	/**
	 * Get the climate detail for given composite key (Called from ajax call)
	 * @param province
	 * @param stationName
	 * @param climateDate
	 * @return
	 */
	@RequestMapping(value="/showDetails", method=RequestMethod.GET)
	public @ResponseBody ClimateSummary viewDetail(@RequestParam("province") String province,
			@RequestParam("stationName") String stationName,
			@RequestParam("climateDate") String climateDate)  {  

		String message="";
		Date date= null;

		if (stationName.isEmpty() || province.isEmpty() || climateDate.isEmpty() || climateDate == null) {
			message = "Climate date,station Name and Province cannot be empty to find unique data/record";
		}
		if(!climateDate.isEmpty()) {
			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
				date = df.parse(climateDate);
			} catch (ParseException e) {
				message = "Given Climate date is not a valid date.";

				e.printStackTrace();
			}
		}
		ClimateSummaryIdentity id = new ClimateSummaryIdentity(stationName,province,date);
		ClimateSummary climateDetail = null;
		try {
			climateDetail = csService.findById(id);
		} catch(Exception e) {
			message = message + "Cannot find detail for given information!";
		}
		return climateDetail;
	}

	
	/**
	 * Redirecting to view the detail page.
	 * (This method called from ajax success call to redirect the page to detail page)
	 * @param model
	 * @param detailStr
	 * 		is the JSOn String with detail information of ClimateSummary 
	 * @return
	 */
	@RequestMapping("/viewControllerHandler")
	public String redirectToDetail(Model model,@RequestParam("climateDetail") String detailStr) {
		ClimateSummary climateDetail = null;
		String message="";
		try {
			ObjectMapper mapper = new ObjectMapper();
			climateDetail = mapper.readValue(detailStr, ClimateSummary.class);
			model.addAttribute("climateDetail", climateDetail);
		} catch(Exception ex) {
			log.error("Error while converting JSON string to ClimateSummry object.");
			message =  message + "Cannot find detail for given information!";
			ex.printStackTrace();
		}
		model.addAttribute("message", message);
		return "detail";
	}	

	/**
	 * Get climate summary for given date range
	 * @param dateRange
	 * @param model
	 * @return
	 */
	@PostMapping()
	public String FilterClimateData(PageableFilterData filterData, Model model) {
		log.debug("filterData pageno : " +filterData.getPageNo() + "pageSize:" +filterData.getPageSize() + "dateFrom: "+filterData.getDateFrom() + "dateTo:"+filterData.getDateTo());
		int currentPage = filterData.getPageNo();
		int pageSize = filterData.getPageSize();
		String message="";
		List<ClimateSummary> summeryList = new ArrayList<ClimateSummary>();
	
		if ((filterData.getDateFrom() != null  && filterData.getDateTo() != null) ) {
			summeryList= csService.findByDatesBetween(filterData.getDateFrom(), filterData.getDateTo());
			log.debug("summeryList size :"+summeryList.size());
			if(filterData.getPageSize() > summeryList.size()) {
				currentPage = 1;
				pageSize = summeryList.size();
				log.debug("page size is greater then list setting current page to 1");
			}
		} else if ((filterData.getDateFrom() == null  && filterData.getDateTo() == null) ) {
			log.debug("Both date null default find all");
			summeryList= csService.findAll();
		} else {
			message = "Not a vlid date range";
		}
		log.debug("summeryList size:"+summeryList.size());
		if (summeryList.size() > 0) {
		Pageable pageable = PageRequest.of(currentPage-1, pageSize,
    			filterData.getSortDir().equals("asc") ? Sort.by(filterData.getSortField()).ascending()
						  : Sort.by(filterData.getSortField()).descending());

		Page<ClimateSummary> page  = csService.getPageableFilteredData(filterData, pageable);
		
		log.debug("total pages " +page.getTotalPages() +"currentPage :"+currentPage +"pageSize: "+pageSize);
		if(currentPage > page.getTotalPages()) {
			currentPage = page.getTotalPages();
			filterData.setPageNo(currentPage);
			pageable = PageRequest.of(currentPage-1, pageSize,
	    			filterData.getSortDir().equals("asc") ? Sort.by(filterData.getSortField()).ascending()
							  : Sort.by(filterData.getSortField()).descending());
			page  = csService.getPageableFilteredData(filterData, pageable);
			
		} 
		summeryList = page.getContent();
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		} else {
			model.addAttribute("totalPages", 0);
			model.addAttribute("totalItems", 0);
			message = message + "or No data found for given selection!";
			model.addAttribute("message", message);
		}
		model.addAttribute("reverseSortDir", filterData.getSortDir().equals("asc") ? "desc" : "asc");

		model.addAttribute("summaryList", summeryList);
		model.addAttribute("dataFilter", filterData);
		return "index";
	}
}
