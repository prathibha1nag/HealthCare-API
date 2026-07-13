package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.dto.CreatePrescriptionRequest;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Prescription;
import com.example.demo.exceptionhandler.ResourceNotFoundException;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.PrescriptionRepository;

@Service
public class PrescriptionService {

    private final PrescriptionRepository repo;
    private final AppointmentRepository appointmentRepo;

    public PrescriptionService(PrescriptionRepository repo, AppointmentRepository appointmentRepo) {
        this.repo = repo;
        this.appointmentRepo = appointmentRepo;
    }

    public List<Prescription> listAll() {
        return repo.findAll();
    }

    public Prescription getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Prescription not found with id " + id));
    }

    public Prescription create(CreatePrescriptionRequest request) {
        Appointment appointment = appointmentRepo.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id " + request.getAppointmentId()));
        Prescription prescription = Prescription.builder()
                .appointment(appointment)
                .medication(request.getMedication())
                .dosage(request.getDosage())
                .instructions(request.getInstructions())
                .build();
        return repo.save(prescription);
    }

    public Prescription update(Long id, Prescription updates) {
        Prescription existing = getById(id);
        existing.setMedication(updates.getMedication());
        existing.setDosage(updates.getDosage());
        existing.setInstructions(updates.getInstructions());
        return repo.save(existing);
    }

    public void delete(Long id) {
        Prescription existing = getById(id);
        repo.delete(existing);
    }
}
