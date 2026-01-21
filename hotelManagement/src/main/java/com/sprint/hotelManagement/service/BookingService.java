package com.sprint.hotelManagement.service;

import com.sprint.hotelManagement.entity.Booking;
import com.sprint.hotelManagement.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {
        if (booking.getBookingDate() == null) {
            booking.setBookingDate(java.time.LocalDateTime.now());
        }
        if (booking.getStatus() == null) {
            booking.setStatus("CONFIRMED");
        }
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public Booking updateBookingStatus(Long id, String status) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            booking.setStatus(status);
            return bookingRepository.save(booking);
        }
        return null;
    }

    public void cancelBooking(Long id) {
        updateBookingStatus(id, "CANCELLED");
    }
}
