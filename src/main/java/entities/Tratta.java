package entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="tratta")
@NamedQuery(name="getAverageTimeOfRoute",query="SELECT t.percorrenza_media FROM Tratta t WHERE t.id=:tratta_id")
@NamedQuery(name="getTripCountForMezzoOnTratta", query="SELECT COUNT(m) FROM Mezzo m WHERE m.tratta.id = :tratta_id AND m.id = :mezzo_id")

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

    public  Tratta(){}

    public Tratta( String partenza, String destinazione, int percorrenza_media) {
        this.partenza = partenza;
        this.destinazione = destinazione;
        this.percorrenza_media = percorrenza_media;
    }

    @Override
    public String toString() {
        return "Tratta{" +
                "id=" + id +
                ", partenza='" + partenza + '\'' +
                ", destinazione='" + destinazione + '\'' +
                ", percorrenza_media=" + percorrenza_media +
                '}'+"\n";
    }
}


