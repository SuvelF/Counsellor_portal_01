package in.stproject.service;

import java.util.List;

import in.stproject.dto.DashboardResponseDTO;
import in.stproject.dto.EnqFilterDTO;
import in.stproject.dto.EnquiryDTO;

public interface EnquiryService {

	
	
	public DashboardResponseDTO getDashboardInfo(Integer counsellorId);
	
	
	public boolean addEnquiry(EnquiryDTO enqDTO, Integer counsellorId);
	
	
	public List<EnquiryDTO> getEnquiries(Integer counsellorId);
	
	public List<EnquiryDTO> getEnquiries(EnqFilterDTO filterDTO, Integer counsellorId);
	
	
	public EnquiryDTO getEnquiryById(Integer enqId);
	

	

}
