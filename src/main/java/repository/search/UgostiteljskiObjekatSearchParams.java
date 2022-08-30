package repository.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UgostiteljskiObjekatSearchParams extends SearchParams {
    private String name;
    private String ugostiteljFirstName;
    private String ugostiteljLastName;

    public void reset() {
        name=null;
        ugostiteljFirstName=null;
        ugostiteljLastName=null;
    }
}
