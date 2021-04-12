package com.practice.ads.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.practice.ads.model.Advertisement;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long>{

	Page<Advertisement> findByAdminId(Long id, Pageable page);
	
	@Query("SELECT a FROM Advertisement a WHERE "
			+ "(:title IS NULL or a.title like :title ) AND "
			+ "(:location IS NULL or a.location like :location ) AND "
			+ "(:username IS NULL OR a.admin.username = :username)"
			)
	Page<Advertisement> search(
			@Param("title") String title,
			@Param("location") String location,
			@Param("username") String username,
			Pageable page
			);
}
