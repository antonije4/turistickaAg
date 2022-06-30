package beans;

import dto.UgostiteljDTO;
import entities.Client;
import entities.Ugostitelj;
import entities.mappers.UgostiteljMapper;
import enums.MessageType;
import enums.UgostiteljType;
import enums.UserType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import repository.ClientRepository;
import repository.UgostiteljRepository;

import javax.annotation.PostConstruct;
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
    private Client client = new Client();

    @Getter
    @Setter
    private UgostiteljDTO ugostitelj = new UgostiteljDTO();

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private UgostiteljRepository ugostiteljRepository;

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
        ugostiteljRepository.createUgostitelj(UgostiteljMapper.INSTANCE.mapToModel(ugostitelj));
        navigationController.navigateToLogin();
    }

    public void registerClient() {
        if (!checkPreconditions()) {
            return;
        }
        clientRepository.createClient(client);
//        log.info("User {} successfully registered.", client.getUsername());
        navigationController.navigateToLogin();
    }

    private boolean checkPreconditions() {
        if (clientRepository.getClientByEmail(client.getEmail()) != null) {
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "User with email "+client.getEmail() +" already exists.");
//            log.error("User with email {} already exists.", client.getEmail());
            return false;
        }
        return true;
    }
}
