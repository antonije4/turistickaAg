package dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategorizationRequestRow {
    private long id;
    private String name;
    private boolean categorized;
    private boolean selected;
}
