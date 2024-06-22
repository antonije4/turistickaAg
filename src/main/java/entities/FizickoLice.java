package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class FizickoLice extends Ugostitelj {
    private String ime;
    private String prezime;
    private String adresa;
    private String jmbg;
}
