package com.example.DakachaPry.accounts.services;

import com.example.DakachaPry.accounts.models.InvoiceStatus;
import com.example.DakachaPry.accounts.repositories.InvoiceStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceStatusService {

	@Autowired
	private InvoiceStatusRepository invoiceStatusRepository;

	// Get All InvoiceStatuss
	public List<InvoiceStatus> findAll() {
		return invoiceStatusRepository.findAll();
	}

	// Get InvoiceStatus By Id
	public Optional<InvoiceStatus> findById(int id) {
		return invoiceStatusRepository.findById(id);
	}

	// Delete InvoiceStatus
	public void delete(int id) {
		invoiceStatusRepository.deleteById(id);
	}

	// Update InvoiceStatus
	public void save(InvoiceStatus invoiceStatus) {
		invoiceStatusRepository.save(invoiceStatus);
	}

}
