package entities;

import entities.info.AdditionalInfo;
import enums.UgostiteljType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Ugostitelj extends User{

    private String firstName;

    private String lastName;

    @NotNull
    private UgostiteljType ugostiteljType;

    @OneToMany(mappedBy = "ugostitelj", fetch = FetchType.EAGER)
    private List<UgostiteljskiObjekat> ugostiteljskiObjekti;

    @OneToMany(mappedBy = "ugostitelj", fetch = FetchType.EAGER)
    private List<CategorizationRequest> categorizationRequests;

    @Embedded
    private AdditionalInfo additionalInfo;

    public void linkUgostiteljskiObjekat(UgostiteljskiObjekat ugostiteljskiObjekat) {
        ugostiteljskiObjekti.add(ugostiteljskiObjekat);
    }

    public void unlinkUgostiteljskiObjekat(UgostiteljskiObjekat ugostiteljskiObjekat) {
        ugostiteljskiObjekti.remove(ugostiteljskiObjekat);
    }
}
