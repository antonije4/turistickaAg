package dto;

import entities.info.*;
import enums.UgostiteljType;
import lombok.Data;

public class UgostiteljDTO extends UserDTO{
    private String ugostiteljType;
    private AdditionalInfo additionalInfo;

    public String getUgostiteljType() {
        return ugostiteljType;
    }

    public void setUgostiteljType(String ugostiteljType) {
        this.ugostiteljType = ugostiteljType;
    }

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public UgostiteljDTO() {
        ugostiteljType = UgostiteljType.FizickoLice.getKey();
    }
}

