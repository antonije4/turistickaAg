package enums;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public enum UgostiteljskiObjekatTip {
    Hotel("Hotel"),
    Hostel("Hostel"),
    Motel("Motel"),
    Rezort("Rezort"),
    Apartmani("Apartmani");

    private final String key;

    public String getKey() {
        return key;
    }

    UgostiteljskiObjekatTip(String key) {
        this.key = key;
    }
}
