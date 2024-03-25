package Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import Entities.Tratta;
public class TrattaDAO {
    private final EntityManager em;

    public TrattaDAO(EntityManager em) {this.em=em;}
    public void save(Tratta tratta) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(tratta);
        tx.commit();
        System.out.println("Tratta salvata con successo");
    }
    public Tratta findById(int id) {
        Tratta tratta = em.find(Tratta.class, id);
        return tratta;
    }
}
