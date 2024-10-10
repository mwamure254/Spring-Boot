package com.mfano.mfano.models;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class VehicleHire {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "vehicleid", insertable = false, updatable = false)
	private Vehicle vehicle;
	private Integer vehicleid;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOut;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateIn;

	// @DateTimeFormat(pattern = "HH:mm a")
	private String timeIn;

	// @DateTimeFormat(pattern = "HH:mm a")
	private String timeOut;

	@ManyToOne
	@JoinColumn(name = "clientid", insertable = false, updatable = false)
	private Client client;
	private Integer clientid;

	@ManyToOne
	@JoinColumn(name = "locationid", insertable = false, updatable = false)
	private Location location;
	private Integer locationid;

	private String price;
	private String remarks;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public Integer getVehicleid() {
		return vehicleid;
	}
	public void setVehicleid(Integer vehicleid) {
		this.vehicleid = vehicleid;
	}
	public LocalDate getDateOut() {
		return dateOut;
	}
	public void setDateOut(LocalDate dateOut) {
		this.dateOut = dateOut;
	}
	public LocalDate getDateIn() {
		return dateIn;
	}
	public void setDateIn(LocalDate dateIn) {
		this.dateIn = dateIn;
	}
	public String getTimeIn() {
		return timeIn;
	}
	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}
	public String getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Integer getClientid() {
		return clientid;
	}
	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Integer getLocationid() {
		return locationid;
	}
	public void setLocationid(Integer locationid) {
		this.locationid = locationid;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
