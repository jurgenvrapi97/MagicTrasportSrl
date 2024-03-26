package org.example;

import entities.Abbonamento;
import entities.Distributore;
import entities.Ticket;
import enums.TipoAbbonamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import service.TicketService;

import java.time.LocalDate;

public class Application {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MagicTrasportSrl");
        EntityManager em = emf.createEntityManager();
        Abbonamento abb=new Abbonamento(LocalDate.now(),LocalDate.now().plusMonths(1), TipoAbbonamento.MENSILE, new Distributore());

        System.out.println( abb.isExpired());





        em.close();
        emf.close();

//        Ticket foundTicket = getTicket(em);
//        if (foundTicket != null) {
//            System.out.println("Ticket trovato: " + foundTicket);
//        } else {
//            System.out.println("Nessun ticket trovato per l'ID specificato.");
//        }
    }

//    private static Ticket getTicket(EntityManager em) {
//        TicketService ticketService = new TicketService(em);
//
//
//        Ticket ticket1 = new Ticket();
//        ticket1.setUserId(1);
//        ticket1.setValidita("2024-04-01");
//
//
//        Ticket ticket2 = new Ticket();
//        ticket2.setUserId(2);
//        ticket2.setValidita("2024-04-02");
//
//
//        // Salva i ticket nel database utilizzando il TicketService
//        ticketService.saveTicket(ticket1);
//        ticketService.saveTicket(ticket2);
//
//
//        // Trova un ticket per ID
//        Ticket foundTicket = ticketService.findTicketById(1);
//        return foundTicket;
//    }
}