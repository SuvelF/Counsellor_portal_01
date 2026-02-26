package in.stproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.stproject.dto.CounsellorDTO;
import in.stproject.dto.DashboardResponseDTO;
import in.stproject.service.CounsellorService;
import in.stproject.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {

	@Autowired
	private CounsellorService counsellorService;
	
	
	@Autowired
	private EnquiryService enqService;
	
	@GetMapping("/")
	public String index(Model model) {
		
	    CounsellorDTO cdto = new CounsellorDTO();
	    model.addAttribute("counsellor",cdto);
	    
		return "index";
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest req, Model model) {
		
		HttpSession session = req.getSession(false);
		session.invalidate();
		
		
//	    CounsellorDTO cdto = new CounsellorDTO();
//	    model.addAttribute("counsellor",cdto);
//	   
		
		
		return "redirect:/";
	}
	
	
	@PostMapping("/login")
	public String handleLogin(HttpServletRequest req, CounsellorDTO dto , Model model) {
		CounsellorDTO counsellor = counsellorService.login(dto);
		
		if(counsellor == null) {
			model.addAttribute("emsg","Invalid Credentials");
			CounsellorDTO cdto = new CounsellorDTO();
		    model.addAttribute("counsellor",cdto);
			return "index";
		}
		else {
			Integer counsellorId = counsellor.getCounsellorId();
			
			HttpSession session = req.getSession();
			session.setAttribute("counsellorId", counsellorId);
			
			DashboardResponseDTO dashboardDto = enqService.getDashboardInfo(counsellorId);
			
			model.addAttribute("dashboardDto", dashboardDto);
		
			return "dashboard";
		}
		
		
	}
	
	
	@GetMapping("/register")
	public String registerPage(Model model) {
		
	    CounsellorDTO cdto = new CounsellorDTO();
	    model.addAttribute("counsellor",cdto);
	    
		return "register";
	}
	
	
	@PostMapping("/register")
	public String handlingRegister(@ModelAttribute("counsellor") CounsellorDTO counsellor, Model model) {
		
		boolean unique = counsellorService.uniqueEmailCheck(counsellor.getEmail());
		
		if(unique) {
			boolean register = counsellorService.register(counsellor);
			if(register) {
			model.addAttribute("smsg","Registration Success");
		}
			else {
				model.addAttribute("emsg","Registration Failed");
			}
		
	}
		else {
			model.addAttribute("emsg","Enter unique Email");
		}
	
       return "register";
	}
	
	public String displayDashboard(HttpServletRequest req, Model model) {
		
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer)session.getAttribute("counsellorId");
	    
		DashboardResponseDTO dashboardDto = enqService.getDashboardInfo(counsellorId);
		
		model.addAttribute("dashboardDto", dashboardDto);
	
		return "dashboard";
	
	}
	
}
