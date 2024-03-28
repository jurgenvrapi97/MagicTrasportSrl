package Dao;

import entities.Abbonamento;
import entities.Card;
import entities.User;
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
    public void updateUser(User user,long card_id){
        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        Card card=em.find(Card.class,card_id);
        if(card!=null){
            card.setUser(user);
            em.merge(card);
            transaction.commit();
            System.out.println("Carta"+ card.toString()+" salvata");
        }
       else{
            System.out.println("la card con id "+card_id+" non esiste");
        }
    }
    public void updateAbbonamento(Abbonamento abbonamento, long card_id){
        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        Card card=em.find(Card.class,card_id);
        System.out.println("trovato");
        if(card!=null){
            System.out.println("siamo dentro");
            card.setAbbonamento(abbonamento);
            em.merge(card);
            transaction.commit();
            System.out.println("Carta"+ card.toString()+" salvata");
        }
        else{
            System.out.println("la card con id "+card_id+" non esiste");
        }
    }

    //methods
    public void save(Card card){
        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        em.persist(card);
        transaction.commit();
        System.out.println("Carta"+ card.toString()+" salvata");
    }
    public  Card findIsExpired(long card_id){
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
