package entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class UgostiteljskiObjekat implements Serializable {

    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @NotNull
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private Ugostitelj ugostitelj;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ugostitelj getUgostitelj() {
        return ugostitelj;
    }

    public void setUgostitelj(Ugostitelj ugostitelj) {
        this.ugostitelj = ugostitelj;
    }
}
