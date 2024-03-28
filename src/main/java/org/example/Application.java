package org.example;

import Dao.*;
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
//       generateDistributori(20);


        AbbonamentoDao abbonamentoDAO = new AbbonamentoDao(em);
        abbonamentoDAO.findNotExpiredByCardN(1);
        DistributoreDao distributoreDao = new DistributoreDao(em);
        distributoreDao.findBigliettiEmessiByLocation("Strada Teseo 84, Cattaneo umbro, SR 63302");
        distributoreDao.findDistributoreAttivo();
        abbonamentoDAO.findAbbonamentiEmessiByLocation("Strada Teseo 84, Cattaneo umbro, SR 63302");
        abbonamentoDAO.findAbbonamentiEmessiByTimeLapse(LocalDate.of(2024,02,28),LocalDate.now());
        TicketDao ticketDao=new TicketDao(em);
        ticketDao.findBigliettiEmessiByTimeLapse(LocalDate.of(2024,03,11),LocalDate.now());
        TrattaDao trattaDao=new TrattaDao(em);
        trattaDao.getAverageTimeOfRoute(23);
        trattaDao.getTripCountForMezzoOnTratta(152,1);


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

    public static void generateAbbonamenti(int numAbbonamenti) {
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
}


    //  Imposto la struttura dello scanner dalla parte del gestore, poi bisognerà cambiare alcuni valori e nomi

    /* 
    int scelta = 0;
        long mezzo_id, tratta_id, ticket_id;
    Scanner scanner = new Scanner(System.in);
    do {

        System.out.println("----------- BENVENUTO NELLA SEZIONE GESTIONE DELLA MAGICTRANSPORTsrl, SCEGLI UN'OPERAZIONE:-----------");

        System.out.println("1) Cerca i biglietti vidimati su un mezzo specifico; ");
        System.out.println("2) Cerca i biglietti emessi in base alla data; ");
        System.out.println("3) Cerca i biglietti venduti da uno specifico distributore/rivenditore; ");
        System.out.println("4) Cerca i periodi di servizio e manutenzione di un mezzo in base alla data;");
        System.out.println("5) Verifica il tempo di percorrenza medio in base alla tratta");
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
              System.out.println("Inserisci l'id di un mezzo per vedere i biglietti vitimati in totale su di esso:");
//            long Mezzo_id = Long.parseLong(scanner.nextLine());
//            md.biglietti_vidimati(md.findMezzoById(mezzo_id));
//            break;
//
//            case 2:
//            System.out.println("Inserisci una data per visualizzare i biglietti emessi: ");
//            LocalDate data = LocalDate.parse(scanner.nextLine());
//            List<Ticket> ticket = rd.ticketsForDate(data);
//
//            if (ticket.isEmpty()) {
//                System.out.println("Non ci sono biglietti emessi in questa data.");
//            } else {
//                System.out.println("Numero biglietti emessi in data " + data + " :");
//                for (Ticket tickets1 : ticket) {
//                    System.out.println(tickets1);
//                }
//            }

            case 3:
             System.out.println("Immetti l'id del distributore:");
             long distributore_Id = Long.parseLong(scanner.nextLine());
             System.out.println("Questo distributore ha venduto " + rd.nBigliettiEmessi(distributore_id).size() + " ticket");
             break;


            case 4:
             System.out.println("Immetti una data per vedere lo stato del mezzo:");
             String input = scanner.nextLine();
             LocalDate StatoMezzo;
             try {
             StatoMezzo = LocalDate.parse(input);
             } catch (DateTimeParseException e) {
             System.out.println("Data non valida. Accertati di inserire la data nel formato YYYY-MM-DD.");
            break;
            }

            System.out.println("Immetti l'id del mezzo di cui vuoi vedere lo stato nella data precedentemente selezionata:");
            mezzo_Id = Long.parseLong(scanner.nextLine());
            Mezzo mezzoX = em.find(Mezzo.class, mezzo_Id);

            if (mezzoX != null | dataStatoVeicolo != null) {
            System.out.println("Stato veicolo in data " + dataStatoVeicolo + " :");
            md.ricercaPeriodiDiStato(dataStatoVeicolo, mezzoX);
            } else {
             System.out.println("Non è stato trovato alcun mezzo con id: " + mezzoId + " in data " + dataStatoVeicolo);
            }
             break;

<<<<<<< HEAD
        } */




