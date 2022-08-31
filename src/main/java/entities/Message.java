package entities;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Message implements Serializable {

    @Id
    @GeneratedValue
    @NotNull
    private long id;
    private boolean read;
    @ManyToOne
    private Inbox inbox;
    private String content;
    private LocalDateTime deliveryTime;

    public Message() {

    }

    @Builder
    public Message(boolean read, Inbox inbox, String content, LocalDateTime deliveryTime) {
        this.read = read;
        this.inbox = inbox;
        this.content = content;
        this.deliveryTime = deliveryTime;
    }
}
