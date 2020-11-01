package com.fiap.ralfmed.ticketamazonservice.dto;

import com.fiap.ralfmed.ticketamazonservice.entity.Ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    private String subject;

    private String type;
    
    private String description;
    
    public static TicketDTO convertTicketDto(Ticket ticket){
    	TicketDTO ticketDTO = new TicketDTO(ticket.getSubject(), ticket.getType(), ticket.getDescription());
        return ticketDTO;
    }
}
