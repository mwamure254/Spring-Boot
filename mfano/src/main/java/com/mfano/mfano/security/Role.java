package com.mfano.mfano.security;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;

	private String description;
	private String details;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}

}
