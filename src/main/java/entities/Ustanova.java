package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Ustanova extends Ugostitelj {
    private String naziv;
    private String vrstaUstanove;
    private String adresa;
}
