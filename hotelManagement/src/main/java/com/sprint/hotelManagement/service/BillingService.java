package com.sprint.hotelManagement.service;

import com.sprint.hotelManagement.entity.Bill;
import com.sprint.hotelManagement.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingService {
    @Autowired
    private BillRepository billRepository;

    public Bill generateBill(Bill bill) {
        return billRepository.save(bill);
    }

    public List<Bill> getBillsByUser(Long userId) {
        return billRepository.findByBookingUserId(userId);
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Bill getBillById(Long id) {
        return billRepository.findById(id).orElse(null);
    }
}
