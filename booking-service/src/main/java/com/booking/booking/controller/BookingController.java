package com.booking.booking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking.booking.dto.BookingRequest;
import com.booking.booking.dto.BookingResponse;
import com.booking.booking.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin("*")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request) {
        BookingResponse response = bookingService.createBooking(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable Long id, @RequestHeader("X-Client-Email") String clientEmail) {
        BookingResponse response = bookingService.getBooking(id, clientEmail);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(@RequestHeader("X-Client-Email") String clientEmail) {
        List<BookingResponse> response = bookingService.getAllBookings(clientEmail);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long id, @RequestHeader("X-Client-Email") String clientEmail, @RequestParam String reason) {
        BookingResponse response = bookingService.cancelBooking(id, clientEmail, reason);
        return ResponseEntity.ok(response);
    }
}
