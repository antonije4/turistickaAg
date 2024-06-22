package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class ZahtevZaKategorizaciju implements Serializable {


    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    private UgostiteljskiObjekat ugostiteljskiObjekat;

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Ugostitelj ugostitelj;

    private LocalDate datumZahteva;
    private boolean odobren;
    private boolean pregledan;
    private String korisnikPregleda;
    private String podaciKategorizacije;
}
