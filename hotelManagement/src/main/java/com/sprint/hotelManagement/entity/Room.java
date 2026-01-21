package com.sprint.hotelManagement.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Room {
	private Long id;

	private String roomNumber;

	private String type; // Standard, Deluxe, Suite

	private Double price;

	private boolean isAvailable;

	private Integer maxOccupancy;

	private String description;

	private String amenities; // Comma separated

	public Room(String roomNumber, String type, Double price, boolean isAvailable, Integer maxOccupancy,
			String description, String amenities) {
		this.roomNumber = roomNumber;
		this.type = type;
		this.price = price;
		this.isAvailable = isAvailable;
		this.maxOccupancy = maxOccupancy;
		this.description = description;
		this.amenities = amenities;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Integer getMaxOccupancy() {
		return maxOccupancy;
	}

	public void setMaxOccupancy(Integer maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAmenities() {
		return amenities;
	}

	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}
}
