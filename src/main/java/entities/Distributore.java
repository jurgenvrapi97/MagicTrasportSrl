package entities;

import enums.Stato;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Distributore")
public class Distributore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "stato")
    @Enumerated(EnumType.STRING)
    private Stato stato;
    @Column(name = "location")
    private String location;
    @Column(name = "n_biglietti_emessi")
    private int nBigliettiEmessi;

    @Column(name = "data_emissione")
    private Date dataEmissione;

    @OneToMany(mappedBy = "distributore")
    private List<Abbonamento> abbonamenti;

    @OneToMany(mappedBy = "distributore")
    private List<Ticket>tickets;

    public Distributore() {
    }

    public Distributore(Stato stato, String location, int nBigliettiEmessi, Date dataEmissione, List<Abbonamento> abbonamenti) {
        this.stato = stato;
        this.location = location;
        this.nBigliettiEmessi = nBigliettiEmessi;
        this.dataEmissione = dataEmissione;
        this.abbonamenti = abbonamenti;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getnBigliettiEmessi() {
        return nBigliettiEmessi;
    }

    public void setnBigliettiEmessi(int nBigliettiEmessi) {
        this.nBigliettiEmessi = nBigliettiEmessi;
    }

    public Date getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(Date dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public List<Abbonamento> getAbbonamenti() {
        return abbonamenti;
    }

    public void setAbbonamenti(List<Abbonamento> abbonamenti) {
        this.abbonamenti = abbonamenti;
    }

    @Override
    public String toString() {
        return "Distributore{" +
                "id=" + id +
                ", stato=" + stato +
                ", location='" + location + '\'' +
                ", nBigliettiEmessi=" + nBigliettiEmessi +
                ", dataEmissione=" + dataEmissione +
                ", abbonamenti=" + abbonamenti +
                '}';
    }
}
