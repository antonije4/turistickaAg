package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter @Setter
public class PrivilegovaniKorisnik extends Korisnik {
    @NotNull
    private String ime;

    @NotNull
    private String prezime;

    private Date datumRodjenja;
}
