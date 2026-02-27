package in.stproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
				.equals("Open")).collect(Collectors.toList())
				.size();
		
		
		int enrolledCnt	= enqList.stream()
							.filter(enq -> enq.getEneStatus().equals("Enrolled"))
							.collect(Collectors.toList())
							.size();
		
		
		int lostCnt	= enqList.stream()
				.filter(enq -> enq.getEneStatus().equals("Lost"))
				.collect(Collectors.toList())
				.size();
		
		dto.setTotalEnqCnt(enqList.size());
		dto.setOpenEnqCnt(openCount);
		dto.setEnrolledEnqCnt(enrolledCnt);
		dto.setLostEnqCnt(lostCnt);

		
		return dto;
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
		List<EnquiryDTO> enqsDtoList = new ArrayList<>();
		
		List<EnquiryEntity> enqList = enqRepo.findByCounsellorCounsellorId(counsellorId);
		for(EnquiryEntity entity : enqList) {
			EnquiryDTO dto = new EnquiryDTO();
			BeanUtils.copyProperties(entity, dto);
			enqsDtoList.add(dto);
		}
		return enqsDtoList;
	}

	@Override
	public List<EnquiryDTO> getEnquiries(EnqFilterDTO filterDTO, Integer counsellorId) {
		// TODO Auto-generated method stub
	
		EnquiryEntity entity = new EnquiryEntity();
		if(filterDTO.getClassMode()!=null && !filterDTO.getClassMode().equals("")) {
			entity.setClassMode(filterDTO.getClassMode());
		}
		if(filterDTO.getCourse()!=null && !filterDTO.getCourse().equals("")) {
			entity.setCourse(filterDTO.getCourse());
		}
		if(filterDTO.getEneStatus()!=null && !filterDTO.getEneStatus().equals("")) {
			entity.setEneStatus(filterDTO.getEneStatus());
		}
		
		
		CounsellorEntity counsellor = new CounsellorEntity();
		counsellor.setCounsellorId(counsellorId);
		entity.setCounsellor(counsellor);
		
		Example<EnquiryEntity> of = Example.of(entity);
		
		List<EnquiryEntity> enqList = enqRepo.findAll(of);
		
		List<EnquiryDTO> enqsDtoList = new ArrayList<>();
		
		for(EnquiryEntity enq : enqList) {
			EnquiryDTO dto = new EnquiryDTO();
			BeanUtils.copyProperties(enq, dto);
			enqsDtoList.add(dto);
		}
		
		return enqsDtoList;
	
	}

	@Override
	public EnquiryDTO getEnquiryById(Integer enqId) {
		// TODO Auto-generated method stub
		
		
		Optional<EnquiryEntity> byId =enqRepo.findById(enqId);
		
		if(byId.isPresent()) {
			EnquiryEntity enquiryEntity = byId.get();
			EnquiryDTO dto = new EnquiryDTO();
			BeanUtils.copyProperties(enquiryEntity, dto);
			return dto;
		}
		return null;
	}

}
