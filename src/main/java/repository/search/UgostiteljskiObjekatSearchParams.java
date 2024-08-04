package repository.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UgostiteljskiObjekatSearchParams extends SearchParams {
    private String naziv;
    private String ugostiteljUsername;
    private Boolean kategorizovan;

    public void reset() {
        naziv =null;
        ugostiteljUsername = null;
        kategorizovan = null;
    }
}
