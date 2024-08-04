package beans.general;

import entities.*;
import entities.mappers.UgostiteljMapper;
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
import java.util.*;
import java.util.stream.Collectors;

@Named
@ViewScoped
@Slf4j
public class RegistrationController implements Serializable {

    @Getter
    @Setter
    private Map<UserType, String> allUserTypes = Arrays.stream(UserType.values()).collect(Collectors.toMap(ut -> ut, UserType::getKey));

    @Getter
    @Setter
    private Map<UgostiteljType, String> allUgostiteljTypes = Arrays.stream(UgostiteljType.values()).collect(Collectors.toMap(ugostiteljType -> ugostiteljType, UgostiteljType::getKey));

    @Getter
    @Setter
    private UserType userType = UserType.Ugostitelj;

    @Getter
    @Setter
    private Turista turista = new Turista();

    @Getter
    @Setter
    private Ugostitelj ugostitelj = new Ugostitelj();

    @Getter
    @Setter
    private FizickoLice fizickoLice = new FizickoLice();

    @Getter
    @Setter
    private PravnoLice pravnoLice = new PravnoLice();

    @Getter
    @Setter
    private Preduzetnik preduzetnik = new Preduzetnik();

    @Getter
    @Setter
    private Ustanova ustanova = new Ustanova();

    @Getter
    @Setter
    private PrivilegovaniKorisnik privilegovaniKorisnik = new PrivilegovaniKorisnik();

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
        allUserTypes = allUserTypes.entrySet().stream()
                .filter(userTypeStringEntry -> !userTypeStringEntry.getKey().equals(UserType.PrivilegedUser))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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

    public boolean renderPreduzetnikData() {
        return renderUgostiteljData() && UgostiteljType.Preduzetnik.equals(ugostitelj.getTipUgostitelja());
    }

    public boolean renderUstanovaData() {
        return renderUgostiteljData() && UgostiteljType.Ustanova.equals(ugostitelj.getTipUgostitelja());
    }

    public boolean renderFizickoLiceData() {
        return renderUgostiteljData() && UgostiteljType.FizickoLice.equals(ugostitelj.getTipUgostitelja());
    }

    public boolean renderPravnoLiceData() {
        return renderUgostiteljData() && UgostiteljType.PravnoLice.equals(ugostitelj.getTipUgostitelja());
    }

    public void registerUgostitelj() {
        if (checkPreconditionsUgostitelj()) {
            setUgostiteljType();
            ugostiteljDomainHelper.createUgostitelj(ugostitelj);
            Sanduce sanduce = new Sanduce();
            sanduce.setUgostitelj(ugostitelj);
            ugostitelj.setSanduce(sanduce);
            messageDomainHelper.createInbox(sanduce);
            ugostiteljDomainHelper.updateUgostitelj(ugostitelj);
            navigationController.navigateToLogin();
        }
    }

    public void registerTourist() {
        if (checkPreconditionsTourist()) {
            touristDomainHelper.create(turista);
            navigationController.navigateToLogin();
        }
    }

    public void registerPrivileged() {
        if (checkPreconditionsPrivileged()) {
            privilegedUserDomainHelper.create(privilegovaniKorisnik);
            navigationController.navigateToLogin();
        }
    }

    private boolean checkPreconditionsTourist() {
        if (touristDomainHelper.getByEmail(turista.getEmail()) != null) {
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Turista sa email-om "+ privilegovaniKorisnik.getEmail() +" vec postoji.");
            return false;
        }
        if (touristDomainHelper.getByUsername(turista.getUsername()) != null) {
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Turista sa username-om "+ privilegovaniKorisnik.getUsername() +" vec postoji.");
            return false;
        }
        return true;
    }

    private boolean checkPreconditionsUgostitelj() {
        if (ugostiteljDomainHelper.getUgostiteljByEmail(ugostitelj.getEmail()) != null) {
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Ugostitelj sa email-om "+ ugostitelj.getEmail() +" vec postoji.");
            return false;
        }
        if (ugostiteljDomainHelper.getUgostiteljByUsername(ugostitelj.getUsername()) != null) {
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Ugostitelj sa username-om "+ ugostitelj.getUsername() +" vec postoji.");
            return false;
        }
        return true;
    }

    private boolean checkPreconditionsPrivileged() {
        if (privilegedUserDomainHelper.getByEmail(privilegovaniKorisnik.getEmail()) != null) {
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Privilegovani korisnik sa email-om "+ privilegovaniKorisnik.getEmail() +" vec postoji.");
            return false;
        }
        if (privilegedUserDomainHelper.getByUsername(privilegovaniKorisnik.getUsername()) != null) {
            messageController.showErrorMessage(MessageType.ShortLiveMessage, "Privilegovani korisnik sa username-om "+ privilegovaniKorisnik.getUsername() +" vec postoji.");
            return false;
        }
        return true;
    }

    private void setUgostiteljType() {
        switch (ugostitelj.getTipUgostitelja()) {
            case Ustanova:
                ugostitelj = UgostiteljMapper.INSTANCE.mergeUstanova(ustanova, ugostitelj);
                break;
            case PravnoLice:
                ugostitelj = UgostiteljMapper.INSTANCE.mergePravnoLice(pravnoLice, ugostitelj);
                break;
            case FizickoLice:
                ugostitelj = UgostiteljMapper.INSTANCE.mergeFizicikoLice(fizickoLice, ugostitelj);
                break;
            case Preduzetnik:
                ugostitelj = UgostiteljMapper.INSTANCE.mergePreduzetnik(preduzetnik, ugostitelj);
                break;
        }
    }
}
