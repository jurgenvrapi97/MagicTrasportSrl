package Dao;

import entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class TrattaDao {
    private final EntityManager em;

    public TrattaDao(EntityManager em) {this.em=em;}
    public void save(Tratta tratta) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(tratta);
        tx.commit();
        System.out.println("Tratta salvata con successo");
    }
    public Tratta getById(long tratta_id){
        Tratta tratta=em.find(Tratta.class,tratta_id);
      return tratta;
    }
    public void delete(long tratta_id){
        Tratta trattaToDelete=this.getById(tratta_id);
        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        em.remove(trattaToDelete);
        transaction.commit();
        System.out.println("tratta"+ trattaToDelete.toString()+" eliminata");
    }
}