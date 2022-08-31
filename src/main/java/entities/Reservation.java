package entities;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class Reservation implements Serializable {

    @Id
    @GeneratedValue
    @NotNull
    private long id;
    @ManyToOne
    private Tourist tourist;
    @ManyToOne(fetch = FetchType.EAGER)
    private UgostiteljskiObjekat ugostiteljskiObjekat;
    private String boravisnaTaksaPrice;
    private boolean boravisnaTaksaPaid;
    private LocalDate startingDate;
    private LocalDate endingDate;

    public Reservation() {

    }

    @Builder
    public Reservation(Tourist tourist, UgostiteljskiObjekat ugostiteljskiObjekat, String boravisnaTaksaPrice, boolean boravisnaTaksaPaid, LocalDate startingDate, LocalDate endingDate) {
        this.tourist = tourist;
        this.ugostiteljskiObjekat = ugostiteljskiObjekat;
        this.boravisnaTaksaPrice = boravisnaTaksaPrice;
        this.boravisnaTaksaPaid = boravisnaTaksaPaid;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
    }
}
