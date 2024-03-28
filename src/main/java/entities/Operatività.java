package entities;
import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name="operatività")
public class Operatività {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "data_inizio_manutenzione")
    private LocalDate dataInizioManutenzione;

    @Column(name = "data_fine_manutenzione")
    private LocalDate dataFineManutenzione;

    @Column(name = "data_inizio_servizio")
    private LocalDate dataInizioServizio;

    @Column(name = "data_fine_servizio")
    private LocalDate dataFineServizio;

    @ManyToOne
    @JoinColumn(name="mezzo_id")
    private Mezzo mezzo;

    public Operatività(){};

    public Operatività(LocalDate dataInizioManutenzione, LocalDate dataFineManutenzione, LocalDate dataInizioServizio, LocalDate dataFineServizio, Mezzo mezzo) {
        this.dataInizioManutenzione = dataInizioManutenzione;
        this.dataFineManutenzione = dataFineManutenzione;
        this.dataInizioServizio = dataInizioServizio;
        this.dataFineServizio = dataFineServizio;
        this.mezzo = mezzo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDataInizioManutenzione() {
        return dataInizioManutenzione;
    }

    public void setDataInizioManutenzione(LocalDate dataInizioManutenzione) {
        this.dataInizioManutenzione = dataInizioManutenzione;
    }

    public LocalDate getDataFineManutenzione() {
        return dataFineManutenzione;
    }

    public void setDataFineManutenzione(LocalDate dataFineManutenzione) {
        this.dataFineManutenzione = dataFineManutenzione;
    }

    public LocalDate getDataInizioServizio() {
        return dataInizioServizio;
    }

    public void setDataInizioServizio(LocalDate dataInizioServizio) {
        this.dataInizioServizio = dataInizioServizio;
    }

    public LocalDate getDataFineServizio() {
        return dataFineServizio;
    }

    public void setDataFineServizio(LocalDate dataFineServizio) {
        this.dataFineServizio = dataFineServizio;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    @Override
    public String toString() {
        return "Operatività{" +
                "id=" + id +
                ", dataInizioManutenzione=" + dataInizioManutenzione +
                ", dataFineManutenzione=" + dataFineManutenzione +
                ", dataInizioServizio=" + dataInizioServizio +
                ", dataFineServizio=" + dataFineServizio +
                ", mezzo=" + mezzo +
                '}';
    }
}

