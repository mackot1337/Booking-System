package com.booking.booking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.booking.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByClientEmail(String email);   

    List<Booking> findByServiceId(Long serviceId);

    Optional<Booking> findByIdAndClientEmail(Long id, String email);
}
