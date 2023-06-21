package beans.overview;


import beans.general.MessageController;
import beans.general.NavigationController;
import beans.general.UserController;
import entities.*;
import enums.MessageType;
import lombok.Getter;
import lombok.Setter;
import repository.*;
import util.Util;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Named
@ViewScoped
@Getter @Setter
public class CategorizationRequestOverviewController extends BaseOverview {

    private CategorizationRequest categorizationRequest;
    private boolean inputEnabled;
    @Inject
    private CategorizationRequestDomainHelper categorizationRequestDomainHelper;
    @Inject
    private UgostiteljskiObjekatDomainHelper ugostiteljskiObjekatDomainHelper;
    @Inject
    private UgostiteljDomainHelper ugostiteljDomainHelper;
    @Inject
    private NavigationController navigationController;
    @Inject
    private MessageController messageController;
    @Inject
    private UserController userController;
    @Inject
    private Util util;

    private long categorizationRequestId;


    public void init() {
        super.init();
    }

    public boolean canEdit() {
        return categorizationRequest.getDateOfRequest().isAfter(LocalDate.now()) && isUgostiteljskiObjekatOwnerLoggedIn() && !categorizationRequest.isReviewed();
    }

    public boolean isUgostiteljskiObjekatOwnerLoggedIn() {
        if (!userController.loggedIn()) {
            return false;
        }
        User user = userController.getLoggedInUser();
        return user.getId() == categorizationRequest.getUgostiteljskiObjekat().getUgostitelj().getId() && userController.ugostiteljLoggedIn();
    }

    public void enableInput() {
        inputEnabled = true;
    }

    public void saveChanges() {
        categorizationRequest.setCategorizationInfo(categorizationRequest.getCategorizationInfo());
        categorizationRequestDomainHelper.updateCategorizationRequest(categorizationRequest);
        inputEnabled = false;
        messageController.showInfoMessage(MessageType.MediumLiveMessage, "Successfully updated categorization request!");
    }

    public void delete() {
        unlinkFromUgostitelj();
        unlinkFromUgostiteljskiObjekat();
        categorizationRequestDomainHelper.deleteCategorizationRequest(categorizationRequest);
        navigationController.navigateToUgostiteljskiObjekatOverview(categorizationRequest.getUgostiteljskiObjekat().getId());
    }

    private void unlinkFromUgostitelj() {
        Ugostitelj ugostitelj = categorizationRequest.getUgostitelj();
        ugostitelj.unlinkCategorizationRequest(categorizationRequest);
        ugostiteljDomainHelper.updateUgostitelj(ugostitelj);
    }

    private void unlinkFromUgostiteljskiObjekat() {
        UgostiteljskiObjekat ugostiteljskiObjekat = categorizationRequest.getUgostiteljskiObjekat();
        ugostiteljskiObjekat.unlinkCategorizationRequest(categorizationRequest);
        ugostiteljskiObjekatDomainHelper.updateUgostiteljskiObjekat(ugostiteljskiObjekat);
    }

    public boolean canEditBoravisnaTaksa() {
        return inputEnabled && userController.ugostiteljLoggedIn();
    }

    protected boolean processParams() {
        try {
            categorizationRequestId = Long.parseLong(util.getRequestParam("categorizationRequestId"));
            return true;
        } catch (Exception e) {
            //log exception
            return false;
        }
    }

    protected boolean processDomain() {
        try {
            categorizationRequest = categorizationRequestDomainHelper.getCategorizationRequestById(categorizationRequestId);
            return true;
        } catch (Exception e) {
            //log exception
            return false;
        }
    }
}
