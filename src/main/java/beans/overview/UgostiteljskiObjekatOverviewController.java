package beans.overview;

import beans.general.UserController;
import entities.Ugostitelj;
import entities.UgostiteljskiObjekat;
import lombok.Getter;
import lombok.Setter;
import repository.UgostiteljskiObjekatDomainHelper;
import util.Util;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class UgostiteljskiObjekatOverviewController extends BaseOverview {

    @Getter @Setter
    private UgostiteljskiObjekat ugostiteljskiObjekat;
    @Getter @Setter
    private Ugostitelj owner;
    @Inject
    private UgostiteljskiObjekatDomainHelper ugostiteljskiObjekatDomainHelper;
    @Inject
    private UserController userController;
    @Inject
    private Util util;
    private boolean inputDisabled;
    private long ugostiteljskiObjekatId;


    public void init() {
        super.init();
        owner = ugostiteljskiObjekat.getUgostitelj();
    }

    @Override
    protected boolean processParams() {
        try {
            ugostiteljskiObjekatId = Long.parseLong(util.getRequestParam("ugostiteljskiObjekatId"));
            return true;
        } catch (Exception e) {
            //log exception
            return false;
        }
    }

    @Override
    protected boolean processDomain() {
        try {
            ugostiteljskiObjekat = ugostiteljskiObjekatDomainHelper.getUgostiteljskiObjekatById(ugostiteljskiObjekatId);
            return true;
        } catch (Exception e) {
            //log exception
            return false;
        }
    }

    public void saveChanges() {
        ugostiteljskiObjekatDomainHelper.updateUgostiteljskiObjekat(ugostiteljskiObjekat);
        inputDisabled = true;
    }

    public boolean inputDisabled() {
       return inputDisabled;
    }

    public boolean canEdit() {
        if (ugostiteljskiObjekat == null) {
            return false;
        }
        return userController.getLoggedInUser().getId() == ugostiteljskiObjekat.getUgostitelj().getId();
    }

    public void enableInput() {
        inputDisabled = false;
    }
}
