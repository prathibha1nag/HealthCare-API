package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);

    @Query("SELECT d FROM Doctor d WHERE d.specialization = :specialization")
    List<Doctor> findBySpecialization(@Param("specialization") String specialization);
}
