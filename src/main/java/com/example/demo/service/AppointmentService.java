package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.Patient;
import com.example.demo.exceptionhandler.ResourceNotFoundException;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;

@Service
public class AppointmentService {

    private final AppointmentRepository repo;
    private final PatientRepository patientRepo;
    private final DoctorRepository doctorRepo;

    public AppointmentService(AppointmentRepository repo, PatientRepository patientRepo, DoctorRepository doctorRepo) {
        this.repo = repo;
        this.patientRepo = patientRepo;
        this.doctorRepo = doctorRepo;
    }

    public List<Appointment> listAll() {
        return repo.findAll();
    }

    public Appointment getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id " + id));
    }

    public Appointment create(Appointment appointment) {
        Patient patient = patientRepo.findById(appointment.getPatient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + appointment.getPatient().getId()));
        Doctor doctor = doctorRepo.findById(appointment.getDoctor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + appointment.getDoctor().getId()));
        appointment.setId(null);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        return repo.save(appointment);
    }

    public Appointment update(Long id, Appointment updates) {
        Appointment existing = getById(id);
        if (updates.getAppointmentDate() != null) {
            existing.setAppointmentDate(updates.getAppointmentDate());
        }
        existing.setReason(updates.getReason());
        existing.setStatus(updates.getStatus());
        return repo.save(existing);
    }

    public void delete(Long id) {
        Appointment existing = getById(id);
        repo.delete(existing);
    }
}
