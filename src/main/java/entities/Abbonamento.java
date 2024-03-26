package entities;

import enums.TipoAbbonamento;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Abbonamento")
public class Abbonamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "data_inizio")
    private Date dataInizio;

    @Column(name = "data_scadenza")
    private Date dataScadenza;
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

    public Abbonamento(Date dataInizio, Date dataScadenza, TipoAbbonamento tipoAbbonamento, Distributore distributore) {
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

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
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
