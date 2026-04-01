package com.booking.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.booking.dto.BusinessProfileRequest;
import com.booking.booking.dto.BusinessProfileResponse;
import com.booking.booking.entity.BusinessProfile;
import com.booking.booking.repository.BusinessProfileRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class BusinessProfileService {
    
    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    public BusinessProfileResponse createBusinessProfile(BusinessProfileRequest request, String ownerEmail) {
        if (businessProfileRepository.findOwnerByOwnerEmail(ownerEmail).isPresent()) {
            throw new RuntimeException("Business profile already exists");
        }

        BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setOwnerEmail(ownerEmail);
        businessProfile.setBusinessName(request.getBusinessName());
        businessProfile.setCategory(request.getCategory());
        businessProfile.setDescription(request.getDescription());
        businessProfile.setPhoneNumber(request.getPhoneNumber());
        businessProfile.setAddress(request.getAddress());
        businessProfile.setActive(true);

        BusinessProfile savedBusinessProfile = businessProfileRepository.save(businessProfile);

        return mapToResponse(savedBusinessProfile);
    }

    public BusinessProfileResponse getBusinessProfile(String ownerEmail) {
        BusinessProfile businessProfile = businessProfileRepository.findOwnerByOwnerEmail(ownerEmail)
        .orElseThrow(() -> new RuntimeException("Business profile not found"));
        return mapToResponse(businessProfile);
    }

    public BusinessProfileResponse getBusinessProfileById(Long id) {
        BusinessProfile businessProfile = businessProfileRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Business profile not found"));
        return mapToResponse(businessProfile);
    }

    public BusinessProfileResponse updateBusinessProfile(Long id, String ownerEmail, BusinessProfileRequest request) {
        BusinessProfile businessProfile = businessProfileRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Business profile not found"));

        if (!businessProfile.getOwnerEmail().equals(ownerEmail)) {
            throw new RuntimeException("You are not authorized to update this business profile");
        }

        businessProfile.setBusinessName(request.getBusinessName());
        businessProfile.setCategory(request.getCategory());
        businessProfile.setDescription(request.getDescription());
        businessProfile.setPhoneNumber(request.getPhoneNumber());
        businessProfile.setAddress(request.getAddress());

        BusinessProfile savedBusinessProfile = businessProfileRepository.save(businessProfile);

        return mapToResponse(savedBusinessProfile);
    }

    public void deleteBusinessProfile(Long id, String ownerEmail) {
        BusinessProfile businessProfile = businessProfileRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Business profile not found"));

        if (!businessProfile.getOwnerEmail().equals(ownerEmail)) {
            throw new RuntimeException("You are not authorized to delete this business profile");
        }

        businessProfileRepository.delete(businessProfile);
    }

    public List<BusinessProfileResponse> getAllActiveBusinessProfiles() {
        return businessProfileRepository.findAllByActiveTrue()
        .stream()
        .map(b -> mapToResponse(b))
        .toList();
    }

    private BusinessProfileResponse mapToResponse(BusinessProfile businessProfile) {
        return new BusinessProfileResponse(
                businessProfile.getId(),
                businessProfile.getOwnerEmail(),
                businessProfile.getBusinessName(),
                businessProfile.getCategory(),
                businessProfile.getDescription(),
                businessProfile.getPhoneNumber(),
                businessProfile.getAddress(),
                businessProfile.getActive()
        );
    }
}
