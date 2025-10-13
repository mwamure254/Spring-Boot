package com.mfano.moe.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.moe.security.models.ServiceStatus;

public interface ServiceStatusRepository extends JpaRepository<ServiceStatus, Integer>{

}
