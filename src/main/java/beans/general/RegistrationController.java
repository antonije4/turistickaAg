package beans.general;

import entities.PrivilegedUser;
import entities.Tourist;
import entities.Inbox;
import entities.Ugostitelj;
import enums.MessageType;
import enums.UgostiteljType;
import enums.UserType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import repository.PrivilegedUserDomainHelper;
import repository.TouristDomainHelper;
import repository.MessageDomainHelper;
import repository.UgostiteljDomainHelper;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

@Named
@ViewScoped
@Slf4j
public class RegistrationController implements Serializable {

    @Getter
    @Setter
    private Set<UserType> allUserTypes = new HashSet<>(EnumSet.allOf(UserType.class));;

    @Getter
    @Setter
    private Set<UgostiteljType> allUgostiteljTypes = new HashSet<>(Arrays.asList(UgostiteljType.values()));

    @Getter
    @Setter
    private UserType userType = UserType.Ugostitelj;

    @Getter
    @Setter
    private Tourist tourist = new Tourist();

    @Getter
    @Setter
    private Ugostitelj ugostitelj = new Ugostitelj();

    @Getter
    @Setter
    private PrivilegedUser privilegedUser = new PrivilegedUser();

    @Inject
    private TouristDomainHelper touristDomainHelper;

    @Inject
    private UgostiteljDomainHelper ugostiteljDomainHelper;

    @Inject
    private PrivilegedUserDomainHelper privilegedUserDomainHelper;

    @Inject
    private MessageDomainHelper messageDomainHelper;

    @Inject
    private MessageController messageController;

    @Inject
    private NavigationController navigationController;

    public void init() {

    }

    public boolean renderUgostiteljData() {
        return userType.equals(UserType.Ugostitelj);
    }

    public boolean renderTouristData() {
        return userType.equals(UserType.Turista);
    }

    public boolean renderPrivilegedUserData() {
        return userType.equals(UserType.PrivilegedUser);
    }

    public void registerUgostitelj() {
        if (checkPreconditionsUgostitelj()) {
            ugostiteljDomainHelper.createUgostitelj(ugostitelj);
            Inbox inbox = new Inbox();
            inbox.setUgostitelj(ugostitelj);
            ugostitelj.setInbox(inbox);
            messageDomainHelper.createInbox(inbox);
            ugostiteljDomainHelper.updateUgostitelj(ugostitelj);
            navigationController.navigateToLogin();
        }
    }

    public void registerTourist() {
        if (checkPreconditionsTourist()) {
            touristDomainHelper.create(tourist);
            navigationController.navigateToLogin();
        }
    }

    public void registerPrivileged() {
        if (checkPreconditionsPrivileged()) {
            privilegedUserDomainHelper.create(privilegedUser);
            navigationController.navigateToLogin();
        }
    }

    private boolean checkPreconditionsTourist() {
        if (touristDomainHelper.getByEmail(tourist.getEmail()) != null) {
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Tourist with email "+ privilegedUser.getEmail() +" already exists.");
            return false;
        }
        if (touristDomainHelper.getByUsername(tourist.getUsername()) != null) {
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Tourist with username "+ privilegedUser.getUsername() +" already exists.");
            return false;
        }
        return true;
    }

    private boolean checkPreconditionsUgostitelj() {
        if (ugostiteljDomainHelper.getUgostiteljByEmail(ugostitelj.getEmail()) != null) {
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Ugostitelj with email "+ ugostitelj.getEmail() +" already exists.");
            return false;
        }
        if (ugostiteljDomainHelper.getUgostiteljByUsername(ugostitelj.getUsername()) != null) {
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Ugostitelj with username "+ ugostitelj.getUsername() +" already exists.");
            return false;
        }
        return true;
    }

    private boolean checkPreconditionsPrivileged() {
        if (privilegedUserDomainHelper.getByEmail(privilegedUser.getEmail()) != null) {
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Privileged user with email "+ privilegedUser.getEmail() +" already exists.");
            return false;
        }
        if (privilegedUserDomainHelper.getByUsername(privilegedUser.getUsername()) != null) {
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Privileged user with username "+ privilegedUser.getUsername() +" already exists.");
            return false;
        }
        return true;
    }

}
