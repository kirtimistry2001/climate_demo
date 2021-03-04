package ca.kirti.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ca.kirti.demo.model.ClimateSummary;
import ca.kirti.demo.service.ClimateSummaryService;


@Controller
@CrossOrigin(origins ="http://localhost:8080")
@RequestMapping("/")
public class ClimateController {


	@Autowired
	private ClimateSummaryService csService;
	
	@GetMapping("/")
	public String getClimateSummary(Model model) {
		List<ClimateSummary> summeryList = csService.findAllClimateSummaryData();
		model.addAttribute("summaryList", summeryList);
		return "index";		
	}

	
//	@GetMapping("/page/{pageNo}")
//	public String findByPagination(@PathVariable (value="pageNo") int pageNo, 
//			Model model)  {
//		System.out.println("findByPagination called ");
//		int pageSize= 5;
//		Page<ClimateSummary> page = csService.findPaginated(pageNo, pageSize);
//		List<ClimateSummary> summeryList= page.getContent();
//		model.addAttribute("currentPag",pageNo);
//		model.addAttribute("totalPages", page.getTotalPages());
//		model.addAttribute("totalItems", page.getTotalElements());
//		model.addAttribute("summaryList", summeryList);
//		return "index";
//	}
	
//	@GetMapping("/province/{province}/page/{pageNo}")
//	public List<ClimateSummary> findByProvince(@PathVariable (value="province")  String province, @PathVariable (value="pageNo") int pageNo, 
//			Model model)  {
//		System.out.println("findByProvince called ");
//		int pageSize= 5;
//		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
//		Page<ClimateSummary> page = csService.findByProviance(Optional.ofNullable(province).orElse("_"), pageable);
//		List<ClimateSummary> summeryList= page.getContent();
//		model.addAttribute("currentPag",pageNo);
//		model.addAttribute("totalPages", page.getTotalPages());
//		model.addAttribute("totalItems", page.getTotalElements());
//		model.addAttribute("summaryList", summeryList);
//		return summeryList;
//	}

	
//	@GetMapping("/climate_summary")
//	public List<ClimateSummary> getClimateSummayByDate(@RequestParam Optional<String> stationName,
//			@RequestParam Optional<Integer> pageNo,
//			@RequestParam Optional<String> sortBy) {
//		int pgno  = pageNo.orElse(0);
//		pgno = pgno != 0 ?pgno-- :0;
//		Pageable pageable = PageRequest.of(pgno, 5, Sort.Direction.ASC, sortBy.orElse("stateName"));
//		Page<ClimateSummary> page =  (Page<ClimateSummary>) csService.findByStateName(stationName.orElse("_"), pageable);
//		List<ClimateSummary> summeryList= page.getContent();
//		return summeryList;
//	}
	
}
