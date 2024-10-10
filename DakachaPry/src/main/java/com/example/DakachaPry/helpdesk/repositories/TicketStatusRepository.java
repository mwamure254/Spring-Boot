package com.example.DakachaPry.helpdesk.repositories;

import com.example.DakachaPry.helpdesk.models.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketStatusRepository extends JpaRepository<TicketStatus, Integer> {

}
