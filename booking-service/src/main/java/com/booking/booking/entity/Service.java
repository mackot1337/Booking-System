package com.booking.booking.entity;
 
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
 
@Entity
@Table(name = "services")
@Data
@RequiredArgsConstructor
public class Service {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(nullable = false)
    private String name;
 
    @Column(nullable = false)
    private String description;
 
    @Column(nullable = false)
    private Double price;
 
    @Column(nullable = false)
    private Integer durationMinutes;
 
    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "business_profile_id", nullable = false)
    private BusinessProfile businessProfile;

    @OneToMany(mappedBy = "service")
    private Set<TimeSlot> timeSlots;

    @OneToMany(mappedBy = "service")
    private Set<Booking> bookings;
}
 