package entities;


import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {

    // Chiave Primaria composta da userId
    @Id
    @Column(name = "user_id")
    private int userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int ticketId;


    @Column(name = "validita")
    private String validita;

    @ManyToOne
    @JoinColumn(name = "distributore_id")
    private double distributore;

    @ManyToOne
    @JoinColumn(name = "tratta_id")
    private String tratta;

    //----------------------------//

    public Ticket() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public int getTicketId() {
        return ticketId;
    }


    public String getValidita() {
        return validita;
    }

    public void setValidita(String validita) {
        this.validita = validita;
    }


    public double getDistributore() {
        return distributore;
    }

    public <Distributore> void setDistributore(Distributore distributore) {
        this.distributore = (double) distributore;
    }


    public String getTratta() {
        return tratta;
    }

    public void setTratta(String tratta) {
        this.tratta = tratta;
    }
}


