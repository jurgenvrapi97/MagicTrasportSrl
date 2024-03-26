package org.example;

import Dao.CardsDAO;
import Dao.UsersDAO;
import com.github.javafaker.Faker;
import entities.*;
import enums.Stato;
import enums.TipoAbbonamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import service.TicketService;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Application {
private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MagicTrasportSrl");
    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();


        // generateUsersCard(50);
        //generateAbbonamenti(23);
        //generateDistributori(20);

        em.close();
        emf.close();


    }

    public static void generateUsersCard(int numUsers){
        Faker faker = new Faker(new Locale("it"));
        EntityManager em = emf.createEntityManager();

        for (int i = 0; i < numUsers; i++) {
            em.getTransaction().begin();
            User user = new User();
            user.setName(faker.name().firstName());
            user.setSurname(faker.name().lastName());
            user.setAge(faker.number().numberBetween(18,100));


            if (faker.random().nextBoolean()){
                Card card = new Card(convertToLocalDate( faker.date().past(365, TimeUnit.DAYS)));
                card.setUser(user);
                user.setCard(card);
                em.persist(card);
            }

            em.persist(user);
            em.getTransaction().commit();
        }
        em.close();
    }

    public static void generateAbbonamenti(int numAbbonamenti){
        Faker faker = new Faker(new Locale("it"));
        EntityManager em = emf.createEntityManager();

        List<User> users = em.createQuery("SELECT u FROM User u WHERE u.card IS NOT NULL", User.class).getResultList();
        for (int i = 0; i < numAbbonamenti && i < users.size(); i++) {
            em.getTransaction().begin();
            Abbonamento abbonamento = new Abbonamento();
            abbonamento.setDataInizio(convertToLocalDate(faker.date().past(30, TimeUnit.DAYS)));
            abbonamento.setDataScadenza(convertToLocalDate(faker.date().future(365, TimeUnit.DAYS)));
            abbonamento.setTipoAbbonamento(TipoAbbonamento.values()[faker.random().nextInt(TipoAbbonamento.values().length)]);
            abbonamento.setCard(users.get(i).getCard());
            em.persist(abbonamento);

            Card card = users.get(i).getCard();
            card.setAbbonamento(abbonamento);
            em.merge(card);

            em.getTransaction().commit();
        }

        em.close();
    }

    public static void generateDistributori(int numDistributori) {
        Faker faker = new Faker(new Locale("it"));
        EntityManager em = emf.createEntityManager();
        List<Abbonamento> abbonamenti = em.createQuery("SELECT a FROM Abbonamento a WHERE a.distributore IS NULL", Abbonamento.class).getResultList();
        List<Distributore> distributori = em.createQuery("SELECT d FROM Distributore d", Distributore.class).getResultList();
        Random random = new Random();
        for (int i = 0; i < numDistributori && i < abbonamenti.size(); i++) {
            em.getTransaction().begin();

            Distributore distributore = new Distributore();
            distributore.setStato(Stato.values()[faker.random().nextInt(Stato.values().length)]);
            distributore.setLocation(faker.address().fullAddress());
            distributore.setnBigliettiEmessi(faker.number().numberBetween(0, 1000));

            Abbonamento abbonamento = abbonamenti.get(i);
            distributore.getAbbonamenti().add(abbonamento);
            abbonamento.setDistributore(distributore);

            em.persist(distributore);
            em.merge(abbonamento);

            em.getTransaction().commit();
        }
    }


    private static LocalDate convertToLocalDate(java.util.Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}