package com.booking.booking.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.booking.entity.TimeSlot;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByServiceIdAndAvailableTrue(Long serviceId);

    List<TimeSlot> findByServiceIdAndStartTimeGreaterThanAndEndTimeLessThenAndAvailableTrue(
            Long serviceId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
}
