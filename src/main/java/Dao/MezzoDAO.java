package Dao;

import entities.Mezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

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

    public double calcolatoreTempoPercorrenzaMedia(long mezzo_Id) {
        TypedQuery<Double> q = em.createNamedQuery("Mezzo.calcolatoreTempoPercorrenzaMedia", Double.class)
                .setParameter("mezzoId", mezzo_Id);

        Double tempoPercorrenzaMedia = q.getSingleResult();

        if (tempoPercorrenzaMedia == null) {
            System.out.println("Non è stata trovata alcuna tratta per il mezzo con ID: " + mezzo_Id);
            return 0.0; // Valore di dafault
        } else {
            System.out.println("Il tempo di percorrenza media del mezzo con id " + mezzo_Id + " è di " + tempoPercorrenzaMedia + " minuti.");
            return tempoPercorrenzaMedia;
        }
    }
}
