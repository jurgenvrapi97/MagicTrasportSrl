package org.example;

import Dao.AbbonamentoDao;
import com.github.javafaker.Faker;
import entities.*;
import enums.Stato;
import enums.TipoAbbonamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class Application {
private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MagicTrasportSrl");
    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();



//       generateUsersCard(50);
//       generateAbbonamenti(23);
       generateDistributori(20);


        AbbonamentoDao abbonamentoDAO=new AbbonamentoDao(em);
        abbonamentoDAO.findNotExpiredByCardN(1);



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
        List<Distributore> distributori = em.createQuery("SELECT d FROM Distributore d", Distributore.class).getResultList();
        for (int i = 0; i < numAbbonamenti && i < users.size(); i++) {
            em.getTransaction().begin();
            Abbonamento abbonamento = new Abbonamento();
            abbonamento.setDataInizio(convertToLocalDate(faker.date().past(30, TimeUnit.DAYS)));
            TipoAbbonamento tipo = TipoAbbonamento.values()[faker.random().nextInt(TipoAbbonamento.values().length)];
            abbonamento.setTipoAbbonamento(tipo);
            switch (tipo) {
                case MENSILE:
                    abbonamento.setDataScadenza(abbonamento.getDataInizio().plusMonths(1));
                    break;
                case SETTIMANALE:
                    abbonamento.setDataScadenza(abbonamento.getDataInizio().plusWeeks(1));
                    break;
                default:
                    abbonamento.setDataScadenza(abbonamento.getDataInizio().plusYears(1));
                    break;
            }
            abbonamento.setCard(users.get(i).getCard());
            abbonamento.setDistributore(distributori.get(faker.random().nextInt(distributori.size())));
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


    /*  Inizio ad impostare la struttura dello scanner dalla parte del gestore

    int scelta = 0;
        long mezzo_id, tratta_id, ticket_id;
    Scanner scanner = new Scanner(System.in);
    do {

        System.out.println("----------- BENVENUTO NELLA SEZIONE GESTIONE DELLA MAGICTRANSPORTsrl, SCEGLI UN'OPERAZIONE:-----------");

        System.out.println("1) Cerca i biglietti vidimati su un mezzo specifico; ");
        System.out.println("2) Cerca i biglietti emessi in base alla data; ");
        System.out.println("3) Cerca i biglietti venduti da uno specifico distributore/rivenditore; ");
        System.out.println("4) Cerca i peridodi di servizio e manutenzione di un mezzo in base alla data;");
        System.out.println("5) Verifica il tempo di percorrenza medio in base alla tratta");
        System.out.println("6) Verifica la validità dell'abbonamento di un'utente in base al suo numero di tessera");
        System.out.println("0) Esci dal programma; ");


        scelta = scanner.nextInt();
        scanner.nextLine();

        switch (scelta) {

            case 0:
            System.out.println("Uscita dal programma in corso...");
            System.out.println("Grazie per aver utilizzato i nostri servizi");
            scanner.close();
            return;

            case 1:
        } */
}