package com.example.MfanoFleet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MfanoFleet.models.InvoiceStatus;

@Repository
public interface InvoiveStatusRepository extends JpaRepository<InvoiceStatus, Integer> {

}
