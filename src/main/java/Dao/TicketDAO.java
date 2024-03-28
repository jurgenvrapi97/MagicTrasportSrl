package Dao;


import entities.Mezzo;
import entities.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TicketDAO {

    private final EntityManager entityManager;

    public TicketDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void saveTicket(Ticket ticket) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(ticket);
            transaction.commit();
            System.out.println("Ticket salvato correttamente!");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Ticket findTicketById(int ticketId) {
        return entityManager.find(Ticket.class, ticketId);
    }

    public List<Ticket> findAllTickets() {
        TypedQuery<Ticket> query = entityManager.createQuery("SELECT t FROM Ticket t", Ticket.class);
        return query.getResultList();
    }



    public void deleteTicket(Ticket ticket) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(ticket);
            transaction.commit();
            System.out.println("Ticket eliminato correttamente!");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    public void vidimareTicket(int ticketId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Cerca il biglietto nel database utilizzando l'ID
            Ticket ticket = entityManager.find(Ticket.class, ticketId);
            if (ticket == null) {
                throw new IllegalArgumentException("Nessun biglietto trovato con l'ID: " + ticketId);
            }

            // Vidima il biglietto
            ticket.vidimare();

            // Incrementa il contatore del mezzo
            Mezzo mezzo = ticket.getMezzo();
            mezzo.incrementaBigliettiVidimati();

            // Salva le modifiche
            entityManager.merge(ticket);
            entityManager.merge(mezzo);

            transaction.commit();
            System.out.println("Biglietto vidimato correttamente!");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }



}