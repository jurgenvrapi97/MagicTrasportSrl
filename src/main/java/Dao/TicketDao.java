package Dao;


import entities.Mezzo;
import entities.Ticket;
import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class TicketDao {

    private final EntityManager entityManager;

    public TicketDao(EntityManager entityManager) {
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

    public void ConvalidazioneBiglietto(Ticket ticket) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            ticket.setValidita(null);
            entityManager.merge(ticket);
            transaction.commit();
            System.out.println("Il biglietto è stato convalidato con successo!");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Si è verificato un errore durante la convalida del biglietto: " + e.getMessage());
        }
    }
    public int getBigliettiConvalidatiPerMezzo(Mezzo mezzo, LocalDate data) {
        try {
            return entityManager.createQuery("SELECT COUNT(t) FROM Ticket t WHERE t.mezzo = :mezzo AND t.dataEmisione <= :data AND t.validita = 'Vidimato'", Long.class)
                    .setParameter("mezzo", mezzo)
                    .setParameter("data", data)
                    .getSingleResult()
                    .intValue();
        } catch (Exception e) {
            System.err.println("Si è verificato un errore durante il recupero del numero di biglietti vidimati per il mezzo: " + e.getMessage());
            return 0;
        }
    }
    public Ticket findTicketById(int ticketId) {
        return entityManager.find(Ticket.class, ticketId);
    }

    public List<Ticket> findAllTickets() {
        TypedQuery<Ticket> query = entityManager.createQuery("SELECT t FROM Ticket t", Ticket.class);
        return query.getResultList();
    }


    public List<Ticket> findVidimatiTicketsByMezzoId(int mezzoId) {
        Mezzo mezzo = entityManager.find(Mezzo.class, mezzoId);
        if (mezzo == null) {
            System.out.println("Nessun mezzo trovato con l'ID fornito");
            return null;
        }
        TypedQuery<Ticket> query = entityManager.createQuery(
                "SELECT t FROM Ticket t WHERE t.mezzo = :mezzo AND t.validita = 'non valido'",
                Ticket.class);
        query.setParameter("mezzo", mezzo);
        try {
            List<Ticket> tickets = query.getResultList();
            System.out.println("Biglietti vidimati per il mezzo con ID " + mezzoId + ": " + tickets.toString());
            return tickets;
        } catch (NoResultException ex) {
            System.out.println("Nessun ticket vidimato trovato per questo mezzo");
            return null;
        }
    }

    public List<Ticket> findTicketsByUserId(int userId) {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            System.out.println("Nessun utente trovato con l'ID fornito");
            return null;
        }
        TypedQuery<Ticket> query = entityManager.createQuery(
                "SELECT t FROM Ticket t WHERE t.user = :user",
                Ticket.class);
        query.setParameter("user", user);
        try {
            List<Ticket> tickets = query.getResultList();
            System.out.println("Biglietti per l'utente con ID " + userId + ": " + tickets.toString());
            return tickets;
        } catch (NoResultException ex) {
            System.out.println("Nessun ticket trovato per questo utente");
            return null;
        }
    }



}
