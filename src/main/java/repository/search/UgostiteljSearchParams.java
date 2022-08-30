package repository.search;

import enums.UgostiteljType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UgostiteljSearchParams extends SearchParams{
    private String firstName;
    private String lastName;
    private List<UgostiteljType> ugostiteljTypes;

    public void reset() {
        firstName = null;
        lastName = null;
        ugostiteljTypes.clear();
    }
}
