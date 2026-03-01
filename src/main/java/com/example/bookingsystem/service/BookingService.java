package com.example.bookingsystem.service;

import com.example.bookingsystem.model.Booking;
import com.example.bookingsystem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(Booking bookingRequest) {
        if (bookingRequest.getStartTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Booking must be for a future date and time.");
        }
        if (Duration.between(bookingRequest.getStartTime(), bookingRequest.getEndTime()).toMinutes() < 10) {
            throw new IllegalArgumentException("Minimum booking duration is 10 minutes.");
        }
        List<Booking> overlaps = bookingRepository.findOverlappingBookings(
                bookingRequest.getRoom().getId(),
                bookingRequest.getStartTime(),
                bookingRequest.getEndTime()
        );
        if (!overlaps.isEmpty()) {
            throw new IllegalStateException("The selected time slot is not available for this room.");
        }
        bookingRequest.setStatus("PENDING");
        return bookingRepository.save(bookingRequest);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking updateBooking(Long id, Booking bookingDetails) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStartTime(bookingDetails.getStartTime());
        booking.setEndTime(bookingDetails.getEndTime());
        booking.setAgenda(bookingDetails.getAgenda());
        booking.setStatus(bookingDetails.getStatus());
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}