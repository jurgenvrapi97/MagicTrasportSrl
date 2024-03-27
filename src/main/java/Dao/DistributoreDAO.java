package Dao;

import Entities.Distributore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DistributoreDAO {
    private final EntityManager em;

    public DistributoreDAO(EntityManager em) {
        this.em = em;
    }

    public void saveDistributore(Distributore distributore) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(distributore);
        transaction.commit();
        System.out.println("Distributore " + distributore.getId() + " salvato");
    }
}
