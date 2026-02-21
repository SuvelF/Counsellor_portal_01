package in.stproject.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.stproject.dto.CounsellorDTO;
import in.stproject.entity.CounsellorEntity;
import in.stproject.repo.CounsellorRepo;

@Service
public class CounsellorServiceImpl implements CounsellorService{
	
	@Autowired
	private CounsellorRepo councellorRepo;

	@Override
	public CounsellorDTO login(CounsellorDTO counsellorDTO) {
		// TODO Auto-generated method stub
	 CounsellorEntity entity =	councellorRepo.findByEmailAndPwd(counsellorDTO.getEmail(),
				counsellorDTO.getPwd());
		
		if(entity != null) {
			
			CounsellorDTO dto = new CounsellorDTO();
			BeanUtils.copyProperties(entity, dto);
			return dto;
		}
		
		return null;
	}

	@Override
	public boolean uniqueEmailCheck(String email) {
		// TODO Auto-generated method stub
		
		
		CounsellorEntity entity = councellorRepo.findByEmail(email);
		
		return entity == null;
	}

	@Override
	public boolean register(CounsellorDTO counsellorDTO) {
		// TODO Auto-generated method stub
		
		
		CounsellorEntity entity = new CounsellorEntity();
		BeanUtils.copyProperties(counsellorDTO, entity);
		
		CounsellorEntity savedentity = councellorRepo.save(entity);
		return null != savedentity.getCounsellorId();
	}

	
}
