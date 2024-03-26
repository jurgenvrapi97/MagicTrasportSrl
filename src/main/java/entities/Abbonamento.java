package entities;

import enums.TipoAbbonamento;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Abbonamento")
public class Abbonamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "data_inizio")
    private LocalDate dataInizio;

    @Column(name = "data_scadenza")
    private LocalDate dataScadenza;
    @Column(name = "tipo_abbonamento")
    @Enumerated(EnumType.STRING)
    private TipoAbbonamento tipoAbbonamento;

    @ManyToOne
    @JoinColumn(name = "distributore_id")
    private Distributore distributore;
    @OneToOne
    private Card card;

    public Abbonamento() {
    }

    public Abbonamento(LocalDate dataInizio, LocalDate dataScadenza, TipoAbbonamento tipoAbbonamento, Distributore distributore) {
        this.dataInizio = dataInizio;
        this.dataScadenza = dataScadenza;
        this.tipoAbbonamento = tipoAbbonamento;
        this.distributore = distributore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public TipoAbbonamento getTipoAbbonamento() {
        return tipoAbbonamento;
    }

    public void setTipoAbbonamento(TipoAbbonamento tipoAbbonamento) {
        this.tipoAbbonamento = tipoAbbonamento;
    }

    public Distributore getDistributore() {
        return distributore;
    }

    public void setDistributore(Distributore distributore) {
        this.distributore = distributore;
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "id=" + id +
                ", dataInizio=" + dataInizio +
                ", dataScadenza=" + dataScadenza +
                ", tipoAbbonamento=" + tipoAbbonamento +
                ", distributore=" + distributore +
                '}';
    }
}
