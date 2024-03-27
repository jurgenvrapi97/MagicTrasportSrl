package Dao;
import entities.Abbonamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class AbbonamentoDao {
    private final EntityManager em;

    public AbbonamentoDao(EntityManager em) {
        this.em = em;
    }

    public void saveAbbonamento(Abbonamento abbonamento) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(abbonamento);
        transaction.commit();
        System.out.println("Abbonamento " + abbonamento.getId() + " salvato");
    }
    public Abbonamento findNotExpiredByCardN(long cardNumber){
        TypedQuery<Abbonamento>  query=em.createNamedQuery("findNotExpiredByCardN", Abbonamento.class);
        query.setParameter("cardNumber",cardNumber);
        query.setParameter("today", LocalDate.now());
        Abbonamento foundAbb= query.getSingleResult();
        System.out.println("trovato abbonamento non scaduto:"+foundAbb);
        return foundAbb;

    }
    public List<Abbonamento> findAbbonamentiEmessiByLocation(String location){
        TypedQuery<Abbonamento> query=em.createNamedQuery("findAbbonamentiEmessiByLocation", Abbonamento.class);
        query.setParameter("location",location);
        List <Abbonamento>foundAbbonamenti=query.getResultList();
        System.out.println("abbonamenti emessi da distributore in "+location+" :"+foundAbbonamenti);
        return foundAbbonamenti;
    }
}