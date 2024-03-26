package service;


import Dao.TicketDAO;
import entities.Ticket;
import jakarta.persistence.EntityManager;

public class TicketService {

    private final TicketDAO ticketDAO;

    public TicketService(EntityManager entityManager) {
        this.ticketDAO = new TicketDAO(entityManager);
    }

    public void saveTicket(Ticket ticket) {
        ticketDAO.saveTicket(ticket);
    }

    public Ticket findTicketById(int ticketId) {
        return ticketDAO.findTicketById(ticketId);
    }

}