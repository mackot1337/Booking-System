package com.booking.booking.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BookingDto {
    private Long TimeSlotId;
    private String clientEmail;
    private String clientName;
    private String clientPhone;
}
