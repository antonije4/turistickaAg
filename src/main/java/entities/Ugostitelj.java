package entities;

import enums.UgostiteljType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Ugostitelj extends Korisnik {

    @NotNull
    protected UgostiteljType tipUgostitelja;

    @OneToMany(mappedBy = "ugostitelj", cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    protected Set<UgostiteljskiObjekat> ugostiteljskiObjekti;

    @OneToMany(mappedBy = "ugostitelj",cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    protected Set<ZahtevZaKategorizaciju> zahteviZaKategorizaciju;

    @OneToOne(fetch = FetchType.EAGER)
    protected Sanduce sanduce;

    public void linkUgostiteljskiObjekat(UgostiteljskiObjekat ugostiteljskiObjekat) {
        ugostiteljskiObjekti.add(ugostiteljskiObjekat);
    }

    public void unlinkUgostiteljskiObjekat(UgostiteljskiObjekat ugostiteljskiObjekat) {
        ugostiteljskiObjekti.remove(ugostiteljskiObjekat);
    }

    public void unlinkCategorizationRequest(ZahtevZaKategorizaciju zahtevZaKategorizaciju) {
        this.zahteviZaKategorizaciju.remove(zahtevZaKategorizaciju);
    }
}
