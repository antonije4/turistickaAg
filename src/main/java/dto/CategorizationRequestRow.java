package dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategorizationRequestRow {
    private long id;
    private String naziv;
    private boolean kategorizovan;
    private boolean selected;
}
