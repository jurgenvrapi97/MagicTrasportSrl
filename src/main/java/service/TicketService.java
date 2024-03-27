package service;


import Dao.TicketDao;
import entities.Ticket;
import jakarta.persistence.EntityManager;

public class TicketService {

    private final TicketDao ticketDAO;

    public TicketService(EntityManager entityManager) {
        this.ticketDAO = new TicketDao(entityManager);
    }

    public void saveTicket(Ticket ticket) {
        ticketDAO.saveTicket(ticket);
    }

    public Ticket findTicketById(int ticketId) {
        return ticketDAO.findTicketById(ticketId);
    }

}