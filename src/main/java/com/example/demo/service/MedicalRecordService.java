package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.entity.MedicalRecord;
import com.example.demo.entity.Patient;
import com.example.demo.exceptionhandler.ResourceNotFoundException;
import com.example.demo.repository.MedicalRecordRepository;
import com.example.demo.repository.PatientRepository;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository repo;
    private final PatientRepository patientRepo;

    public MedicalRecordService(MedicalRecordRepository repo, PatientRepository patientRepo) {
        this.repo = repo;
        this.patientRepo = patientRepo;
    }

    public List<MedicalRecord> listAll() {
        return repo.findAll();
    }

    public MedicalRecord getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medical record not found with id " + id));
    }

    public MedicalRecord create(MedicalRecord medicalRecord) {
        Patient patient = patientRepo.findById(medicalRecord.getPatient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + medicalRecord.getPatient().getId()));
        medicalRecord.setId(null);
        medicalRecord.setPatient(patient);
        return repo.save(medicalRecord);
    }

    public MedicalRecord update(Long id, MedicalRecord updates) {
        MedicalRecord existing = getById(id);
        existing.setDiagnosis(updates.getDiagnosis());
        existing.setTreatment(updates.getTreatment());
        existing.setRecordDate(updates.getRecordDate());
        return repo.save(existing);
    }

    public void delete(Long id) {
        MedicalRecord existing = getById(id);
        repo.delete(existing);
    }
}
