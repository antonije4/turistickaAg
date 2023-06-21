package beans.general;
import entities.PrivilegedUser;
import entities.Tourist;
import entities.Ugostitelj;
import enums.MessageType;
import enums.UserType;
import lombok.Getter;
import lombok.Setter;
import repository.PrivilegedUserDomainHelper;
import repository.TouristDomainHelper;
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
    private TouristDomainHelper touristDomainHelper;

    @Inject
    private UgostiteljDomainHelper ugostiteljDomainHelper;

    @Inject
    private PrivilegedUserDomainHelper privilegedUserDomainHelper;

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
            logInTourist();
        } else
            if (userType.equals(UserType.Ugostitelj)){
            logInUgostitelj();
        } else {
                logInPrivileged();
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

    private void logInTourist() {
        Tourist tourist = touristDomainHelper.getByUsernamePassword(username, password);
        if (tourist != null) {
            userController.logIn(tourist);
            navigationController.navigateToHome();
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Jek jek");
        } else {
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Username password combination doesn't exist");
        }
    }

    private void logInPrivileged() {
        PrivilegedUser privilegedUser = privilegedUserDomainHelper.getByUsernamePassword(username, password);
        if (privilegedUser != null) {
            userController.logIn(privilegedUser);
            navigationController.navigateToHome();
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Jek jek");
        } else {
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Username password combination doesn't exist");
        }
    }
}
