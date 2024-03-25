package dao;

import entities.Card;
import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class CardsDAO {
    EntityManager em;
    public CardsDAO(EntityManager em) {
        this.em = em;
    }

    //methods
    public void save(Card card){
        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        em.persist(card);
        transaction.commit();
        System.out.println("Carta"+ card.toString()+" salvata");
    }
}
