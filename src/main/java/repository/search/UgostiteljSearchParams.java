package repository.search;

import enums.UgostiteljType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UgostiteljSearchParams extends SearchParams{
    private String ime;
    private String prezime;
    private List<UgostiteljType> ugostiteljTypes;

    public void reset() {
        ime = null;
        prezime = null;
        ugostiteljTypes.clear();
    }
}
