package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.dto.CreateBillingRequest;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Billing;
import com.example.demo.exceptionhandler.ResourceNotFoundException;
import com.example.demo.exceptionhandler.BadRequestException;
import java.time.LocalDateTime;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.BillingRepository;

@Service
public class BillingService {

    private final BillingRepository repo;
    private final AppointmentRepository appointmentRepo;

    public BillingService(BillingRepository repo, AppointmentRepository appointmentRepo) {
        this.repo = repo;
        this.appointmentRepo = appointmentRepo;
    }

    public List<Billing> listAll() {
        return repo.findAll();
    }

    public Billing getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Billing not found with id " + id));
    }

    public Billing create(CreateBillingRequest request) {
        Appointment appointment = appointmentRepo.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id " + request.getAppointmentId()));
        if (request.getBillingDate().isAfter(LocalDateTime.now())) {
            throw new BadRequestException("Billing date cannot be in the future");
        }

        Billing billing = Billing.builder()
                .appointment(appointment)
                .amount(request.getAmount())
                .status(request.getStatus())
                .billingDate(request.getBillingDate())
                .build();

        return repo.save(billing);
    }

    public Billing update(Long id, Billing updates) {
        Billing existing = getById(id);
        if (updates.getBillingDate() != null && updates.getBillingDate().isAfter(LocalDateTime.now())) {
            throw new BadRequestException("Billing date cannot be in the future");
        }
        existing.setAmount(updates.getAmount());
        existing.setStatus(updates.getStatus());
        existing.setBillingDate(updates.getBillingDate());
        return repo.save(existing);
    }

    public void delete(Long id) {
        Billing existing = getById(id);
        repo.delete(existing);
    }
}
