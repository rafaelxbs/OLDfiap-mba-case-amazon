package com.fiap.ralfmed.ticketamazonservice.repository;

import java.util.List;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.ralfmed.ticketamazonservice.entity.Ticket;

@EntityScan(basePackages = {"com.fiap.ralfmed.ticketamazonservice.entity"})

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByType(String type);
}
