package entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@MappedSuperclass
@Getter @Setter
public abstract class User implements Serializable {
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
