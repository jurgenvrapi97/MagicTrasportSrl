package entities;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tickets")
@NamedQuery(
        name = "findBigliettiEmessiByTimeLapse",
        query = "SELECT t FROM Ticket t WHERE t.dataEmisione BETWEEN :start_date AND :end_date"
)
public class Ticket {

    // Chiave Primaria composta da userId


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int ticketId;

    @Column(name = "data_emissione")
    private LocalDate dataEmisione;
    @Column(name = "validita")
    private String validita;

    @Column(name = "data_vidimazione")
    private LocalDate dataVidimazione;

    @ManyToOne
    @JoinColumn(name = "distributore_id")
    private Distributore distributore;


    @ManyToOne
    @JoinColumn(name="mezzo_id")
    private Mezzo mezzo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    //----------------------------//

    public Ticket() {

    }

    public Ticket(LocalDate dataEmisione, String validita, LocalDate dataVidimazione, Distributore distributore, Mezzo mezzo, User user) {
        this.dataEmisione = dataEmisione;
        this.validita = validita;
        this.dataVidimazione = dataVidimazione;
        this.distributore = distributore;
        this.mezzo = mezzo;
        this.user = user;
    }


    public LocalDate getDataEmisione() {
        return dataEmisione;
    }

    public LocalDate getDataVidimazione() {
        return dataVidimazione;
    }

    public User getUser() {
        return user;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getValidita() {
        return validita;
    }

    public void setValidita(String validita) {
        this.validita = validita;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public void setDataVidimazione(LocalDate dataVidimazione) {
        this.dataVidimazione = dataVidimazione;
    }

    public void setDataEmisione(LocalDate dataEmisione) {
        this.dataEmisione = dataEmisione;
    }

    public Distributore getDistributore() {
        return distributore;
    }

    public void setDistributore(Distributore distributore) {
        this.distributore = distributore;
    }


    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }



    public void vidimare() {
        // Controlla se il biglietto è già stato vidimato
        if (this.dataVidimazione != null) {
            throw new IllegalStateException("Il biglietto è già stato vidimato.");
        }

        // Vidima il biglietto
        this.dataVidimazione = LocalDate.now();
        this.validita = "non valido";
        // Incrementa il contatore del mezzo
        this.mezzo.incrementaBigliettiVidimati();
    }
}



