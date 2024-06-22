package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter @Setter
public class UgostiteljskiObjekat implements Serializable {

    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @NotNull
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private Ugostitelj ugostitelj;

    @OneToMany(mappedBy = "ugostiteljskiObjekat",cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<ZahtevZaKategorizaciju> zahteviZaKategorizaciju;

    @OneToMany(mappedBy = "ugostiteljskiObjekat", fetch = FetchType.EAGER)
    private Set<Rezervacija> rezervacije;

    private boolean categorized;
    private LocalDate categorizationExpiryDate;
    private boolean notifiedOfCategorizationExpiry;

    public void unlinkReservation(Rezervacija rezervacija) {
        rezervacije.removeIf(reservation1 -> reservation1.getId() == rezervacija.getId());
    }

    public void unlinkCategorizationRequest(ZahtevZaKategorizaciju zahtevZaKategorizaciju) {
        zahteviZaKategorizaciju.removeIf(categorizationRequest1 -> categorizationRequest1.getId() == zahtevZaKategorizaciju.getId());
    }
}
