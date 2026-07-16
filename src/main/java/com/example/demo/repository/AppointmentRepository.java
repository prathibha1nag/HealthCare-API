package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	@Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId")
	List<Appointment> findByDoctorId(@Param("doctorId") Long doctorId);

	@Query("SELECT a FROM Appointment a WHERE a.patient.id = :patientId")
	List<Appointment> findByPatientId(@Param("patientId") Long patientId);

	@Query("SELECT a FROM Appointment a WHERE a.appointmentDate > :fromDate")
	List<Appointment> findFutureAppointments(@Param("fromDate") LocalDateTime fromDate);

}
