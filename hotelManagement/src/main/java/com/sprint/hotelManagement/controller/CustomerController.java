package com.sprint.hotelManagement.controller;

import com.sprint.hotelManagement.entity.Bill;
import com.sprint.hotelManagement.entity.Booking;
import com.sprint.hotelManagement.entity.Complaint;
import com.sprint.hotelManagement.entity.Room;
import com.sprint.hotelManagement.entity.User;
import com.sprint.hotelManagement.repository.UserRepository;
import com.sprint.hotelManagement.security.UserDetailsImpl;
import com.sprint.hotelManagement.service.BillingService;
import com.sprint.hotelManagement.service.BookingService;
import com.sprint.hotelManagement.service.ComplaintService;
import com.sprint.hotelManagement.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
public class CustomerController {

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

    @GetMapping("/details")
    public User getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userRepository.findById(userDetails.getId()).orElse(null);
    }

    @PutMapping("/update-profile")
    public User updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody User userUpdate) {
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        if (user != null) {
            user.setMobile(userUpdate.getMobile());
            user.setAddress(userUpdate.getAddress());
            user.setEmail(userUpdate.getEmail());
            // Name update if needed? User entity has username which is login.
            // Assuming we added 'name' field or just using username.
            return userRepository.save(user);
        }
        return null;
    }

    @GetMapping("/rooms/search")
    public List<Room> searchRooms(
            @RequestParam("checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam("checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {
        return roomService.searchRooms(checkIn, checkOut);
    }

    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PostMapping("/book")
    public Booking bookRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody Booking booking) {
        // Fetch full user object or just set ID reference if Hibernat Proxy
        User user = userRepository.findById(userDetails.getId()).get();
        booking.setUser(user);

        // Ensure room is set in request body (id)
        return bookingService.createBooking(booking);
    }

    @GetMapping("/bookings")
    public List<Booking> getMyBookings(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return bookingService.getBookingsByUser(userDetails.getId());
    }

    @PutMapping("/bookings/{id}/cancel")
    public void cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
    }

    @GetMapping("/bills")
    public List<Bill> getMyBills(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return billingService.getBillsByUser(userDetails.getId());
    }

    @PostMapping("/complaints")
    public Complaint registerComplaint(@AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody Complaint complaint) {
        User user = userRepository.findById(userDetails.getId()).get();
        complaint.setUser(user);
        return complaintService.registerComplaint(complaint);
    }

    @GetMapping("/complaints")
    public List<Complaint> getMyComplaints(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return complaintService.getComplaintsByUser(userDetails.getId());
    }
}
