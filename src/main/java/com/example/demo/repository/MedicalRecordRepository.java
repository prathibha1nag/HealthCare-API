package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

	@Query("SELECT m FROM MedicalRecord m WHERE m.patient.id = :patientId")
	List<MedicalRecord> findByPatientId(@Param("patientId") Long patientId);
}
