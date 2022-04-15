package enums;

import lombok.Getter;

public enum MessageType {

    ShortLiveMessage("shortLiveMessages"),
    MediumLiveMessage("mediumLiveMessages"),
    LongLiveMessage("longLiveMessages");


    /**
     * id of message, messages or growl JSF component.
     */
    @Getter
    private String id;

    private MessageType(String id) {
        this.id = id;

    }
}
