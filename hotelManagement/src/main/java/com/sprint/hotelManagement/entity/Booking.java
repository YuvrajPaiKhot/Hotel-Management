package com.sprint.hotelManagement.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Booking {
	private Long id;

	private User user;

	private Room room;

	private LocalDate checkInDate;

	private LocalDate checkOutDate;

	private Integer numAdults;

	private Integer numChildren;

	private String status; // CONFIRMED, CHECKED_IN, CANCELLED, COMPLETED

	private Double totalAmount;

	private LocalDateTime bookingDate;

	public Booking(User user, Room room, LocalDate checkInDate, LocalDate checkOutDate, Integer numAdults,
			Integer numChildren, Double totalAmount) {
		this.user = user;
		this.room = room;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.numAdults = numAdults;
		this.numChildren = numChildren;
		this.totalAmount = totalAmount;
		this.status = "CONFIRMED";
		this.bookingDate = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Integer getNumAdults() {
		return numAdults;
	}

	public void setNumAdults(Integer numAdults) {
		this.numAdults = numAdults;
	}

	public Integer getNumChildren() {
		return numChildren;
	}

	public void setNumChildren(Integer numChildren) {
		this.numChildren = numChildren;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public LocalDateTime getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDateTime bookingDate) {
		this.bookingDate = bookingDate;
	}

}
