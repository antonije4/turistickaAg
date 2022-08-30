package entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

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

    @OneToMany(mappedBy = "ugostiteljskiObjekat", fetch = FetchType.EAGER)
    private List<CategorizationRequest> categorizationRequests;

    private boolean categorized;
}
