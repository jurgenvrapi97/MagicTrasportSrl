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

//    public int getBigliettiConvalidatiPerMezzo(long mezzo_id, LocalDate data) {
//        try {
//            return int result=entityManager.createQuery("SELECT COUNT(t) FROM Ticket t WHERE t.mezzo.id = :mezzo_id AND t.dataEmisione <= :data AND t.validita = 'Vidimato'", Long.class)
//                    .setParameter("mezzo_id", mezzo_id)
//                    .setParameter("data", data)
//                    .getSingleResult()
//                    .intValue();
//            System.out.println("ok");
//        } catch (Exception e) {
//            System.err.println("Si è verificato un errore durante il recupero del numero di biglietti vidimati per il mezzo: " + e.getMessage());
//            return 0;
//        }
//    }
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
            return findings;
            }
        catch (NoResultException ex){
            System.out.println("Nessun ticket trovato per questo lasso di tempo");
            return null;
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
