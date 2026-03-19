package com.booking.booking.entity;

import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.RequiredArgsConstructor;
 
@Entity
@Table(name = "business_profiles")
@Data
@RequiredArgsConstructor
public class BusinessProfile {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(nullable = false)
    @Email
    private String ownerEmail;
 
    @Column(nullable = false)
    private String businessName;
 
    @Column(nullable = false)
    private String category;
 
    @Column(nullable = false)
    private String description;
 
    @Column(nullable = false)
    private String phoneNumber;
 
    @Column(nullable = false)
    private String address;
 
    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean active = true;

    @OneToMany(mappedBy = "businessProfile")
    private Set<Service> services;
}
