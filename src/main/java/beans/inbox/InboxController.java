package beans.inbox;

import beans.general.UserController;
import entities.Poruka;
import entities.Sanduce;
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
    private List<Poruka> listaPoruka;
    private Ugostitelj ugostitelj;
    private Sanduce sanduce;
    @Inject
    private UserController userController;
    @Inject
    private MessageDomainHelper messageDomainHelper;

    public void init() {
        ugostitelj = (Ugostitelj) userController.getLoggedInUser();
        sanduce = ugostitelj.getSanduce();
        listaPoruka = messageDomainHelper.fetchMessagesFromInbox(sanduce.getId());
    }

    public void readMessage(Poruka poruka) {
        int index = listaPoruka.indexOf(poruka);
        listaPoruka.get(index).setProcitana(true);
        messageDomainHelper.updateMessage(listaPoruka.get(index));
    }


}
