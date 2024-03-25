package org.example;

import entities.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import service.TicketService;

public class Application {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MagicTrasportSrl");
        EntityManager em = emf.createEntityManager();


        Ticket foundTicket = getTicket(em);
        if (foundTicket != null) {
            System.out.println("Ticket trovato: " + foundTicket);
        } else {
            System.out.println("Nessun ticket trovato per l'ID specificato.");
        }


        em.close();
        emf.close();
    }

    private static Ticket getTicket(EntityManager em) {
        TicketService ticketService = new TicketService(em);


        Ticket ticket1 = new Ticket();
        ticket1.setUserId(1);
        ticket1.setValidita("2024-04-01");


        Ticket ticket2 = new Ticket();
        ticket2.setUserId(2);
        ticket2.setValidita("2024-04-02");


        // Salva i ticket nel database utilizzando il TicketService
        ticketService.saveTicket(ticket1);
        ticketService.saveTicket(ticket2);


        // Trova un ticket per ID
        Ticket foundTicket = ticketService.findTicketById(1);
        return foundTicket;
    }
}