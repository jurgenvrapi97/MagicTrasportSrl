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
        System.out.println(mezzo.getTipo() + " salvato con successo!");

    }

    public Mezzo findMezzoById(long id){
        Mezzo mezzo = em.find(Mezzo.class, id);
        return mezzo;
    }
}
