package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter @Setter
public class Inbox implements Serializable {

    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @OneToOne
    private Ugostitelj ugostitelj;

    @OneToMany(mappedBy = "inbox")
    private List<Message> messages;

}
