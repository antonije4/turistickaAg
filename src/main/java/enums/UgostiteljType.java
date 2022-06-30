package enums;

import lombok.Getter;
import lombok.Setter;

public enum UgostiteljType {
    PravnoLice("Pravno lice"),
    Preduzetnik("Preduzetnik"),
    FizickoLice("Fizicko lice"),
    Ustanova("Ustanova");

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    UgostiteljType(String key) {
        this.key = key;
    }

    String showKey() {
        return key;
    }
}
