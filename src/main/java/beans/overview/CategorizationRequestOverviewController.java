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

@Named
@ViewScoped
@Getter @Setter
public class CategorizationRequestOverviewController extends BaseOverview {

    private ZahtevZaKategorizaciju zahtevZaKategorizaciju;
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
        return zahtevZaKategorizaciju.getDatumZahteva().isAfter(LocalDate.now()) && isUgostiteljskiObjekatOwnerLoggedIn() && !zahtevZaKategorizaciju.isPregledan();
    }

    public boolean isUgostiteljskiObjekatOwnerLoggedIn() {
        if (!userController.loggedIn()) {
            return false;
        }
        Korisnik korisnik = userController.getLoggedInUser();
        return korisnik.getId() == zahtevZaKategorizaciju.getUgostiteljskiObjekat().getUgostitelj().getId() && userController.ugostiteljLoggedIn();
    }

    public void enableInput() {
        inputEnabled = true;
    }

    public void saveChanges() {
        zahtevZaKategorizaciju.setPodaciKategorizacije(zahtevZaKategorizaciju.getPodaciKategorizacije());
        categorizationRequestDomainHelper.updateCategorizationRequest(zahtevZaKategorizaciju);
        inputEnabled = false;
        messageController.showInfoMessage(MessageType.MediumLiveMessage, "Successfully updated categorization request!");
    }

    public void delete() {
        unlinkFromUgostitelj();
        unlinkFromUgostiteljskiObjekat();
        categorizationRequestDomainHelper.deleteCategorizationRequest(zahtevZaKategorizaciju);
        navigationController.navigateToUgostiteljskiObjekatOverview(zahtevZaKategorizaciju.getUgostiteljskiObjekat().getId());
    }

    private void unlinkFromUgostitelj() {
        Ugostitelj ugostitelj = zahtevZaKategorizaciju.getUgostitelj();
        ugostitelj.unlinkCategorizationRequest(zahtevZaKategorizaciju);
        ugostiteljDomainHelper.updateUgostitelj(ugostitelj);
    }

    private void unlinkFromUgostiteljskiObjekat() {
        UgostiteljskiObjekat ugostiteljskiObjekat = zahtevZaKategorizaciju.getUgostiteljskiObjekat();
        ugostiteljskiObjekat.unlinkCategorizationRequest(zahtevZaKategorizaciju);
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
            zahtevZaKategorizaciju = categorizationRequestDomainHelper.getCategorizationRequestById(categorizationRequestId);
            return true;
        } catch (Exception e) {
            //log exception
            return false;
        }
    }
}
