package entities;

import enums.TipoMezzo;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="mezzi")
public class Mezzo {

    @Id
    @GeneratedValue
    private  long id;


    @Column(name = "tipo_mezzo")
    @Enumerated(EnumType.STRING)
    private TipoMezzo tipo;

    @OneToMany(mappedBy = "mezzo")
    private List<Ticket> tickets;
    @ManyToOne
    private Tratta tratta;
    @OneToMany(mappedBy = "mezzo")
    private List<Operatività>operatività;
    @Column(name = "biglietti_vidimati")
    private int biglietti_vidimati;

    @Column(name = "data_vidimazione")
    private Date data_vidimazione;


    public Mezzo(){}
    public Mezzo(long id, TipoMezzo tipo, int biglietti_vidimati, Date data_vidimazione) {
        this.id = id;

        this.tipo = tipo;

        this.biglietti_vidimati = biglietti_vidimati;
        this.data_vidimazione = data_vidimazione;
    }

    public long getId() {
        return id;
    }







    public TipoMezzo getTipo() {
        return tipo;
    }

    public void setTipo(TipoMezzo tipo) {
        this.tipo = tipo;
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
                ", tipo=" + tipo +
                ", biglietti_vidimati=" + biglietti_vidimati +
                ", data_vidimazione=" + data_vidimazione +
                '}';
    }
}
