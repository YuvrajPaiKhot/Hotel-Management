package com.sprint.hotelManagement.controller;

import com.sprint.hotelManagement.entity.Complaint;
import com.sprint.hotelManagement.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/staff")
@PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
public class StaffController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/complaints")
    public List<Complaint> getComplaints() {
        return complaintService.getAllComplaints();
    }

    @PutMapping("/complaints/{id}/status")
    public Complaint updateComplaintStatus(@PathVariable Long id, @RequestParam String status) {
        return complaintService.updateComplaintStatus(id, status, null);
    }
}
