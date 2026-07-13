package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.dto.CreateAppointmentRequest;
import com.example.demo.entity.Appointment;
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
        if (!patientRepo.existsById(request.getPatientId())) {
            throw new ResourceNotFoundException("Patient not found with id " + request.getPatientId());
        }
        if (!doctorRepo.existsById(request.getDoctorId())) {
            throw new ResourceNotFoundException("Doctor not found with id " + request.getDoctorId());
        }

        LocalDateTime appointmentDate;
        try {
            appointmentDate = LocalDateTime.parse(request.getAppointmentDate());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Appointment date must be in ISO format yyyy-MM-dd'T'HH:mm:ss");
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patientRepo.getReferenceById(request.getPatientId()));
        appointment.setDoctor(doctorRepo.getReferenceById(request.getDoctorId()));
        appointment.setAppointmentDate(appointmentDate);
        appointment.setReason(request.getReason());
        appointment.setStatus(request.getStatus());

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
