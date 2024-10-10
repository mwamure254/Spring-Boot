package com.example.DakachaPry.helpdesk.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.example.DakachaPry.hr.models.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String subject;
	private String details;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date openDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date closeDate;

	@ManyToOne
	@JoinColumn(name = "ticketstatusid", insertable = false, updatable = false)
	private TicketStatus ticketStatus;
	private Integer ticketstatusid;

	@ManyToOne
	@JoinColumn(name = "employeeid", insertable = false, updatable = false)
	private Employee raisedBy;
	private Integer employeeid;

	@ManyToOne
	@JoinColumn(name = "employeeid", insertable = false, updatable = false)
	private Employee asignedTo;

	private String remarks;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public TicketStatus getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(TicketStatus ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public Integer getTicketstatusid() {
		return ticketstatusid;
	}

	public void setTicketstatusid(Integer ticketstatusid) {
		this.ticketstatusid = ticketstatusid;
	}

	public Employee getRaisedBy() {
		return raisedBy;
	}

	public void setRaisedBy(Employee raisedBy) {
		this.raisedBy = raisedBy;
	}

	public Integer getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(Integer employeeid) {
		this.employeeid = employeeid;
	}

	public Employee getAsignedTo() {
		return asignedTo;
	}

	public void setAsignedTo(Employee asignedTo) {
		this.asignedTo = asignedTo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
