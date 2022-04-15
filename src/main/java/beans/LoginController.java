package beans;
import entities.Client;
import enums.MessageType;
import lombok.Getter;
import lombok.Setter;
import repository.ClientRepository;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;


@Named
@ViewScoped
public class LoginController implements Serializable {

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private NavigationController navigationController;

    @Inject
    private UserController userController;

    @Inject
    private MessageController messageController;

    public void init() {

    }

    public void login(){
        Client client = clientRepository.getClientByUsernamePassword(username, password);
        if (client != null) {
            userController.logIn(client);
            navigationController.navigateToHome();
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Jek jek");
        } else {
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Username password combination doesn't exist");
        }
    }
}
