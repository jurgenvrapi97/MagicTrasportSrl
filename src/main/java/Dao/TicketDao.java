package Dao;


import entities.Mezzo;
import entities.Ticket;
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

    public  void ConvalidazioneBiglietto(Ticket ticket) {
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
    public List<Ticket>findBigliettiEmessiByTimeLapse(LocalDate startDate,LocalDate endDate){
        TypedQuery<Ticket>query=entityManager.createNamedQuery("findBigliettiEmessiByTimeLapse", Ticket.class);
        query.setParameter("start_date",startDate);
        query.setParameter("end_date",endDate);
        try{ List<Ticket>findings=query.getResultList();
            System.out.println("biglietti emessi nel periodo dal "+startDate+" al "+endDate+": "+findings.toString());
            return findings;
            }
        catch (NoResultException ex){
            System.out.println("Nessun ticket trovato per questo lasso di tempo");
            return null;
        }

    }
}
