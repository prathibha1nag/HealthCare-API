package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateDoctorRequest;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Patient;
import com.example.demo.exceptionhandler.ResourceNotFoundException;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;

@Service
public class DoctorService {

    private final DoctorRepository repo;
    private final AppointmentRepository appointmentRepo;
    private final PatientRepository patientRepo;

    public DoctorService(DoctorRepository repo, AppointmentRepository appointmentRepo, PatientRepository patientRepo) {
        this.repo = repo;
        this.appointmentRepo = appointmentRepo;
        this.patientRepo = patientRepo;
    }

    public List<Doctor> listAll() {
        return repo.findAll();
    }

    public Doctor getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + id));
    }

    public Doctor create(CreateDoctorRequest request) {
        Doctor doctor = Doctor.builder()
                .name(request.getName())
                .specialization(request.getSpecialization())
                .phone(request.getPhone())
                .email(request.getEmail())
                .build();
        return repo.save(doctor);
    }

    public Doctor update(Long id, Doctor updates) {
        Doctor existing = getById(id);
        existing.setName(updates.getName());
        existing.setSpecialization(updates.getSpecialization());
        existing.setPhone(updates.getPhone());
        existing.setEmail(updates.getEmail());
        return repo.save(existing);
    }

    public void delete(Long id) {
        Doctor existing = getById(id);
        repo.delete(existing);
    }

    public List<Patient> getMyPatients(Long doctorId) {
        getById(doctorId);
        return patientRepo.findPatientsByDoctorId(doctorId);
    }

    public List<Appointment> getMyAllMyAppointments(Long doctorId) {
        getById(doctorId);
        return appointmentRepo.findByDoctorId(doctorId);
    }

    public List<Appointment> getAppointmentsByDate(Long doctorId, LocalDate date) {
        getById(doctorId);
        return appointmentRepo.findByDoctorIdAndAppointmentDate(
                doctorId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());
    }
}
