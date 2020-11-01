package com.fiap.ralfmed.ticketamazonservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.ralfmed.ticketamazonservice.dto.TicketDTO;
import com.fiap.ralfmed.ticketamazonservice.entity.Ticket;

import com.fiap.ralfmed.ticketamazonservice.service.TicketService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @HystrixCommand(fallbackMethod  = "singleTicketFallback2",
		commandProperties=
		{@HystrixProperty(
				name="execution.isolation.thread.timeoutInMilliseconds",value="2000")})
    public Ticket createCase(@RequestBody TicketDTO ticketDTO){
        return ticketService.create(ticketDTO);
    }

    @GetMapping("/findById/{id}")
    @ResponseStatus(HttpStatus.OK)
    @HystrixCommand(fallbackMethod  = "singleTicketFallback")
    public TicketDTO findById(@PathVariable(name = "id") Long id){
        return ticketService.findById(id);
    }

    @GetMapping("/findByType/{type}")
    @ResponseStatus(HttpStatus.OK)
    @HystrixCommand(fallbackMethod  = "listFallbackReturn")
    public List<Ticket> findByType(@PathVariable(name = "type") String type){
    	
        return ticketService.findByType(type);
    }
    
    public TicketDTO singleTicketFallback(Long lg) {
		Ticket ticket = new Ticket();
		ticket.subject = "Erro ao cadastrar.";
		ticket.description = "Tente novamente mais tarde ou contate o administrador do sistema.";
		
		return TicketDTO.convertTicketDto(ticket);
	}
    
    public Ticket singleTicketFallback2(TicketDTO tckt) {
		Ticket ticket = new Ticket();
		ticket.subject = "Erro ao cadastrar.";
		ticket.description = "Tente novamente mais tarde ou contate o administrador do sistema.";
		
		return ticket;
	}
    
    public List<Ticket> listFallbackReturn(String str){
    	List<Ticket> ticketList = null;
    	
    	Ticket ticket = new Ticket();
		ticket.subject = "Erro ao cadastrar.";
		ticket.description = "Tente novamente mais tarde ou contate o administrador do sistema.";
    
		ticketList.add(ticket);
		return ticketList;
    }
    
}
