package entities;

import entities.info.AdditionalInfo;
import enums.UgostiteljType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Ugostitelj extends User{

    private String firstName;

    private String lastName;

    @NotNull
    private UgostiteljType ugostiteljType;

    @OneToMany(mappedBy = "ugostitelj")
    private Set<UgostiteljskiObjekat> ugostiteljskiObjekti;

    @Embedded
    private AdditionalInfo additionalInfo;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UgostiteljType getUgostiteljType() {
        return ugostiteljType;
    }

    public void setUgostiteljType(UgostiteljType ugostiteljType) {
        this.ugostiteljType = ugostiteljType;
    }

    public Set<UgostiteljskiObjekat> getUgostiteljskiObjekti() {
        return ugostiteljskiObjekti;
    }

    public void setUgostiteljskiObjekti(Set<UgostiteljskiObjekat> ugostiteljskiObjekti) {
        this.ugostiteljskiObjekti = ugostiteljskiObjekti;
    }

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
