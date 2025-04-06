package com.mfano.mfano.mpesa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CallData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String merchantRequestID;
	private String checkoutRequestID;
	private String mpesaReceiptNumber;
	private int resultCode;
	private String resultDesc;
	private int amount;
	private String phoneNumber;
	private String transactionDate;
	
	//setters and getters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMerchantRequestID() {
		return merchantRequestID;
	}
	public void setMerchantRequestID(String merchantRequestID) {
		this.merchantRequestID = merchantRequestID;
	}
	public String getCheckoutRequestID() {
		return checkoutRequestID;
	}
	public void setCheckoutRequestID(String checkoutRequestID) {
		this.checkoutRequestID = checkoutRequestID;
	}
	public String getMpesaReceiptNumber() {
		return mpesaReceiptNumber;
	}
	public void setMpesaReceiptNumber(String mpesaReceiptNumber) {
		this.mpesaReceiptNumber = mpesaReceiptNumber;
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	
}
