package com.sprint.hotelManagement.controller;

import com.sprint.hotelManagement.entity.*;
import com.sprint.hotelManagement.repository.UserRepository;
import com.sprint.hotelManagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BillingService billingService;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private UserRepository userRepository;

    // Room Management
    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PostMapping("/rooms")
    public Room addRoom(@RequestBody Room room) {
        return roomService.addRoom(room);
    }

    @PutMapping("/rooms/{id}")
    public Room updateRoom(@PathVariable Long id, @RequestBody Room room) {
        return roomService.updateRoom(id, room);
    }

    @DeleteMapping("/rooms/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }

    // Booking Management
    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PutMapping("/bookings/{id}")
    public Booking updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        // General update
        Booking existing = bookingService.getBookingById(id);
        if (existing != null) {
            existing.setStatus(booking.getStatus());
            // Update other fields as needed
            return bookingService.createBooking(existing);
        }
        return null;
    }

    @PutMapping("/bookings/{id}/status")
    public Booking updateBookingStatus(@PathVariable Long id, @RequestParam String status) {
        return bookingService.updateBookingStatus(id, status);
    }

    // User Management
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Bill Management
    @GetMapping("/bills")
    public List<Bill> getAllBills() {
        return billingService.getAllBills();
    }

    @PostMapping("/bills")
    public Bill createBill(@RequestBody Bill bill) {
        return billingService.generateBill(bill);
    }

    // Complaint Management (Admin view)
    @GetMapping("/complaints")
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    @PutMapping("/complaints/{id}/resolve")
    public Complaint resolveComplaint(@PathVariable Long id, @RequestBody String resolution) {
        return complaintService.updateComplaintStatus(id, "RESOLVED", resolution);
    }

}
