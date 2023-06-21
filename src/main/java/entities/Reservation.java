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
    private int numberOfPeople;

    public Reservation() {
    }

    private Reservation(Builder builder) {
        this.id = builder.id;
        this.tourist = builder.tourist;
        this.ugostiteljskiObjekat = builder.ugostiteljskiObjekat;
        this.boravisnaTaksaPrice = builder.boravisnaTaksaPrice;
        this.boravisnaTaksaPaid = builder.boravisnaTaksaPaid;
        this.startingDate = builder.startingDate;
        this.endingDate = builder.endingDate;
        this.numberOfPeople = builder.numberOfPeople;
    }

    public static class Builder {
        private long id;
        private Tourist tourist;
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

        public Builder tourist(Tourist tourist) {
            this.tourist = tourist;
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

        public Reservation build() {
            return new Reservation(this);
        }
    }
}
