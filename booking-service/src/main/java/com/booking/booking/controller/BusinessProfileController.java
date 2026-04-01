package com.booking.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.booking.dto.BusinessProfileRequest;
import com.booking.booking.dto.BusinessProfileResponse;
import com.booking.booking.service.BusinessProfileService;

@RestController
@RequestMapping("/api/business-profiles")
@CrossOrigin("*")
public class BusinessProfileController {
    @Autowired
    private BusinessProfileService businessProfileService;

    @PostMapping("/create")
    public ResponseEntity<BusinessProfileResponse> createBusinessProfile(@RequestBody BusinessProfileRequest request, @RequestHeader("X-Owner-Email") String ownerEmail) {
        BusinessProfileResponse response = businessProfileService.createBusinessProfile(request, ownerEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/my-profile")
    public ResponseEntity<BusinessProfileResponse> getMyProfile(@RequestHeader("X-Owner-Email") String ownerEmail) {
        BusinessProfileResponse response = businessProfileService.getBusinessProfile(ownerEmail);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<BusinessProfileResponse> getPublicProfile(@PathVariable Long id) {
        BusinessProfileResponse response = businessProfileService.getBusinessProfileById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BusinessProfileResponse> updateBusinessProfile(@PathVariable Long id, @RequestHeader("X-Owner-Email") String ownerEmail, @RequestBody BusinessProfileRequest request) {
        BusinessProfileResponse response = businessProfileService.updateBusinessProfile(id, ownerEmail, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBusinessProfile(@PathVariable Long id, @RequestHeader("X-Owner-Email") String ownerEmail) {
        businessProfileService.deleteBusinessProfile(id, ownerEmail);
        return ResponseEntity.ok("Business profile deleted successfully");
    }

    @GetMapping("/public/all")
    public ResponseEntity<List<BusinessProfileResponse>> getAllActiveBusinessProfiles() {
        List<BusinessProfileResponse> response = businessProfileService.getAllActiveBusinessProfiles();
        return ResponseEntity.ok(response);
    }
}
