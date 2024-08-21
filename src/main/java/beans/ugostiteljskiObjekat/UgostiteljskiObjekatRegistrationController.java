package beans.ugostiteljskiObjekat;

import beans.general.MessageController;
import beans.general.NavigationController;
import beans.general.UserController;
import entities.Ugostitelj;
import entities.UgostiteljskiObjekat;
import enums.MessageType;
import enums.UgostiteljskiObjekatTip;
import lombok.Getter;
import lombok.Setter;
import repository.UgostiteljDomainHelper;
import repository.UgostiteljskiObjekatDomainHelper;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class UgostiteljskiObjekatRegistrationController implements Serializable {

    @Inject
    private UgostiteljskiObjekatDomainHelper ugostiteljskiObjekatDomainHelper;
    @Inject
    private UserController userController;
    @Inject
    private NavigationController navigationController;
    @Inject
    private MessageController messageController;
    @Inject
    private UgostiteljDomainHelper ugostiteljDomainHelper;
    @Getter @Setter
    private Map<UgostiteljskiObjekatTip, String> allUgostiteljskiObjekatTypes = Arrays.stream(UgostiteljskiObjekatTip.values()).collect(Collectors.toMap(tip -> tip, UgostiteljskiObjekatTip::getKey));


    @Getter @Setter
    private Ugostitelj owner;
    @Getter @Setter
    private String ownerUsername;
    @Getter @Setter
    private UgostiteljskiObjekat ugostiteljskiObjekat;

    public void init() {
        if (userController.ugostiteljLoggedIn()) {
            ownerUsername = userController.getLoggedInUser().getUsername();
            fetchUgostitelj();
        }
    }

    public void registerUgostiteljskiObjekat() {
        ugostiteljskiObjekatDomainHelper.createUgostiteljskiObjekat(ugostiteljskiObjekat);
        ugostiteljDomainHelper.updateUgostitelj(owner);
        navigationController.navigateToUgostiteljOverview(owner.getUsername());
    }

    public void fetchUgostitelj() {
        owner = ugostiteljDomainHelper.getUgostiteljByUsername(ownerUsername);
        if (owner == null) {
            messageController.showErrorMessage(MessageType.MediumLiveMessage, "Ugostitelj sa korisnickim imenom "+ownerUsername+" ne postoji!");
            return;
        }
        ugostiteljskiObjekat = new UgostiteljskiObjekat();
        ugostiteljskiObjekat.setUgostitelj(owner);
        owner.linkUgostiteljskiObjekat(ugostiteljskiObjekat);
    }
}
