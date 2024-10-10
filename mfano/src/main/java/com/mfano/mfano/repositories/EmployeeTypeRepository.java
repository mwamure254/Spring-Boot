package com.mfano.mfano.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.mfano.models.EmployeeType;

public interface EmployeeTypeRepository extends JpaRepository<EmployeeType, Integer> {

}
