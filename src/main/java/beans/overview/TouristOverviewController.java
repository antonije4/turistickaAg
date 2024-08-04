package beans.overview;

import beans.general.NavigationController;
import beans.general.UserController;
import entities.Rezervacija;
import entities.Turista;
import lombok.Getter;
import lombok.Setter;
import repository.TouristDomainHelper;
import util.Util;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
@Getter @Setter
public class TouristOverviewController extends BaseOverview {

    private String touristUsername;
    private Turista turista;
    private boolean inputDisabled;

    @Inject
    private Util util;
    @Inject
    private TouristDomainHelper touristDomainHelper;
    @Inject
    private NavigationController navigationController;
    @Inject
    private UserController userController;

    public void init() {
        super.init();
        inputDisabled = true;
    }

    public boolean inputDisabled() {
        return inputDisabled;
    }

    public void enableInput() {
        inputDisabled = false;
    }

    public void saveChanges() {
        touristDomainHelper.update(turista);
        inputDisabled=true;
    }

    public void editReservation(Rezervacija rezervacija) {
        navigationController.navigateToReservationOverview(rezervacija.getId());
    }

    @Override
    protected boolean processParams() {
        try {
            touristUsername = util.getRequestParam("touristUsername");
            return true;
        } catch (Exception e) {
            //log exception
            return false;
        }
    }

    @Override
    protected boolean processDomain() {
        try {
            turista = touristDomainHelper.getByUsername(touristUsername);
            return true;
        } catch (Exception e) {
            //log exception
            return false;
        }
    }

    public boolean currentTouristLoggedIn() {
        if (userController.loggedIn()) {
            return turista.getId() == userController.getLoggedInUser().getId();
        } else {
            return false;
        }
    }
}
