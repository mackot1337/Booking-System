package com.booking.booking.repository;

import org.springframework.stereotype.Repository;

import com.booking.booking.entity.Service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByBusinessId(Long businessId);
}
