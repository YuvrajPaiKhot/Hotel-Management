package com.sprint.hotelManagement.service;

import com.sprint.hotelManagement.entity.Complaint;
import com.sprint.hotelManagement.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {
    @Autowired
    private ComplaintRepository complaintRepository;

    public Complaint registerComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    public List<Complaint> getComplaintsByUser(Long userId) {
        return complaintRepository.findByUserId(userId);
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint updateComplaintStatus(Long id, String status, String resolution) {
        Complaint complaint = complaintRepository.findById(id).orElse(null);
        if (complaint != null) {
            complaint.setStatus(status);
            if (resolution != null)
                complaint.setResolutionNotes(resolution);
            return complaintRepository.save(complaint);
        }
        return null;
    }
}
