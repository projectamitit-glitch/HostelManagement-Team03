package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Bed;

public interface BedRepository extends JpaRepository<Bed, Integer> {

}
