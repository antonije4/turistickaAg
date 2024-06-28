package enums;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public enum UgostiteljType {
    PravnoLice("Pravno lice"),
    Preduzetnik("Preduzetnik"),
    FizickoLice("Fizicko lice"),
    Ustanova("Ustanova");

    private final String key;

    public String getKey() {
        return key;
    }

    UgostiteljType(String key) {
        this.key = key;
    }
}
