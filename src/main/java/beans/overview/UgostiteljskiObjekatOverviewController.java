package beans.overview;

import beans.general.NavigationController;
import beans.general.UserController;
import entities.CategorizationRequest;
import entities.Reservation;
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

    @Inject
    private NavigationController navigationController;

    private boolean inputEnabled;
    private long ugostiteljskiObjekatId;


    public void init() {
        super.init();
        owner = ugostiteljskiObjekat.getUgostitelj();
        inputEnabled = false;
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
        inputEnabled = false;
    }

    public boolean inputEnabled() {
       return inputEnabled && canEdit();
    }

    public boolean canEdit() {
        if (userController.loggedIn()) {
            return userController.getLoggedInUser().getId() == ugostiteljskiObjekat.getUgostitelj().getId();
        }
        else {
            return false;
        }
    }

    public void enableInput() {
        inputEnabled = true;
    }

    public void editReservation(Reservation reservation) {
        navigationController.navigateToReservationOverview(reservation.getId());
    }

    public void editCategorizationRequest(CategorizationRequest categorizationRequest) {
        navigationController.navigateTo(reservation.getId());
    }
}
