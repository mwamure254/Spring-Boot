package com.example.DakachaPry.helpdesk.repositories;

import com.example.DakachaPry.helpdesk.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}
