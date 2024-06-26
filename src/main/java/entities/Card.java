package entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="Card")
@NamedQuery(name="findIsExpired",query="SELECT c FROM Card c WHERE c.id=:id AND c.data_di_scadenza >=:today"
)
public class Card {
    //attributes
    @Id
    @GeneratedValue
    @Column(name="card_number")
    private long id;
    private LocalDate data_di_scadenza;
    private LocalDate data_di_sottoscrizione;
    @OneToOne
    private entities.User user;
    @OneToOne
    @JoinColumn(name = "abbonamento_id")
    private entities.Abbonamento abbonamento;


    //constructors

    public Card() {
    }

    public Card(LocalDate data_di_sottoscrizione) {
        this.setData_di_sottoscrizione(data_di_sottoscrizione);
        this.setData_di_scadenza(data_di_sottoscrizione);
    }
    //getters

    public long getId() {
        return id;
    }

    public LocalDate getData_di_scadenza() {
        return data_di_scadenza;
    }

    public LocalDate getData_di_sottoscrizione() {
        return data_di_sottoscrizione;
    }


    //setters


    public void setAbbonamento(entities.Abbonamento abbonamento) {
        this.abbonamento = abbonamento;
    }

    public void setUser(entities.User user) {
        this.user = user;
    }

    public void setData_di_scadenza(LocalDate data_di_sottoscrizione) {
        this.data_di_scadenza = data_di_sottoscrizione.plusYears(1);
    }

    public void setData_di_sottoscrizione(LocalDate data_di_sottoscrizione) {
        this.data_di_sottoscrizione = data_di_sottoscrizione;
    }

    //methods

    @java.lang.Override
    public java.lang.String toString() {
        return "Card{" +
                "id=" + id +
                ", data_di_scadenza=" + data_di_scadenza +
                ", data_di_sottoscrizione=" + data_di_sottoscrizione +
                '}';
    }
    public boolean isExpired() {
        LocalDate today = LocalDate.now();
        return data_di_scadenza.isBefore(today);
    }


}