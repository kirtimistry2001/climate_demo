package ca.kirti.demo.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
		return findByPagination(model, 1, "stationName", "asc");		
	}


	/**
	 * Find  all records 
	 * @param model
	 * @param pageNo
	 * 			get the record for given page number
	 * @param sortField
	 * 		Column to sort
	 * @param sortDir
	 * 		Sort direction "ASC/DESC"
	 * @return
	 */
	@GetMapping("/page/{pageNo}")
	public String findByPagination(Model model, @PathVariable (value="pageNo") int pageNo, 
									@Param("sortField") String sortField,
									@Param("sortDir") String sortDir)  {
		//TODO for page size
		int pageSize= 10;
		//date range
		PageableFilterData filterData  = new PageableFilterData(null, null, "",
				pageSize, pageNo,sortField,sortDir);

		log.info("get data for pagination "+filterData.toString() );
		Page<ClimateSummary> page = csService.findPaginated(null,null, "", pageNo, pageSize, sortField, sortDir);
		List<ClimateSummary> summeryList= page.getContent();
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("summaryList", summeryList);
		model.addAttribute("dataFilter", filterData);
		return "index";
	}
	
	/**
	 * Get the climate detail for given composite key
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
		System.out.println("stationName: "+stationName + "province: " +province +"date :"+ climateDate);
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
			date = df.parse(climateDate);
			System.out.println("date: "+date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ClimateSummaryIdentity id = new ClimateSummaryIdentity(stationName,province,date);
		ClimateSummary climateDetail = csService.findById(id);
		mav.addObject("climateDetail", climateDetail);
		return mav;
	}
	
	/**
	 * Get climate summary for given date range
	 * @param dateRange
	 * @param model
	 * @return
	 */
	@PostMapping()
    public String FilterClimateData(PageableFilterData filterData, Model model) {
		log.info("get filterData  for:" +filterData.toString());
		//TODO for page size
		int pageSize= 10;
		filterData.setPageSize(pageSize);
		//filtered data must be displayed from 1st page.
        int pageNo = 1;
        filterData.setPageNo(pageNo);
    	Pageable pageable = PageRequest.of(pageNo-1, pageSize,
    			filterData.getSortDir().equals("asc") ? Sort.by(filterData.getSortField()).ascending()
						  : Sort.by(filterData.getSortField()).descending());
		Page<ClimateSummary> page = csService.findPaginated(filterData.getDateFrom(), filterData.getDateTo(), "", pageNo, pageSize, filterData.getSortField(), filterData.getSortDir());

    	List<ClimateSummary> summeryList= page.getContent();
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", filterData.getSortField());
		model.addAttribute("sortDir", filterData.getSortDir());
		model.addAttribute("reverseSortDir", filterData.getSortDir().equals("asc") ? "desc" : "asc");
		model.addAttribute("summaryList", summeryList);
        model.addAttribute("dataFilter", filterData);
	
        return "index";
    }
}
