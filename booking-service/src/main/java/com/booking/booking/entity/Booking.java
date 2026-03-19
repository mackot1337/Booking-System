package com.booking.booking.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@RequiredArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String clientEmail;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    private String clientPhone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(nullable = false)
    private Double totalPrice;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime cancelledAt;

    @Column(nullable = true)
    private String cancellationReason;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean active = true;

    public enum BookingStatus {
        PENDING,      
        CONFIRMED,    
        CANCELLED,    
        COMPLETED    
    }

    @OneToOne
    @JoinColumn(name = "time_slot_id", nullable = false, unique = true)
    private TimeSlot timeSlot;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;
}