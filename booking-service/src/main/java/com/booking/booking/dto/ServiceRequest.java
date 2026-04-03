package com.booking.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequest {
    private Long businessProfileId;
    private String name;
    private String description;
    private Double price;
    private Integer durationMinutes;
}
