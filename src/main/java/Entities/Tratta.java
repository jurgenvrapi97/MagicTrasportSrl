package Entities;

import jakarta.persistence.*;

@Entity
@Table(name="tratta")
public class Tratta {
    @Column(name="partenza")
    private String partenza;

    @Column(name="destinazione")
    private String destinazione;

    @Column(name="percorrenza_media")
    private int percorrenza_media;

    //@ManyToMany
    //inserire poi qui la conjunction table fra Tratta e Abbonamento

    //@OneToMany
    //inserire poi qui la relazione con ticket

    //@OneToMany
    //inserire poi qui la relazione con mezzo

    public Tratta( String partenza, String destinazione, int percorrenza_media) {
        this.partenza = partenza;
        this.destinazione = destinazione;
        this.percorrenza_media = percorrenza_media;
    }
 }

}
