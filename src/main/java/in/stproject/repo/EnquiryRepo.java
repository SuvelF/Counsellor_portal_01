package in.stproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.stproject.entity.EnquiryEntity;

public interface EnquiryRepo extends JpaRepository<EnquiryEntity, Integer>{

}
