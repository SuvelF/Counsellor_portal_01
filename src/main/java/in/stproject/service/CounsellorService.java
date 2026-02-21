package in.stproject.service;

import in.stproject.dto.CounsellorDTO;

public interface CounsellorService {

	// for valid login return id, for invalid return 0;
	
		public CounsellorDTO login(CounsellorDTO counsellorDTO);
		
		
		//if unique return true else return false
		
		public boolean uniqueEmailCheck(String email);
		
		
		public boolean register(CounsellorDTO counsellorDTO);

}
