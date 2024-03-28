package org.example;

import Dao.*;
import com.github.javafaker.Faker;
import entities.*;
import enums.Stato;
import enums.TipoAbbonamento;
import enums.TipoMezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ApplicationDB {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MagicTrasportSrl");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();


        // ho aggiunto i metodi e il dao per timbrare uno specifico biglietto con il suo id per far si che lo vidimi
//        TicketDAO ticketDAO = new TicketDAO(em);
//        ticketDAO.vidimareTicket(1);
        OperativitaDAO operativitaDAO = new OperativitaDAO(em);
        System.out.println("giorni di manutenzione: "+operativitaDAO.getGiorniDiManutenzione(8));
        System.out.println("giorni di servizio: " + operativitaDAO.getGiorniDiServizio(8));

        // per riempire il database non si possono usare tutti i metoti contemporaneamente c'è un ordine




        // 1 generateUsersCard(50);
        // 2 generateDistributori(20);
      // 3 ( abbonamenti ha un po di problemi con gli id univoci quindi conviene eseguirlo 2 volte per avere abbastanza abbonamenti)generateAbbonamenti(20);

        // 4 generateTickets(100);
        //  5  generateMezzi(30);
//       AbbonamentoDAO abbonamentoDAO=new AbbonamentoDAO(em);
//       abbonamentoDAO.findNotExpiredByCardN(1);
        // 6 aggiornaTratteMezzi();



        // per adesso operatività non lo eseguite per pensare meglio a come farlo funzionare per ottenere cosa ci serve
//       generateOperativita(30);

       // generateOperativita();




        em.close();
        emf.close();


    }

    public static void generateUsersCard(int numUsers) {
        Faker faker = new Faker(new Locale("it"));
        EntityManager em = emf.createEntityManager();

        for (int i = 0; i < numUsers; i++) {
            em.getTransaction().begin();
            User user = new User();
            user.setName(faker.name().firstName());
            user.setSurname(faker.name().lastName());
            user.setAge(faker.number().numberBetween(18, 100));


            if (faker.random().nextBoolean()) {
                Card card = new Card(convertToLocalDate(faker.date().past(365, TimeUnit.DAYS)));
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

        List<User> users = em.createQuery("SELECT u FROM User u WHERE u.card IS NOT NULL AND u.card.abbonamento IS NULL", User.class).getResultList();
        if (!users.isEmpty()) {
            for (int i = 0; i < numAbbonamenti; i++) {
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
                User user = users.get(faker.random().nextInt(users.size()));
                abbonamento.setCard(user.getCard());
                em.persist(abbonamento);

                Card card = user.getCard();
                card.setAbbonamento(abbonamento);
                em.merge(card);

                em.getTransaction().commit();
            }
        } else {
            System.out.println("La lista users è vuota o tutte le Card sono già associate a un Abbonamento");
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

    public static void generateTickets(int numTickets){
        Faker faker = new Faker(new Locale("it"));
        EntityManager em = emf.createEntityManager();

        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        List<Distributore> distributori = em.createQuery("SELECT d FROM Distributore d", Distributore.class).getResultList();
        List<Mezzo> mezzi = em.createQuery("SELECT m FROM Mezzo m", Mezzo.class).getResultList();
        if (!users.isEmpty() && !distributori.isEmpty() && !mezzi.isEmpty()) {
            for (int i = 0; i < numTickets; i++) {
                em.getTransaction().begin();
                Ticket ticket = new Ticket();
                User user = users.get(faker.random().nextInt(users.size()));
                ticket.setUser(user);
                ticket.setDataEmisione(LocalDate.now());
                ticket.setValidita("valido");

                ticket.setDistributore(distributori.get(faker.random().nextInt(distributori.size())));
                ticket.setMezzo(mezzi.get(faker.random().nextInt(mezzi.size())));
                em.persist(ticket);


                user.getTickets().add(ticket);
                em.merge(user);

                em.getTransaction().commit();
            }
        } else {
            System.out.println("Le liste users, distributori o mezzi sono vuote");
        }

        em.close();
    }




    public static void generateMezzi(int numMezzi) {
        Faker faker = new Faker(new Locale("it"));
        EntityManager em = emf.createEntityManager();

        List<Tratta> tratte = em.createQuery("SELECT t FROM Tratta t", Tratta.class).getResultList();
        for (int i = 0; i < numMezzi; i++) {
            em.getTransaction().begin();
            Mezzo mezzo = new Mezzo();

            mezzo.setTipo(TipoMezzo.values()[faker.random().nextInt(TipoMezzo.values().length)]);
            mezzo.setBiglietti_vidimati(faker.number().numberBetween(0, 100));

            if (!tratte.isEmpty()) {
                mezzo.setTratta(tratte.get(faker.random().nextInt(tratte.size())));
            }
            em.persist(mezzo);
            em.getTransaction().commit();
        }

        em.close();
    }


    public static void aggiornaTratteMezzi() {
        Faker faker = new Faker(new Locale("it"));
        EntityManager em = emf.createEntityManager();


        List<Mezzo> mezzi = em.createQuery("SELECT m FROM Mezzo m", Mezzo.class).getResultList();

        for (Mezzo mezzo : mezzi) {
            em.getTransaction().begin();


            Tratta tratta = new Tratta();
            tratta.setPartenza(faker.address().cityName());
            tratta.setDestinazione(faker.address().cityName());
            tratta.setPercorrenza_media(faker.number().numberBetween(30, 120));


            em.persist(tratta);
            em.flush();
            em.refresh(tratta);


            mezzo.setTratta(tratta);


            em.merge(mezzo);

            em.getTransaction().commit();
        }

        em.close();
    }




    public static void generateOperativita() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        List<Mezzo> mezzi = em.createQuery("SELECT m FROM Mezzo m", Mezzo.class).getResultList();

        Faker faker = new Faker(new Locale("it"));

        for (Mezzo mezzo : mezzi) {
            Operatività manutenzione = new Operatività();
            manutenzione.setDataInizioManutenzione(faker.date().past(30, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            manutenzione.setDataFineManutenzione(faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            manutenzione.setMezzo(mezzo);

            Operatività servizio = new Operatività();
            servizio.setDataInizioServizio(manutenzione.getDataFineManutenzione().plusDays(1));

            int durataServizio = faker.number().numberBetween(200, 300);
            servizio.setDataFineServizio(servizio.getDataInizioServizio().plusDays(durataServizio));
            servizio.setMezzo(mezzo);

            em.persist(manutenzione);
            em.persist(servizio);
        }

        em.getTransaction().commit();
        em.close();
    }




    private static LocalDate convertToLocalDate(java.util.Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}