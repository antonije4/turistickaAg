package dto;

import enums.UgostiteljType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UgostiteljDTO extends UserDTO{
    private UgostiteljType tipUgostitelja;
    private String ime;
    private String prezime;
    private String naziv;
    private List<UgostiteljskiObjekatDTO> ugostiteljskiObjekti;

    public UgostiteljDTO() {
        tipUgostitelja = UgostiteljType.FizickoLice;
    }
}

