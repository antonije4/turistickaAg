package entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Poruka implements Serializable {

    @Id
    @GeneratedValue
    @NotNull
    private long id;
    private boolean procitana;
    @ManyToOne
    private Sanduce sanduce;
    private String sadrzaj;
    private LocalDateTime datumPrispeca;
}
