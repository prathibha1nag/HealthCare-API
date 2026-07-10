package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Patient;
import com.example.demo.exceptionhandler.ResourceNotFoundException;
import com.example.demo.repository.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository repo;

    public PatientService(PatientRepository repo) {
        this.repo = repo;
    }

    public List<Patient> listAll() {
        return repo.findAll();
    }

    public Patient getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));
    }

    public Patient create(Patient patient) {
        patient.setId(null);
        return repo.save(patient);
    }

    public Patient update(Long id, Patient updates) {
        Patient existing = getById(id);
        existing.setName(updates.getName());
        existing.setDob(updates.getDob());
        existing.setGender(updates.getGender());
        existing.setContact(updates.getContact());
        existing.setAddress(updates.getAddress());
        return repo.save(existing);
    }

    public void delete(Long id) {
        Patient existing = getById(id);
        repo.delete(existing);
    }
}
