package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Client implements Serializable {

    @Id
    @GeneratedValue
    @NotNull
    protected long id;

    @NotNull
    protected String username;

    @NotNull
    protected String password;

    @NotNull
    protected String email;
}
