package com.example.demo.dto;

import com.example.demo.enums.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAppointmentRequest {
    @NotNull(message = "AppointId is required")
    private Long appointmentId;

    @NotNull(message = "Doctor id is required")
    private Long doctorId;


    @NotBlank(message = "Appointment date is required")
    private String appointmentDate;

    @NotBlank(message = "Appointment reason is required")
    private String reason;

    @NotNull(message = "Appointment status is required")
    private Status status;
}
