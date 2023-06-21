package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter @Setter
public class PrivilegedUser extends User {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private Date dateOfBirth;
}
