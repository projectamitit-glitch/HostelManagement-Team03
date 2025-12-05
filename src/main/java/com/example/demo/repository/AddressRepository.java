package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

	@Query("SELECT a.hostel.id FROM Address a WHERE a.city LIKE %:city% AND a.area LIKE %:area%")
	List<Integer> findHostelIdsByCityAndArea(@Param("city") String city, @Param("area") String area);

	@Query("SELECT a.hostel.id FROM Address a WHERE a.area LIKE %:area%")
	List<Integer> findHostelIdsByArea(@Param("area") String area);

	@Query("SELECT a.hostel.id FROM Address a WHERE a.city LIKE %:city%")
	List<Integer> findHostelIdsByCity(@Param("city") String city);
}
