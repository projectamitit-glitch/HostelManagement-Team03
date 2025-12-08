package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.Bed;

public interface BedRepository extends JpaRepository<Bed, Integer> {

	@Query("SELECT b FROM Bed b WHERE b.status = 'AVAILABLE' AND b.room.sharing = :sharing AND b.room.floor.building.hostel.id = :hostelId")
	List<Bed> findAvailableBedsByRoomSharing(@Param("sharing") int sharing, @Param("hostelId") int hostelId);
}
