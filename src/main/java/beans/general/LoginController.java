package beans.general;
import entities.Client;
import entities.Ugostitelj;
import entities.mappers.ClientMapper;
import entities.mappers.UgostiteljMapper;
import enums.MessageType;
import enums.UserType;
import lombok.Getter;
import lombok.Setter;
import repository.ClientRepository;
import repository.UgostiteljDomainHelper;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;


@Named
@ViewScoped
public class LoginController implements Serializable {

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private UserType userType;

    @Getter
    private Set<UserType> userTypeSet;

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private UgostiteljDomainHelper ugostiteljDomainHelper;

    @Inject
    private NavigationController navigationController;

    @Inject
    private UserController userController;

    @Inject
    private MessageController messageController;

    public void init() {
        userType = UserType.Ugostitelj;
        userTypeSet = new HashSet<>(EnumSet.allOf(UserType.class));
    }

    public void login(){
        if (userType.equals(UserType.Turista)) {
            logInClient();
        } else {
            logInUgostitelj();
        }
    }

    public void logInUgostitelj() {
        Ugostitelj ugostitelj = ugostiteljDomainHelper.getUgostiteljByUsernamePassword(username, password);
        if (ugostitelj != null) {
            userController.logIn(ugostitelj);
            navigationController.navigateToHome();
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Jek jek");
        } else {
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Username password combination doesn't exist");
        }
    }

    private void logInClient() {
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
