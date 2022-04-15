package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class UgostiteljskiObjekat implements Serializable {

    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Ugostitelj ugostitelj;
}
