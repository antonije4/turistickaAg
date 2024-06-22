package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Korisnik implements Serializable {
    @Id
    @GeneratedValue
    @NotNull
    protected long id;

    @NotNull
    @Column(unique = true)
    protected String username;
    @NotNull
    protected String password;
    @NotNull
    @Column(unique = true)
    protected String email;
}
