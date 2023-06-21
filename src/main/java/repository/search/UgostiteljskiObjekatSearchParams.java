package repository.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UgostiteljskiObjekatSearchParams extends SearchParams {
    private String name;
    private String ugostiteljUsername;
    private Boolean categorized;

    public void reset() {
        name=null;
        ugostiteljUsername = null;
        categorized = null;
    }
}
