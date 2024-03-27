package entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="tratta")
public class Tratta {
    @Id
    @GeneratedValue
    private long id;
    @Column(name="partenza")
    private String partenza;

    @Column(name="destinazione")
    private String destinazione;

    @Column(name="percorrenza_media")
    private int percorrenza_media;


    @OneToMany(mappedBy = "tratta")
    private List<Mezzo> mezzi;

    public Tratta(){};

    public Tratta( String partenza, String destinazione, int percorrenza_media) {
        this.partenza = partenza;
        this.destinazione = destinazione;
        this.percorrenza_media = percorrenza_media;
    }

    public void setPartenza(String partenza) {
        this.partenza = partenza;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    public void setPercorrenza_media(int percorrenza_media) {
        this.percorrenza_media = percorrenza_media;
    }

    public void setMezzi(List<Mezzo> mezzi) {
        this.mezzi = mezzi;
    }

}


