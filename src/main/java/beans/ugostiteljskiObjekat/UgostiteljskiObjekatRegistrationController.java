package beans.ugostiteljskiObjekat;

import beans.general.NavigationController;
import beans.general.UserController;
import dto.UgostiteljDTO;
import dto.UgostiteljskiObjekatDTO;
import entities.Ugostitelj;
import entities.UgostiteljskiObjekat;
import entities.mappers.UgostiteljMapper;
import entities.mappers.UgostiteljskiObjekatMapper;
import lombok.Getter;
import lombok.Setter;
import repository.UgostiteljDomainHelper;
import repository.UgostiteljskiObjekatDomainHelper;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

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
    private UgostiteljDomainHelper ugostiteljDomainHelper;
    @Getter @Setter
    private Ugostitelj owner;

    @Getter @Setter
    private UgostiteljskiObjekat ugostiteljskiObjekat;

    public void init() {
        String ownerUsername = userController.getLoggedInUser().getUsername();
        owner = ugostiteljDomainHelper.getUgostiteljByUsername(ownerUsername);
        ugostiteljskiObjekat = new UgostiteljskiObjekat();
        ugostiteljskiObjekat.setUgostitelj(owner);
        owner.linkUgostiteljskiObjekat(ugostiteljskiObjekat);
    }

    public void registerUgostiteljskiObjekat() {
        ugostiteljskiObjekatDomainHelper.createUgostiteljskiObjekat(ugostiteljskiObjekat);
        ugostiteljDomainHelper.updateUgostitelj(owner);
        navigationController.navigateToUgostiteljOverview(owner.getUsername());
    }
}
