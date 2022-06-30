package entities.info;

import entities.Ugostitelj;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import java.io.Serializable;

@MappedSuperclass
@Embeddable
public abstract class AdditionalInfo implements Serializable {
}
