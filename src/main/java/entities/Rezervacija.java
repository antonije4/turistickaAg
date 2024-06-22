package entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter @Setter
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

    public Rezervacija() {
    }

    private Rezervacija(Builder builder) {
        this.id = builder.id;
        this.turista = builder.turista;
        this.ugostiteljskiObjekat = builder.ugostiteljskiObjekat;
        this.cenaBoravisneTakse = builder.boravisnaTaksaPrice;
        this.boravisnaTaksaPlacena = builder.boravisnaTaksaPaid;
        this.pocetniDatum = builder.startingDate;
        this.krajnjiDatum = builder.endingDate;
        this.brojLjudi = builder.numberOfPeople;
    }

    public static class Builder {
        private long id;
        private Turista turista;
        private UgostiteljskiObjekat ugostiteljskiObjekat;
        private String boravisnaTaksaPrice;
        private boolean boravisnaTaksaPaid;
        private LocalDate startingDate;
        private LocalDate endingDate;
        private int numberOfPeople;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder tourist(Turista turista) {
            this.turista = turista;
            return this;
        }

        public Builder ugostiteljskiObjekat(UgostiteljskiObjekat ugostiteljskiObjekat) {
            this.ugostiteljskiObjekat = ugostiteljskiObjekat;
            return this;
        }

        public Builder boravisnaTaksaPrice(String boravisnaTaksaPrice) {
            this.boravisnaTaksaPrice = boravisnaTaksaPrice;
            return this;
        }

        public Builder boravisnaTaksaPaid(boolean boravisnaTaksaPaid) {
            this.boravisnaTaksaPaid = boravisnaTaksaPaid;
            return this;
        }

        public Builder startingDate(LocalDate startingDate) {
            this.startingDate = startingDate;
            return this;
        }

        public Builder endingDate(LocalDate endingDate) {
            this.endingDate = endingDate;
            return this;
        }

        public Builder numberOfPeople(int numberOfPeople) {
            this.numberOfPeople = numberOfPeople;
            return this;
        }

        public Rezervacija build() {
            return new Rezervacija(this);
        }
    }
}
