package in.stproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.stproject.dto.EnqFilterDTO;
import in.stproject.dto.EnquiryDTO;
import in.stproject.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

	@Autowired
	private EnquiryService enqService;
	
	
	@GetMapping("/edit-enquiry")
	public String editEnquiry(@RequestParam("enqId") Integer enqId, Model model) {
		
		EnquiryDTO enqDto = enqService.getEnquiryById(enqId);
		model.addAttribute("enquiry",enqDto);
		
		
		return "add-enquiry";
	}
	
	@GetMapping("/enquiry-page")
	public String loadEnqPage(Model model) {
		
		EnquiryDTO enqDto = new EnquiryDTO();
		model.addAttribute("enquiry",enqDto);
		
		
		return "add-enquiry";
	}
	
	@PostMapping("/add-enquiry")
	public String addEnquiry(HttpServletRequest req, @ModelAttribute("enquiry")EnquiryDTO enquiry, Model model) {
		
		HttpSession session = req.getSession(false);
		Integer counsellorId =  (Integer)session.getAttribute("counsellorId");
		
		boolean status = enqService.addEnquiry(enquiry, counsellorId);
		
		if(status) {
			model.addAttribute("smsg", "Enquiry Saved");
		}
		else {
			model.addAttribute("emsg", "Failed to save enquiry");
		}
		
		return "add-enquiry";
	}
	
	@GetMapping("/view-enquiries")
	public String getEnquiries(HttpServletRequest req, Model model) {
	HttpSession session = req.getSession(false);
	
	Integer counsellorId = (Integer)session.getAttribute("counsellorId");
	
	
		
	List<EnquiryDTO> enqList = enqService.getEnquiries(counsellorId);
	
	model.addAttribute("enquiries", enqList);	
	
	EnqFilterDTO filterDto = new EnqFilterDTO();
	model.addAttribute("filterDto", filterDto);
	
		return "view-enquiries";
	}
	
	
	@PostMapping("/filter-enquiries")
	public String filterEnquiries(HttpServletRequest req, @ModelAttribute("filterDto") EnqFilterDTO filterDto, Model model) {
	HttpSession session = req.getSession(false);
	
	Integer counsellorId = (Integer)session.getAttribute("counsellorId");
	
	
		
	List<EnquiryDTO> enqList = enqService.getEnquiries(filterDto, counsellorId);
	
	model.addAttribute("enquiries", enqList);	
	
	
		return "view-enquiries";
	}
	
	
}
