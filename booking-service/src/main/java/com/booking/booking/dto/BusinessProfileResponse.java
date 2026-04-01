package com.booking.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessProfileResponse {
    private Long id;
    private String ownerEmail;
    private String businessName;
    private String category;
    private String description;
    private String phoneNumber;
    private String address;
    private Boolean active;
}
