package Dao;

import entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.Random;

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
    public long getTripCountForMezzoOnTratta(long tratta_id, long mezzo_id){
        TypedQuery<Long>query= em.createNamedQuery("getTripCountForMezzoOnTratta", Long.class);
        query.setParameter("tratta_id",tratta_id);
        query.setParameter("mezzo_id",mezzo_id);
        try{
            Long result=query.getSingleResult();
            Random rand = new Random();
            int randomNum = rand.nextInt((15 - 5) + 1) + 5;
            if (result!=0) {
                System.out.println("La tratta con id: " + tratta_id + " è percorsa dal mezzo n." + mezzo_id + " " + result + " volte. Tempo di percorrenza effetivo: " + (getAverageTimeOfRoute(tratta_id) + randomNum) + " minuti.");
                return result;
            }else {
                System.out.println("il mezzo non ha mai percorso questa tratta :)");
           return 0 ;}

        }catch(NoResultException ex){
            System.out.println("Dato non disponibile");
            return 0;
        }
    }
    public long getAverageTimeOfRoute(long tratta_id){
        TypedQuery<Number> query = em.createNamedQuery("getAverageTimeOfRoute", Number.class);
        query.setParameter("tratta_id",tratta_id);
        try{
            Number result = query.getSingleResult();
            System.out.println("tempo di percorrenza medio per la tratta con id: "+tratta_id+" è "+result.longValue()+" min");
            return result.longValue();
        }catch(NoResultException ex){
            System.out.println("Dato non disponibile");
            return 0;
        }
    }


}
