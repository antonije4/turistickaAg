package repository.search;

import lombok.Getter;
import lombok.Setter;

public class UgostiteljskiObjekatSearchParams extends SearchParams {
    private String name;
    private String ugostiteljFirstName;
    private String ugostiteljLastName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUgostiteljFirstName() {
        return ugostiteljFirstName;
    }

    public void setUgostiteljFirstName(String ugostiteljFirstName) {
        this.ugostiteljFirstName = ugostiteljFirstName;
    }

    public String getUgostiteljLastName() {
        return ugostiteljLastName;
    }

    public void setUgostiteljLastName(String ugostiteljLastName) {
        this.ugostiteljLastName = ugostiteljLastName;
    }

    public void reset() {
        name=null;
        ugostiteljFirstName=null;
        ugostiteljLastName=null;
    }
}
