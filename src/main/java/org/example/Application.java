package org.example;

import Dao.*;

import entities.*;

import enums.TipoAbbonamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MagicTrasportSrl");


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exitProgram = false;
        do {
            System.out.println("Seleziona il lato dell'applicazione:");
            System.out.println("1. Utente");
            System.out.println("2. Admin");
            System.out.println("0. Esci");

            int lato = Integer.parseInt(scanner.nextLine());


            switch (lato) {
                case 1:
                    runUserSide(scanner);
                    break;
                case 2:
                    runAdminSide(scanner);
                    break;
                case 0:
                    System.out.println("Uscita dal programma in corso...");
                    System.out.println("Grazie per aver utilizzato i nostri servizi");

                    exitProgram = true;
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.");
                    break;
            }
        } while (!exitProgram);

        scanner.close();
    }

    private static void runUserSide(Scanner scanner) {
        EntityManager em = emf.createEntityManager();
        int scelta;
        do {

            System.out.println("----------- BENVENUTO NELLA SEZIONE UTENTE DELLA MAGICTRANSPORTsrl, SCEGLI UN'OPERAZIONE:-----------");
            System.out.println("1. Biglietteria");
            System.out.println("2. Abbonamento");
            System.out.println("3. Card per abbonamento");
            System.out.println("4. Torna al menu principale");


            scelta = scanner.nextInt();
            scanner.nextLine();
            switch (scelta) {


                case 1:
                    System.out.println("1.Acquista biglietto");
                    System.out.println("2.Convalida biglietto");

                    int scelta1 = scanner.nextInt();
                    switch (scelta1) {
                        case 1:
                            TicketDao ticketDao = new TicketDao(em);
                            DistributoreDao distributoreDao = new DistributoreDao(em);
                            MezzoDao mezzoDao = new MezzoDao(em);
                            Ticket ticket = new Ticket(LocalDate.now(), "valido", null, distributoreDao.getById(104), mezzoDao.findMezzoById(25), null);
                            ticketDao.saveTicket(ticket);
                            System.out.println("Biglietto acquistato con successo!");
                            break;

                        case 2:
                            System.out.println("Inserisci l'ID del biglietto per convalidarlo");
                            int ticketId = scanner.nextInt();
                            TicketDao ticketDao1 = new TicketDao(em);
                            ticketDao1.vidimareTicket(ticketId);

                            break;
                    }
                    break;

                case 2:
                    System.out.println("1 Acquista un abbonamento");
                    System.out.println("2 Verifica data di scadenza card");

                    int scelta2 = scanner.nextInt();
                    switch (scelta2) {
                        case 1:
                            System.out.println("Sei provvisto di una card? (S/N)");
                            String risposta = scanner.next();
                            if (risposta.equalsIgnoreCase("S")) {
                                System.out.println("Inserisci il numero della tua card");
                                long cardId = scanner.nextLong();


                                CardsDao cardsDao = new CardsDao(em);
                                Card card = cardsDao.findIsExpired(cardId);
                                if (cardsDao != null) {

                                    System.out.println("Seleziona il tipo di abbonamento:");
                                    System.out.println("1. Settimanale");
                                    System.out.println("2. Mensile");
                                    int tipoScelto = scanner.nextInt();


                                    LocalDate dataInizio = LocalDate.now();
                                    LocalDate dataScadenza;
                                    TipoAbbonamento tipoAbbonamento;
                                    switch (tipoScelto) {

                                        case 1:
                                            DistributoreDao distributoreDao = new DistributoreDao(em);
                                            Random rdm = new Random();
                                            Distributore nDistributore = distributoreDao.getById(rdm.nextInt(102, 112));
                                            dataScadenza = dataInizio.plusWeeks(1);
                                            tipoAbbonamento = TipoAbbonamento.SETTIMANALE;
                                            Abbonamento abbonamento = new Abbonamento(dataInizio, dataScadenza, tipoAbbonamento, nDistributore);
                                            AbbonamentoDao abbonamentoDao = new AbbonamentoDao(em);
                                            abbonamento.setCard(card);
                                            abbonamentoDao.saveAbbonamento(abbonamento);
                                            cardsDao.updateAbbonamento(abbonamento, card.getId());
                                            System.out.println("Abbonamento acquistato con successo!");
                                            break;
                                        case 2:
                                            DistributoreDao distributoreDao1 = new DistributoreDao(em);
                                            Random rdm1 = new Random();
                                            Distributore nDistributore1 = distributoreDao1.getById(rdm1.nextInt(102, 112));
                                            dataScadenza = dataInizio.plusWeeks(1);
                                            tipoAbbonamento = TipoAbbonamento.MENSILE;
                                            Abbonamento abbonamento1 = new Abbonamento(dataInizio, dataScadenza, tipoAbbonamento, nDistributore1);
                                            AbbonamentoDao abbonamentoDao1 = new AbbonamentoDao(em);
                                            abbonamento1.setCard(card);
                                            abbonamentoDao1.saveAbbonamento(abbonamento1);
                                            cardsDao.updateAbbonamento(abbonamento1, card.getId());
                                            System.out.println("Abbonamento acquistato con successo!");
                                            break;

                                        default:
                                            System.out.println("Scelta non valida.");
                                            break;
                                    }


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
                            CardsDao cardsDao = new CardsDao(em);
                            Card card = cardsDao.findIsExpired(cardId);


                            if (card != null) {

                                System.out.println("La card esiste. Puoi procedere all'acquisto dell'abbonamento.");
                            } else {

                                System.out.println("La card non esiste. Impossibile procedere all'acquisto dell'abbonamento.");
                            }
                            break;

                    }
                    break;
//                ---------Aggiunta logica creazione e salvataggio card (da testare)-------

                case 3:
                    System.out.println("Creazione di una nuova carta:");


                    Card nuovaCarta = new Card(LocalDate.now());
                    CardsDao cardsDao = new CardsDao(em);
                    cardsDao.save(nuovaCarta);
                    System.out.println("se sei già un utente inserisci il tuo codice utente");
                    UsersDao usersDao = new UsersDao(em);
                    long user_id = Long.parseLong(scanner.nextLine());
                    User user = usersDao.getById(user_id);
                    cardsDao.updateUser(user, nuovaCarta.getId());
                    System.out.println("Nuova carta creata con successo!");
                    break;
                case 4:
                    System.out.println("Tornando al menu principale");
                    return;
                default:
                    System.out.println("Scelta non valida. Riprova.");
                    break;
            }
        } while (scelta != 4);

        em.close();
        emf.close();

    }

    private static void runAdminSide(Scanner scanner) {
        //__________________________LATO ADMIN_______________________________________________________
//  Imposto la struttura dello scanner dalla parte del gestore, poi bisognerà cambiare alcuni valori e nomi
// riga di prova

        int scelta;
        long mezzo_id, tratta_id, ticket_id;
        do {

            System.out.println("----------- BENVENUTO NELLA SEZIONE GESTIONE DELLA MAGICTRANSPORTsrl, SCEGLI UN'OPERAZIONE:-----------");

            System.out.println("1) Cerca i biglietti vidimati su un mezzo specifico; ");
            System.out.println("2) Cerca i biglietti emessi in base alla data; ");
            System.out.println("3) Cerca i biglietti venduti da uno specifico distributore/rivenditore; ");
            System.out.println("4) Cerca i periodi di servizio e manutenzione di un mezzo in base alla data;");
            System.out.println("5) Verifica il tempo di percorrenza medio in base alla tratta");
            System.out.println("6) Torna al menu principale ");


            scelta = scanner.nextInt();
            scanner.nextLine();
            EntityManager em = emf.createEntityManager();
            MezzoDao md=new MezzoDao(em);

            switch (scelta) {

                case 1:
                    System.out.println("Inserisci l'id di un mezzo per vedere i biglietti vitimati in totale su di esso:");
                    long Mezzo_id = Long.parseLong(scanner.nextLine());
                    md.getBigliettiConvalidatiPerMezzo(Mezzo_id);
                    break;

                case 2:
                    System.out.println("Inserisci le due date  per visualizzare i biglietti emessi in quel periodo: ");
                    System.out.println("data di inizio periodo");
                    LocalDate dataS = LocalDate.parse(scanner.nextLine());
                    System.out.println("data di fine periodo");
                    LocalDate dataE=LocalDate.parse(scanner.nextLine());
                    TicketDao rd=new TicketDao(em);
                    List<Ticket> ticket = rd.findBigliettiEmessiByTimeLapse(dataS,dataE);
                    if (ticket.isEmpty()) {
                        System.out.println("Non ci sono biglietti emessi in questa data.");
                    } else {
                        System.out.println("Numero biglietti emessi nel periodo" + dataS + " - "+dataE);
                        for (Ticket tickets1 : ticket) {
                            System.out.println(tickets1);
                        }
                    }

//                case 3:
//                    System.out.println("Immetti l'id del distributore:");
//                    long distributore_Id = Long.parseLong(scanner.nextLine());
//                    System.out.println("Questo distributore ha venduto " + rd.nBigliettiEmessi(distributore_id).size() + " ticket");
//                    break;
//
//
//                case 4:
//                    System.out.println("Immetti una data per vedere lo stato del mezzo:");
//                    String input = scanner.nextLine();
//                    LocalDate StatoMezzo;
//                    try {
//                        StatoMezzo = LocalDate.parse(input);
//                    } catch (DateTimeParseException e) {
//                        System.out.println("Data non valida. Accertati di inserire la data nel formato YYYY-MM-DD.");
//                        break;
//                    }
//
//                    System.out.println("Immetti l'id del mezzo di cui vuoi vedere lo stato nella data precedentemente selezionata:");
//                    mezzo_Id = Long.parseLong(scanner.nextLine());
//                    Mezzo mezzoX = em.find(Mezzo.class, mezzo_Id);
//
//                    if (mezzoX != null | dataStatoVeicolo != null) {
//                        System.out.println("Stato veicolo in data " + dataStatoVeicolo + " :");
//                        md.ricercaPeriodiDiStato(dataStatoVeicolo, mezzoX);
//                    } else {
//                        System.out.println("Non è stato trovato alcun mezzo con id: " + mezzoId + " in data " + dataStatoVeicolo);
//                    }
//                    break;

                case 6:
                    System.out.println("Tornando al menu principale");
                    return;
                default:
                    System.out.println("Scelta non valida. Riprova.");
                    break;

            }
        } while (scelta != 6);

    }
}






















