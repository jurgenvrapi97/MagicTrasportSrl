package entities;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tickets")
public class Ticket {

    // Chiave Primaria composta da userId
    @Column(name = "user_id")
    private int userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int ticketId;

    @Column(name = "data_emissione")
    private LocalDate dataEmisione;
    @Column(name = "validita")
    private String validita;

    @ManyToOne
    @JoinColumn(name = "distributore_id")
    private Distributore distributore;


    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private Mezzo mezzo;
    //----------------------------//

    public Ticket() {

    }

    public int getUserId() {
        return userId;
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
}



