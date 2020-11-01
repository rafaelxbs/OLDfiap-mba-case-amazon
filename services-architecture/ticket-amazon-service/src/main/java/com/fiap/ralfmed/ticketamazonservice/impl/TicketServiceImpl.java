package com.fiap.ralfmed.ticketamazonservice.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fiap.ralfmed.ticketamazonservice.dto.TicketDTO;
import com.fiap.ralfmed.ticketamazonservice.entity.Ticket;
import com.fiap.ralfmed.ticketamazonservice.repository.TicketRepository;
import com.fiap.ralfmed.ticketamazonservice.service.TicketService;


@Service
public class TicketServiceImpl implements TicketService {

	private TicketRepository ticketRepository;

	public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket create(TicketDTO ticketDTO) {
        return ticketRepository.save(Ticket.convertToTicket(ticketDTO));
    }

    /*public ProductDTO findById(Long id) {
        Product product = (Product) productRepository.findById(id).get();
        //product.setMostViewed(product.getMostViewed()+1L);
        productRepository.save(product);
        return ProductDTO.convertProductDto(product);
    */
	@Override
	public TicketDTO findById(Long id) {
		Ticket ticket = (Ticket) ticketRepository.findById(id).get();
		return TicketDTO.convertTicketDto(ticket);
	}

	@Override
	public List<Ticket> findByType(String type) {
		// TODO Auto-generated method stub
		return ticketRepository.findByType(type);
	}


}
