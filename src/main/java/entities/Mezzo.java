package entities;

import enums.DisponibilitàMezzo;
import enums.TipoMezzo;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="mezzi")
public class Mezzo {

    @Id
    @GeneratedValue
    private  long id;

    @Column(name = "disponibilità_mezzo")
    @Enumerated(EnumType.STRING)
    private DisponibilitàMezzo disponibilità;
    @Column(name = "tipo_mezzo")
    @Enumerated(EnumType.STRING)
    private TipoMezzo tipo;


    @Column(name = "tratta_id")

    private long tratta_id;

    @Column(name = "biglietti_vidimati")
    private int biglietti_vidimati;

    @Column(name = "data_vidimazione")
    private Date data_vidimazione;


    public Mezzo(){}
    public Mezzo(long id, DisponibilitàMezzo disponibilità, TipoMezzo tipo, long tratta_id, int biglietti_vidimati, Date data_vidimazione) {
        this.id = id;
        this.disponibilità = disponibilità;
        this.tipo = tipo;
        this.tratta_id = tratta_id;
        this.biglietti_vidimati = biglietti_vidimati;
        this.data_vidimazione = data_vidimazione;
    }

    public long getId() {
        return id;
    }



    public DisponibilitàMezzo getDisponibilità() {
        return disponibilità;
    }

    public void setDisponibilità(DisponibilitàMezzo disponibilità) {
        this.disponibilità = disponibilità;
    }

    public TipoMezzo getTipo() {
        return tipo;
    }

    public void setTipo(TipoMezzo tipo) {
        this.tipo = tipo;
    }

    public long getTratta_id() {
        return tratta_id;
    }

    public void setTratta_id(long tratta_id) {
        this.tratta_id = tratta_id;
    }

    public int getBiglietti_vidimati() {
        return biglietti_vidimati;
    }

    public void setBiglietti_vidimati(int biglietti_vidimati) {
        this.biglietti_vidimati = biglietti_vidimati;
    }

    public Date getData_vidimazione() {
        return data_vidimazione;
    }

    public void setData_vidimazione(Date data_vidimazione) {
        this.data_vidimazione = data_vidimazione;
    }

    @Override
    public String toString() {
        return "Mezzo{" +
                "id=" + id +
                ", disponibilità=" + disponibilità +
                ", tipo=" + tipo +
                ", tratta_id=" + tratta_id +
                ", biglietti_vidimati=" + biglietti_vidimati +
                ", data_vidimazione=" + data_vidimazione +
                '}';
    }
}