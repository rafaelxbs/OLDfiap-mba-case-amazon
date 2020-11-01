package com.fiap.ralfmed.ticketamazonservice.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.fiap.ralfmed.ticketamazonservice.dto.TicketDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@EntityScan(basePackages = {"com.fiap.ralfmed.ticketamazonservice.entity"})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ticket {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    
    public String subject;

    public String type;
    
    public String description;
    
    
    public static Ticket convertToTicket(TicketDTO ticketDTO){
    	
    	Ticket ticket = new Ticket();
    	ticket.setSubject(ticketDTO.getSubject());
    	ticket.setType(ticketDTO.getType());
    	ticket.setDescription(ticketDTO.getDescription());
    	return ticket;
    	
    }
}
