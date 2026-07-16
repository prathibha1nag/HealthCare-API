package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateAppointmentRequest;
import com.example.demo.dto.UpdateAppointmentRequest;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.Patient;
import com.example.demo.exceptionhandler.BadRequestException;
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

    public Appointment create(CreateAppointmentRequest request) {
        Patient patient = patientRepo.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + request.getPatientId()));
        Doctor doctor = doctorRepo.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + request.getDoctorId()));

        LocalDateTime appointmentDate = parseAppointmentDate(request.getAppointmentDate());
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setReason(request.getReason());
        appointment.setStatus(request.getStatus());

        return repo.save(appointment);
    }

    public Appointment update(Long id, UpdateAppointmentRequest request) {
        Appointment existing = getById(id);
        
        Doctor doctor = doctorRepo.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + request.getDoctorId()));

        LocalDateTime appointmentDate = parseAppointmentDate(request.getAppointmentDate());
        // existing.setPatient(patient); patient shudnt be changed
        existing.setDoctor(doctor);
        existing.setAppointmentDate(appointmentDate);
        existing.setReason(request.getReason());
        existing.setStatus(request.getStatus());
        return repo.save(existing);
    }

    public void delete(Long id) {
        Appointment existing = getById(id);
        repo.delete(existing);
    }

    public LocalDateTime parseAppointmentDate(String dateStr) {
        LocalDateTime appointmentDate;
        try {
            appointmentDate = LocalDateTime.parse(dateStr);
        } catch (DateTimeParseException ex) {
            throw new BadRequestException("Appointment date must be in ISO format yyyy-MM-dd'T'HH:mm:ss");
        }
        if (appointmentDate.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Appointment date must be in the future");
        }
        return appointmentDate;
    }
}
