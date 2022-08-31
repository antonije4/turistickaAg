package beans.inbox;

import beans.general.UserController;
import entities.Inbox;
import entities.Message;
import entities.Ugostitelj;
import lombok.Getter;
import lombok.Setter;
import repository.MessageDomainHelper;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Getter @Setter
public class InboxController implements Serializable {
    private List<Message> messageList;
    private Ugostitelj ugostitelj;
    private Inbox inbox;
    @Inject
    private UserController userController;
    @Inject
    private MessageDomainHelper messageDomainHelper;

    public void init() {
        ugostitelj = (Ugostitelj) userController.getLoggedInUser();
        inbox = ugostitelj.getInbox();
        messageList = messageDomainHelper.fetchMessagesFromInbox(inbox.getId());
    }

    public void readMessage(Message message) {
        int index = messageList.indexOf(message);
        messageList.get(index).setRead(true);
        messageDomainHelper.updateMessage(messageList.get(index));
    }


}
