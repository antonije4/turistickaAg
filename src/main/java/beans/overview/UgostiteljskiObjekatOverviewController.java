package beans.overview;

import beans.general.NavigationController;
import beans.general.UserController;
import entities.Rezervacija;
import entities.ZahtevZaKategorizaciju;
import entities.Ugostitelj;
import entities.UgostiteljskiObjekat;
import enums.UgostiteljskiObjekatTip;
import lombok.Getter;
import lombok.Setter;
import repository.UgostiteljskiObjekatDomainHelper;
import util.Util;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class UgostiteljskiObjekatOverviewController extends BaseOverview {

    @Getter @Setter
    private UgostiteljskiObjekat ugostiteljskiObjekat;
    @Getter @Setter
    private Ugostitelj owner;
    @Getter @Setter
    private List<ZahtevZaKategorizaciju> zahtevZaKategorizacijuList;
    @Getter @Setter
    private List<Rezervacija> rezervacijaList;
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

    @Getter @Setter
    private Map<UgostiteljskiObjekatTip, String> allUgostiteljskiObjekatTypes = Arrays.stream(UgostiteljskiObjekatTip.values()).collect(Collectors.toMap(tip -> tip, UgostiteljskiObjekatTip::getKey));


    public void init() {
        super.init();
        owner = ugostiteljskiObjekat.getUgostitelj();
        inputEnabled = false;
        zahtevZaKategorizacijuList = new ArrayList<>(ugostiteljskiObjekat.getZahteviZaKategorizaciju());
        rezervacijaList = new ArrayList<>(ugostiteljskiObjekat.getRezervacije());
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

    public void editReservation(Rezervacija rezervacija) {
        navigationController.navigateToReservationOverview(rezervacija.getId());
    }

    public void editCategorizationRequest(ZahtevZaKategorizaciju zahtevZaKategorizaciju) {
        navigationController.navigateToCategorizationRequestOverview(zahtevZaKategorizaciju.getId());
    }
}
