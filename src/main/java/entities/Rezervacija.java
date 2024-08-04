package entities;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rezervacija implements Serializable {

    @Id
    @GeneratedValue
    @NotNull
    private long id;
    @ManyToOne
    private Turista turista;
    @ManyToOne(fetch = FetchType.EAGER)
    private UgostiteljskiObjekat ugostiteljskiObjekat;
    private String cenaBoravisneTakse;
    private boolean boravisnaTaksaPlacena;
    private LocalDate pocetniDatum;
    private LocalDate krajnjiDatum;
    private int brojLjudi;

}
