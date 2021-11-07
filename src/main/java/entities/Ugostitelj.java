package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Ugostitelj implements Serializable {
    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @NotNull
    private String ime;


}
