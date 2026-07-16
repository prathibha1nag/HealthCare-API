package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Billing;

public interface BillingRepository extends JpaRepository<Billing, Long> {

	@Query("SELECT b FROM Billing b WHERE b.appointment.id = :appointmentId")
	Optional<Billing> findByAppointmentId(@Param("appointmentId") Long appointmentId);
}
