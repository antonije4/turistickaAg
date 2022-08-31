package beans.categorization;

import beans.general.MessageController;
import beans.general.UserController;
import entities.CategorizationRequest;
import entities.UgostiteljskiObjekat;
import enums.MessageType;
import lombok.Getter;
import lombok.Setter;
import repository.CategorizationRequestDomainHelper;
import repository.UgostiteljskiObjekatDomainHelper;
import repository.data.ResultList;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Named
@ViewScoped
@Getter @Setter
public class CategorizationReviewController implements Serializable {

    private ResultList<CategorizationRequest> categorizationRequests;

    @Inject
    private CategorizationRequestDomainHelper categorizationRequestDomainHelper;
    @Inject
    private UgostiteljskiObjekatDomainHelper ugostiteljskiObjekatDomainHelper;
    @Inject
    private UserController userController;
    @Inject
    private MessageController messageController;

    public void init() {
        categorizationRequests = categorizationRequestDomainHelper.getUnReviewedRequests();
    }

    public void reviewRequest(CategorizationRequest categorizationRequest, boolean approved) {
        processReviewedRequest(approved, categorizationRequest);
        processUgostiteljskiObjekat(approved, categorizationRequest);
    }

    private void processReviewedRequest(boolean approved, CategorizationRequest categorizationRequest) {
        categorizationRequest.setReviewed(true);
        categorizationRequest.setApproved(approved);
        categorizationRequest.setUserReviewed(userController.getLoggedInUser().getUsername());
        List<CategorizationRequest> categorizationRequestList = categorizationRequests.getList();
        categorizationRequestList.remove(categorizationRequest);
        categorizationRequestDomainHelper.updateCategorizationRequest(categorizationRequest);
    }

    private void processUgostiteljskiObjekat(boolean approved, CategorizationRequest categorizationRequest) {
        UgostiteljskiObjekat ugostiteljskiObjekat = categorizationRequest.getUgostiteljskiObjekat();
        ugostiteljskiObjekat.setCategorized(approved);
        ugostiteljskiObjekat.setNotifiedOfCategorizationExpiry(false);
        if (approved) {
            ugostiteljskiObjekat.setCategorizationExpiryDate(LocalDate.now().plusYears(2));
        }

        ugostiteljskiObjekatDomainHelper.updateUgostiteljskiObjekat(ugostiteljskiObjekat);
        messageController.showInfoMessage(MessageType.ShortLiveMessage, "Successfully reviewed categorization request for ugostiteljski objekat "+ ugostiteljskiObjekat.getName());
    }
}
