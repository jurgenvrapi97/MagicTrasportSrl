package org.example;
import dao.DistributoreDAO;
import entities.Distributore;
import enums.Stato;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MagicTrasportSrl");
    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        DistributoreDAO distributoreDAO = new DistributoreDAO(em);

        Distributore distributore = new Distributore(Stato.ATTIVO, "marche", 39, null,null);
        distributoreDAO.saveDistributore(distributore);
    }
}
