package com.sprint.hotelManagement.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Complaint {
	private Long id;

	private User user;

	private String title;

	private String description;

	private String category; // Room Issue, Service Issue, etc.

	private String status; // OPEN, IN_PROGRESS, RESOLVED, CLOSED

	private LocalDateTime submissionDate;

	private String resolutionNotes;

	public Complaint(User user, String title, String description, String category) {
		this.user = user;
		this.title = title;
		this.description = description;
		this.category = category;
		this.status = "OPEN";
		this.submissionDate = LocalDateTime.now();
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(LocalDateTime submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getResolutionNotes() {
		return resolutionNotes;
	}

	public void setResolutionNotes(String resolutionNotes) {
		this.resolutionNotes = resolutionNotes;
	}

}
