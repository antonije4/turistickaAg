package repository.search;

import enums.UgostiteljskiObjekatTip;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UgostiteljskiObjekatSearchParams extends SearchParams {
    private String naziv;
    private String ugostiteljUsername;
    private Boolean kategorizovan;
    private List<UgostiteljskiObjekatTip> types;

    public void reset() {
        naziv =null;
        ugostiteljUsername = null;
        kategorizovan = null;
    }
}
