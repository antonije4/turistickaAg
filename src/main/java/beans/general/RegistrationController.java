package beans.general;

import entities.Tourist;
import entities.Inbox;
import entities.Ugostitelj;
import enums.MessageType;
import enums.UgostiteljType;
import enums.UserType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
    private Set<UserType> allUserTypes;

    @Getter
    @Setter
    private Set<UgostiteljType> allUgostiteljTypes;

    @Getter
    @Setter
    private UserType userType;

    @Getter
    @Setter
    private Tourist tourist = new Tourist();

    @Getter
    @Setter
    private Ugostitelj ugostitelj = new Ugostitelj();

    @Inject
    private TouristDomainHelper touristDomainHelper;

    @Inject
    private UgostiteljDomainHelper ugostiteljDomainHelper;

    @Inject
    private MessageDomainHelper messageDomainHelper;

    @Inject
    private MessageController messageController;

    @Inject
    private NavigationController navigationController;

    public void init() {
        userType = UserType.Ugostitelj;
        allUserTypes = new HashSet<>(EnumSet.allOf(UserType.class));
        allUgostiteljTypes = new HashSet<>(Arrays.asList(UgostiteljType.values()));
    }

    public boolean renderUgostiteljData() {
        return userType.equals(UserType.Ugostitelj);
    }

    public void registerUgostitelj() {
        if (!checkPreconditions()) {
            return;
        }
        ugostiteljDomainHelper.createUgostitelj(ugostitelj);
        Inbox inbox = new Inbox();
        inbox.setUgostitelj(ugostitelj);
        ugostitelj.setInbox(inbox);
        messageDomainHelper.createInbox(inbox);
        ugostiteljDomainHelper.updateUgostitelj(ugostitelj);
        navigationController.navigateToLogin();
    }

    public void registerClient() {
        if (!checkPreconditions()) {
            return;
        }
        touristDomainHelper.create(tourist);
//        log.info("User {} successfully registered.", client.getUsername());
        navigationController.navigateToLogin();
    }

    private boolean checkPreconditions() {
        if (touristDomainHelper.getByEmail(tourist.getEmail()) != null) {
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "User with email "+ tourist.getEmail() +" already exists.");
//            log.error("User with email {} already exists.", client.getEmail());
            return false;
        }
        return true;
    }
}
