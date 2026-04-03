package com.booking.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse {
    private Long id;
    private Long businessProfileId;
    private String name;
    private String description;
    private Double price;
    private Integer durationMinutes;
    private Boolean active;
}
