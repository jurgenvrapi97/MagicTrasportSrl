package Dao;

import entities.Card;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;

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
    public static Card findIsExpired(long card_id){
        TypedQuery<Card>query=em.createNamedQuery("findIsExpired", Card.class);
        query.setParameter("id",card_id);
        query.setParameter("today", LocalDate.now());
        try{ Card found=query.getSingleResult();
            System.out.println("trovata card non scaduta: "+found);
            return found;}
        catch (NoResultException ex){
           System.out.println("Carta inesistente o scaduta");
            return null;
        }

    }
}
