package com.booking.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessProfileRequest {
    private String businessName;
    private String category;
    private String description;
    private String phoneNumber;
    private String address;
}
