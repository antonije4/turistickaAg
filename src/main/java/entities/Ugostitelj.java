package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
public class Ugostitelj extends Client implements Serializable {

    @NotNull
    private String jmbg;

    @OneToMany(mappedBy = "ugostitelj")
    private Set<UgostiteljskiObjekat> ugostiteljskiObjekti;

    private

}
