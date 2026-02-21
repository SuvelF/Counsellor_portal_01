package in.stproject.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.stproject.dto.DashboardResponseDTO;
import in.stproject.dto.EnqFilterDTO;
import in.stproject.dto.EnquiryDTO;
import in.stproject.entity.CounsellorEntity;
import in.stproject.entity.EnquiryEntity;
import in.stproject.repo.CounsellorRepo;
import in.stproject.repo.EnquiryRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService{

	@Autowired
	private EnquiryRepo enqRepo;
	
	@Autowired
	private CounsellorRepo counsellorRep;
	
	@Override
	public DashboardResponseDTO getDashboardInfo(Integer counsellorId) {
		// TODO Auto-generated method stub
		List<EnquiryEntity> enqList = enqRepo.findByCounsellorCounsellorId(counsellorId);
		
		DashboardResponseDTO dto = new DashboardResponseDTO();
		
		
		int openCount = enqList.stream()
				.filter(enq -> enq.getEneStatus()
				.equals("OPEN")).collect(Collectors.toList())
				.size();
		
		
		int enrolledCnt	= enqList.stream()
							.filter(enq -> enq.getEneStatus().equals("ENROLLED"))
							.collect(Collectors.toList())
							.size();
		
		
		int lostCnt	= enqList.stream()
				.filter(enq -> enq.getEneStatus().equals("LOST"))
				.collect(Collectors.toList())
				.size();
		
		dto.setTotalEnqCnt(enqList.size());
		dto.setOpenEnqCnt(openCount);
		dto.setEnrolledEnqCnt(enrolledCnt);
		dto.setLostEnqCnt(lostCnt);

		
		return null;
	}

	@Override
	public boolean addEnquiry(EnquiryDTO enqDTO, Integer counsellorId) {
		// TODO Auto-generated method stub
		
	 	
		
		EnquiryEntity entity = new EnquiryEntity();
		BeanUtils.copyProperties(enqDTO, entity);
		
		
		//Setting FK(counsellor_Id) to enquiry obj
		Optional<CounsellorEntity> byId = counsellorRep.findById(counsellorId);
		
		if(byId.isPresent()) {
			CounsellorEntity counsellor = byId.get();
			entity.setCounsellor(counsellor);
		}
		EnquiryEntity save = enqRepo.save(entity);
		
		return save.getEnqId()!=null;
	}

	@Override
	public List<EnquiryDTO> getEnquiries(Integer counsellorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EnquiryDTO> getEnquiries(EnqFilterDTO filterDTO, Integer counsellorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnquiryDTO getEnquiryById(Integer enqId) {
		// TODO Auto-generated method stub
		return null;
	}

}
