package beans.overview;

import beans.general.NavigationController;
import entities.Reservation;
import entities.Tourist;
import lombok.Getter;
import lombok.Setter;
import repository.TouristDomainHelper;
import util.Util;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
@Getter @Setter
public class TouristOverviewController extends BaseOverview {

    private String touristUsername;
    private Tourist tourist;
    private boolean inputDisabled;

    @Inject
    private Util util;
    @Inject
    private TouristDomainHelper touristDomainHelper;
    @Inject
    private NavigationController navigationController;

    public void init() {
        super.init();
    }

    public boolean inputDisabled() {
        return inputDisabled;
    }

    public void enableInput() {
        inputDisabled = false;
    }

    public void editReservation(Reservation reservation) {
        navigationController.navigateToReservationOverview(reservation.getId());
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
            tourist = touristDomainHelper.getByUsername(touristUsername);
            return true;
        } catch (Exception e) {
            //log exception
            return false;
        }
    }
}
