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
import com.example.demo.dto.CreateMedicalRecordRequest;
import com.example.demo.entity.MedicalRecord;
import com.example.demo.service.MedicalRecordService;

@RestController
@RequestMapping("/api/records")
public class MedicalRecordController {

    private final MedicalRecordService service;

    public MedicalRecordController(MedicalRecordService service) {
        this.service = service;
    }

    @GetMapping
    public List<MedicalRecord> listAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public MedicalRecord getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public MedicalRecord create(@Valid @RequestBody CreateMedicalRecordRequest request) {
        MedicalRecord savedRecord = service.create(request);
        return savedRecord;
    }

    @PutMapping("/{id}")
    public MedicalRecord update(@PathVariable Long id, @Valid @RequestBody MedicalRecord medicalRecord) {
        MedicalRecord updatedRecord = service.update(id, medicalRecord);
        return updatedRecord;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
