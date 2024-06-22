package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
public class Turista extends Korisnik {
    @NotNull
    private String ime;

    @NotNull
    private String prezime;

    private Date datumRodjenja;

    @OneToMany(mappedBy = "turista", fetch = FetchType.EAGER)
    private List<Rezervacija> rezervacijaList;

    public void unlinkReservation(Rezervacija rezervacija) {
        rezervacijaList.removeIf(reservation1 -> reservation1.getId() == rezervacija.getId());
    }
}
