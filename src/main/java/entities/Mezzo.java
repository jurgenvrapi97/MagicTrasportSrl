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
    @JoinColumn(name="tratta_id")
    private Tratta tratta;
    @OneToMany(mappedBy = "mezzo")
    private List<Operatività>operatività;
    @Column(name = "biglietti_vidimati")
    private int biglietti_vidimati;



    public Mezzo(){}
    public Mezzo(long id, TipoMezzo tipo, int biglietti_vidimati) {
        this.id = id;

        this.tipo = tipo;

        this.biglietti_vidimati = biglietti_vidimati;

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



    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }



    @Override
    public String toString() {
        return "Mezzo{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", biglietti_vidimati=" + biglietti_vidimati +

                '}';
    }



    public void incrementaBigliettiVidimati() {
        this.biglietti_vidimati++;
    }
}
