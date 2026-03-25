package com.booking.booking.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Long id;
    private String serviceName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String clientName;
    private String status;
    private Double totalPrice;
    private LocalDateTime createdAt;
}
