package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter @Setter
public class CategorizationRequest implements Serializable {


    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private UgostiteljskiObjekat ugostiteljskiObjekat;

    @ManyToOne(fetch = FetchType.EAGER)
    private Ugostitelj ugostitelj;

    private LocalDate dateOfRequest;
    private boolean approved;
    private boolean reviewed;
    private String userReviewed;
    private String categorizationInfo;
}
