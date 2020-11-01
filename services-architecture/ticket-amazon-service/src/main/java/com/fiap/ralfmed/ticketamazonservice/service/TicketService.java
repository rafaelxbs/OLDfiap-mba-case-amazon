package com.fiap.ralfmed.ticketamazonservice.service;

import java.util.List;
import java.util.Optional;

import com.fiap.ralfmed.ticketamazonservice.dto.TicketDTO;
import com.fiap.ralfmed.ticketamazonservice.entity.Ticket;

public interface TicketService {

    Ticket create(TicketDTO ticketDTO);

    TicketDTO findById(Long id);

    List<Ticket> findByType(String type);
}
