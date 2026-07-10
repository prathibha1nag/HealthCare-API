package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Doctor;
import com.example.demo.exceptionhandler.ResourceNotFoundException;
import com.example.demo.repository.DoctorRepository;

@Service
public class DoctorService {

    private final DoctorRepository repo;

    public DoctorService(DoctorRepository repo) {
        this.repo = repo;
    }

    public List<Doctor> listAll() {
        return repo.findAll();
    }

    public Doctor getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + id));
    }

    public Doctor create(Doctor doctor) {
        doctor.setId(null);
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
}
