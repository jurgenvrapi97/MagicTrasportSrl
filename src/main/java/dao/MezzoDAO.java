package dao;

import entities.Mezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class MezzoDAO {

    private final EntityManager em ;

    public MezzoDAO(EntityManager em) {this.em=em ;};

    public void save(Mezzo mezzo){
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(mezzo);
        transaction.commit();
        System.out.println("Mezzo salvato con successo!");
    }
}
