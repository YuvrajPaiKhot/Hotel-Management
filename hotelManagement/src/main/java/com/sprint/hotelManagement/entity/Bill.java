package com.sprint.hotelManagement.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Bill {
	private Long id;

	private Booking booking;

	private LocalDateTime issueDate;

	private Double totalAmount;

	private String paymentStatus; // PAID, PENDING

	private String details; // JSON or text description of itemized charges

	public Bill(Booking booking, Double totalAmount, String paymentStatus, String details) {
		this.booking = booking;
		this.issueDate = LocalDateTime.now();
		this.totalAmount = totalAmount;
		this.paymentStatus = paymentStatus;
		this.details = details;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public LocalDateTime getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDateTime issueDate) {
		this.issueDate = issueDate;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
