package Dao;



import entities.Operatività;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class OperativitaDAO {
    private final EntityManager entityManager;


    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("MagicTrasportSrl");

    public OperativitaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Operatività getOperativita(long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Operatività.class, id);
    }

    public void saveOperativita(Operatività operatività) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(operatività);
        em.getTransaction().commit();
        em.close();
    }

    public void updateOperativita(Operatività operatività) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(operatività);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteOperativita(long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Operatività operatività = em.find(Operatività.class, id);
        if (operatività != null) {
            em.remove(operatività);
        }
        em.getTransaction().commit();
        em.close();
    }

    public List<Operatività> getServiziMezzo(long idMezzo) {
        EntityManager em = emf.createEntityManager();
        List<Operatività> servizi = em.createQuery("SELECT o FROM Operatività o WHERE o.mezzo.id = :idMezzo AND o.dataInizioServizio IS NOT NULL AND o.dataFineServizio IS NOT NULL", Operatività.class)
                .setParameter("idMezzo", idMezzo)
                .getResultList();
        em.close();


        for (Operatività servizio : servizi) {
            System.out.println("ID: " + servizio.getId());
            System.out.println("Data Inizio Servizio: " + servizio.getDataInizioServizio());
            System.out.println("Data Fine Servizio: " + servizio.getDataFineServizio());
            System.out.println("-------------------------");
        }

        return servizi;
    }

    public List<Operatività> getManutenzioniMezzo(long idMezzo) {
        EntityManager em = emf.createEntityManager();
        List<Operatività> manutenzioni = em.createQuery("SELECT o FROM Operatività o WHERE o.mezzo.id = :idMezzo AND o.dataInizioManutenzione IS NOT NULL AND o.dataFineManutenzione IS NOT NULL", Operatività.class)
                .setParameter("idMezzo", idMezzo)
                .getResultList();
        em.close();

        for (Operatività manutenzione : manutenzioni) {
            System.out.println("ID: " + manutenzione.getId());
            System.out.println("Data Inizio Manutenzione: " + manutenzione.getDataInizioManutenzione());
            System.out.println("Data Fine Manutenzione: " + manutenzione.getDataFineManutenzione());
            System.out.println("-------------------------");
        }

        return manutenzioni;
    }



}
