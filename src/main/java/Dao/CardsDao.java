package Dao;

import entities.Card;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class CardsDao {
    EntityManager em;
    public CardsDao(EntityManager em) {
        this.em = em;
    }

    public Card getById(long card_id){
        Card card=em.find(Card.class,card_id);
        return card;
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
