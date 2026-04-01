package com.booking.booking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.booking.booking.dto.BookingRequest;
import com.booking.booking.dto.BookingResponse;
import com.booking.booking.entity.Booking;
import com.booking.booking.entity.TimeSlot;
import com.booking.booking.repository.BookingRepository;
import com.booking.booking.repository.TimeSlotRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final TimeSlotRepository timeSlotRepository;

    public BookingResponse createBooking(BookingRequest request) {
        TimeSlot timeSlot = timeSlotRepository.findById(request.getTimeSlotId())
        .orElseThrow(() -> new RuntimeException("Time slot not found"));

        if(!timeSlot.getAvailable()) {
            throw new RuntimeException("Time slot is not available");
        }

        Booking booking = new Booking();

        booking.setTimeSlot(timeSlot);
        booking.setService(timeSlot.getService());
        booking.setClientEmail(request.getClientEmail());
        booking.setClientName(request.getClientName());
        booking.setClientPhone(request.getClientPhone());
        booking.setStatus(Booking.BookingStatus.PENDING);
        booking.setTotalPrice(timeSlot.getService().getPrice());
        booking.setCreatedAt(LocalDateTime.now());
        booking.setActive(true);

        Booking savedBooking = bookingRepository.save(booking);

        timeSlot.setAvailable(false);
        timeSlotRepository.save(timeSlot);
        
        return mapToResponse(savedBooking);
    }

    public BookingResponse getBooking(Long id, String clientEmail) {
        Booking booking = bookingRepository.findByIdAndClientEmail(id, clientEmail)
        .orElseThrow(() -> new RuntimeException("Booking not found"));
        return mapToResponse(booking);
    }

    public BookingResponse cancelBooking(Long id, String clientEmail, String reason) {
        Booking booking = bookingRepository.findByIdAndClientEmail(id, clientEmail)
        .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() == Booking.BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking is already cancelled");
        }

        booking.setStatus(Booking.BookingStatus.CANCELLED);
        booking.setCancelledAt(LocalDateTime.now());
        booking.setCancellationReason(reason);
        bookingRepository.save(booking);

        TimeSlot timeSlot = booking.getTimeSlot();
        timeSlot.setAvailable(true);
        timeSlotRepository.save(timeSlot);

        return mapToResponse(booking);
    }

    public List<BookingResponse> getAllBookings(String clientEmail) {
        List<Booking> bookings = bookingRepository.findByClientEmail(clientEmail);
        return bookings.stream().map(b -> mapToResponse(b)).toList();
    }

    private BookingResponse mapToResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getService().getName(),
                booking.getTimeSlot().getStartTime(),
                booking.getTimeSlot().getEndTime(),
                booking.getClientName(),
                booking.getStatus().toString(),
                booking.getTotalPrice(),
                booking.getCreatedAt()
        );
    }
}
