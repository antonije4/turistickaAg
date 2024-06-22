package beans.categorization;

import beans.general.MessageController;
import beans.general.UserController;
import entities.ZahtevZaKategorizaciju;
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

    private ResultList<ZahtevZaKategorizaciju> categorizationRequests;

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

    public void reviewRequest(ZahtevZaKategorizaciju zahtevZaKategorizaciju, boolean approved) {
        processReviewedRequest(approved, zahtevZaKategorizaciju);
        processUgostiteljskiObjekat(approved, zahtevZaKategorizaciju);
    }

    private void processReviewedRequest(boolean approved, ZahtevZaKategorizaciju zahtevZaKategorizaciju) {
        zahtevZaKategorizaciju.setPregledan(true);
        zahtevZaKategorizaciju.setOdobren(approved);
        zahtevZaKategorizaciju.setKorisnikPregleda(userController.getLoggedInUser().getUsername());
        List<ZahtevZaKategorizaciju> zahtevZaKategorizacijuList = categorizationRequests.getList();
        zahtevZaKategorizacijuList.remove(zahtevZaKategorizaciju);
        categorizationRequestDomainHelper.updateCategorizationRequest(zahtevZaKategorizaciju);
    }

    private void processUgostiteljskiObjekat(boolean approved, ZahtevZaKategorizaciju zahtevZaKategorizaciju) {
        UgostiteljskiObjekat ugostiteljskiObjekat = zahtevZaKategorizaciju.getUgostiteljskiObjekat();
        ugostiteljskiObjekat.setCategorized(approved);
        ugostiteljskiObjekat.setNotifiedOfCategorizationExpiry(false);
        if (approved) {
            ugostiteljskiObjekat.setCategorizationExpiryDate(LocalDate.now().plusYears(2));
        }

        ugostiteljskiObjekatDomainHelper.updateUgostiteljskiObjekat(ugostiteljskiObjekat);
        messageController.showInfoMessage(MessageType.ShortLiveMessage, "Successfully reviewed categorization request for ugostiteljski objekat "+ ugostiteljskiObjekat.getName());
    }
}
