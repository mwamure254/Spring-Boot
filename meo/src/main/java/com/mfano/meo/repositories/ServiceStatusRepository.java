package com.mfano.meo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfano.meo.models.ServiceStatus;

@Repository
public interface ServiceStatusRepository extends JpaRepository<ServiceStatus, Integer>{

}
