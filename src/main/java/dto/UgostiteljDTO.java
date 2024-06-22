package dto;

import enums.UgostiteljType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UgostiteljDTO extends UserDTO{
    private UgostiteljType ugostiteljType;
    private String firstName;
    private String lastName;
    private List<UgostiteljskiObjekatDTO> ugostiteljskiObjekti;

    public UgostiteljDTO() {
        ugostiteljType = UgostiteljType.FizickoLice;
    }
}

