package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

	@Query("SELECT p FROM Prescription p WHERE p.appointment.id = :appointmentId")
	Optional<Prescription> findByAppointmentId(@Param("appointmentId") Long appointmentId);
}
