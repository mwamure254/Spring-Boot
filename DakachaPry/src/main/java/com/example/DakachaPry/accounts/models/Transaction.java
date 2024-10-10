package com.example.DakachaPry.accounts.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.example.DakachaPry.hr.models.Employee;
import com.example.DakachaPry.parameters.models.Client;
import com.example.DakachaPry.parameters.models.Contact;
import com.example.DakachaPry.parameters.models.Supplier;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double amount;
    private String purpose;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date transactionDate;

    @ManyToOne
    @JoinColumn(name = "transactionstatusid", insertable = false, updatable = false)
    private TransactionStatus transactionStatus;
    private Integer transactionstatusid;

    @ManyToOne
    @JoinColumn(name = "transactiontypeid", insertable = false, updatable = false)
    private TransactionType transactionType;
    private Integer transactiontypeid;

    @ManyToOne
    @JoinColumn(name = "contactid", insertable = false, updatable = false)
    private Contact contact;
    private Integer contactid;

    @ManyToOne
    @JoinColumn(name = "supplierid", insertable = false, updatable = false)
    private Supplier supplier;
    private Integer supplierid;

    @ManyToOne
    @JoinColumn(name = "clientid", insertable = false, updatable = false)
    private Client client;
    private Integer clientid;

    @ManyToOne
    @JoinColumn(name = "employeeid", insertable = false, updatable = false)
    private Employee approvedBy;
    private Integer employeeid;

    private String remarks;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Integer getTransactionstatusid() {
		return transactionstatusid;
	}

	public void setTransactionstatusid(Integer transactionstatusid) {
		this.transactionstatusid = transactionstatusid;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public Integer getTransactiontypeid() {
		return transactiontypeid;
	}

	public void setTransactiontypeid(Integer transactiontypeid) {
		this.transactiontypeid = transactiontypeid;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Integer getContactid() {
		return contactid;
	}

	public void setContactid(Integer contactid) {
		this.contactid = contactid;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Integer getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(Integer supplierid) {
		this.supplierid = supplierid;
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

	public Employee getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Employee approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Integer getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(Integer employeeid) {
		this.employeeid = employeeid;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
