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
import com.example.demo.dto.CreateBillingRequest;
import com.example.demo.entity.Billing;
import com.example.demo.service.BillingService;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private final BillingService service;

    public BillingController(BillingService service) {
        this.service = service;
    }

    @GetMapping
    public List<Billing> listAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public Billing getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Billing create(@Valid @RequestBody CreateBillingRequest request) {
        Billing savedBilling = service.create(request);
        return savedBilling;
    }

    @PutMapping("/{id}")
    public Billing update(@PathVariable Long id, @Valid @RequestBody Billing billing) {
        Billing updatedBilling = service.update(id, billing);
        return updatedBilling;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
