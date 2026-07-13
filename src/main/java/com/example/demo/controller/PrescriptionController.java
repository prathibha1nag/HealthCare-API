package com.example.demo.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.CreatePrescriptionRequest;
import com.example.demo.entity.Prescription;
import com.example.demo.service.PrescriptionService;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService service;

    public PrescriptionController(PrescriptionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Prescription> listAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public Prescription getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Prescription create(@Valid @RequestBody CreatePrescriptionRequest request) {
        Prescription savedPrescription = service.create(request);
        return savedPrescription;
    }

    @PutMapping("/{id}")
    public Prescription update(@PathVariable Long id, @Valid @RequestBody Prescription prescription) {
        Prescription updatedPrescription = service.update(id, prescription);
        return updatedPrescription;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