//SCANNER: LATO UTENTE (da completare)

   /* Scanner scanner = new Scanner(System.in);
    int scelta;
    do  {

        EntityManager em = emf.createEntityManager();

        System.out.println("----------- BENVENUTO NELLA SEZIONE UTENTE DELLA MAGICTRANSPORTsrl, SCEGLI UN'OPERAZIONE:-----------");
        System.out.println("1. Biglietteria");
        System.out.println("2. Abbonamento");
        System.out.println("3. Card per abbonamento");
        System.out.println("0. Esci");


        scelta =scanner.nextInt();
        scanner.nextLine();
        switch (scelta) {


            case 1:
                System.out.println("1.Acquista biglietto");
                System.out.println("2.Convalida biglietto");

                int scelta1 = scanner.nextInt();
                switch (scelta1) {
                    case 1:
                        TicketDao ticketDao = new TicketDao(em);
                        Ticket ticket = new Ticket();
                        ticketDao.saveTicket(ticket);
                        System.out.println("Biglietto acquistato con successo!");
                        break;

                    case 2:
                        System.out.println("Inserisci l'ID del biglietto per convalidarlo");
                        int ticketId = scanner.nextInt();
                        Ticket ticketToValidate = TicketDao.findTicketById(ticketId);
                        if (ticketToValidate != null) {

                            TicketDao.ConvalidazioneBiglietto(ticketToValidate);
                        } else {
                            System.out.println("Nessun biglietto con l'ID specificato.");
                        }
                        break;
                }
                break;

            case 2:
                System.out.println("Acquista un abbonamento");
                System.out.println("Verifica data di scadenza abbonamento");

                int scelta2 = scanner.nextInt();
                switch (scelta2) {
                    case 1:
                        System.out.println("Sei provvisto di una card? (S/N)");
                        String risposta = scanner.next();
                        if (risposta.equalsIgnoreCase("S")) {
                            System.out.println("Inserisci il numero della tua card");
                            long cardId = scanner.nextLong();


                            Card card = CardsDao.findIsExpired(cardId);
                            if (card != null) {

                                System.out.println("Seleziona il tipo di abbonamento:");
                                System.out.println("1. Mensile");
                                System.out.println("2. Settimanale");
                                int tipoScelto = scanner.nextInt();


                                LocalDate dataInizio = LocalDate.now();
                                LocalDate dataScadenza;
                                TipoAbbonamento tipoAbbonamento;
                                switch (tipoScelto) {
                                    case 1:
                                        dataScadenza = dataInizio.plusWeeks(1);
                                        tipoAbbonamento = TipoAbbonamento.SETTIMANALE;
                                        break;
                                    case 2:
                                        dataScadenza = dataInizio.plusMonths(1);
                                        tipoAbbonamento = TipoAbbonamento.MENSILE;
                                        break;

                                    default:
                                        System.out.println("Scelta non valida.");
                                        break;
                                }


                                Abbonamento abbonamento = new Abbonamento(dataInizio, dataScadenza, tipoAbbonamento, null);
                                abbonamento.setCard(card);
                                AbbonamentoDao.saveAbbonamento(abbonamento);
                                System.out.println("Abbonamento acquistato con successo!");
                            } else {
                                System.out.println("La card non esiste o è scaduta. Impossibile procedere all'acquisto dell'abbonamento.");
                            }
                        } else if (risposta.equalsIgnoreCase("N")) {
                            System.out.println("Spiacenti, per acquistare un abbonamento è necessario essere provvisti di una card.");
                        } else {
                            System.out.println("Scelta non valida. Riprova.");
                        }
                        break;

                    case 2:
                        System.out.println("Inserisci il numero della tua card");
                        int cardId = scanner.nextInt();


                        if (isCardValid(cardId)) {

                            System.out.println("La card esiste. Puoi procedere all'acquisto dell'abbonamento.");
                        } else {

                            System.out.println("La card non esiste. Impossibile procedere all'acquisto dell'abbonamento.");
                        }
                        break;
                }
                break;


            case 0:
                System.out.println("Uscita dal programma in corso...");
                System.out.println("Grazie per aver utilizzato i nostri servizi");
                scanner.close();
                break;
            default:
                System.out.println("Scelta non valida. Riprova.");
                break;
        }
    */

//>>>>>>> master
//}