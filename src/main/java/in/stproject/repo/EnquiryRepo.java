package in.stproject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.stproject.entity.EnquiryEntity;

public interface EnquiryRepo extends JpaRepository<EnquiryEntity, Integer>{

	
	//Select  * from enq_tbl where counsellor_id =: id
	public List<EnquiryEntity> findByCounsellorCounsellorId(Integer counsellorId);
}
