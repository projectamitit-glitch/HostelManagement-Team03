package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Hostel;

public interface HostelRepository extends JpaRepository<Hostel, Integer>{

}
