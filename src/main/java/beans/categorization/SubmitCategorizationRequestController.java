package beans.categorization;


import beans.general.NavigationController;
import beans.general.UserController;
import dto.CategorizationRequestRow;
import entities.ZahtevZaKategorizaciju;
import entities.Ugostitelj;
import entities.UgostiteljskiObjekat;
import entities.mappers.UgostiteljskiObjekatMapper;
import enums.UgostiteljskiObjekatTip;
import lombok.Getter;
import lombok.Setter;
import repository.CategorizationRequestDomainHelper;
import repository.UgostiteljDomainHelper;
import repository.UgostiteljskiObjekatDomainHelper;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@ViewScoped
@Getter @Setter
public class SubmitCategorizationRequestController implements Serializable {

    private ZahtevZaKategorizaciju zahtevZaKategorizaciju;
    private UgostiteljskiObjekat newUgostiteljskiObjekat;
    private Ugostitelj ugostitelj;
    private boolean existingUgostiteljskiObjekat;
    private int selectedUgostiteljskiObjekatIndex;
    private List<CategorizationRequestRow> categorizationRequestRowList;
    private CategorizationRequestRow selectedCategorizationRow;
    private String selectedUgostiteljskiObjekat;
    private boolean inputEnabled;
    @Getter @Setter
    private Map<UgostiteljskiObjekatTip, String> allUgostiteljskiObjekatTypes = Arrays.stream(UgostiteljskiObjekatTip.values()).collect(Collectors.toMap(tip -> tip, UgostiteljskiObjekatTip::getKey));

    @Inject
    private UserController userController;
    @Inject
    private NavigationController navigationController;
    @Inject
    private UgostiteljskiObjekatDomainHelper ugostiteljskiObjekatDomainHelper;
    @Inject
    private CategorizationRequestDomainHelper categorizationRequestDomainHelper;
    @Inject
    private UgostiteljDomainHelper ugostiteljDomainHelper;

    public void init() {
        zahtevZaKategorizaciju = new ZahtevZaKategorizaciju();
        String ugostiteljUsername = userController.getLoggedInUser().getUsername();
        ugostitelj = ugostiteljDomainHelper.getUgostiteljByUsername(ugostiteljUsername);
        categorizationRequestRowList = UgostiteljskiObjekatMapper.INSTANCE.ugostiteljskiObjekatListToCategorizationRow(ugostitelj.getUgostiteljskiObjekti());
        selectedUgostiteljskiObjekatIndex = -1;
        inputEnabled = true;
        newUgostiteljskiObjekat = new UgostiteljskiObjekat();
    }

    public void selectUgostiteljskiObjekat(CategorizationRequestRow row) {
        int selectedIndex = categorizationRequestRowList.indexOf(row);
        if (!row.isSelected()) {
            selectedCategorizationRow = null;
            selectedUgostiteljskiObjekat = null;
            selectedUgostiteljskiObjekatIndex = -1;
        } else {
            if (selectedUgostiteljskiObjekatIndex >= 0) {
                categorizationRequestRowList.get(selectedUgostiteljskiObjekatIndex).setSelected(false);
            }
            selectedUgostiteljskiObjekatIndex = selectedIndex;
            selectedCategorizationRow = row;
            selectedUgostiteljskiObjekat = row.getNaziv();
        }
    }

    public void changeSelectedUgostiteljskiObjekat() {
        if (existingUgostiteljskiObjekat) {
            if (selectedUgostiteljskiObjekatIndex >= 0) {
                selectedUgostiteljskiObjekat = categorizationRequestRowList.get(selectedUgostiteljskiObjekatIndex).getNaziv();
            } else {
                selectedUgostiteljskiObjekat = null;
            }
        } else {
            if (newUgostiteljskiObjekat != null) {
                selectedUgostiteljskiObjekat = newUgostiteljskiObjekat.getNaziv();
            } else {
                selectedUgostiteljskiObjekat = null;
            }
        }
    }

    public void enableInput() {
        inputEnabled = true;
    }

    public void saveNewUgostiteljskiObjekat() {
        selectedUgostiteljskiObjekat = newUgostiteljskiObjekat.getNaziv();
        inputEnabled = false;
    }

    public void submitRequest() {
        //checkPreconditions
        if (existingUgostiteljskiObjekat) {
            UgostiteljskiObjekat ugostiteljskiObjekat = ugostiteljskiObjekatDomainHelper.getUgostiteljskiObjekatById(categorizationRequestRowList.get(selectedUgostiteljskiObjekatIndex).getId());
            zahtevZaKategorizaciju.setUgostiteljskiObjekat(ugostiteljskiObjekat);
        } else {
            newUgostiteljskiObjekat.setUgostitelj(ugostitelj);
            ugostiteljskiObjekatDomainHelper.createUgostiteljskiObjekat(newUgostiteljskiObjekat);
            zahtevZaKategorizaciju.setUgostiteljskiObjekat(newUgostiteljskiObjekat);
        }
        fillCategorizationRequest();
        categorizationRequestDomainHelper.saveCategorizationRequest(zahtevZaKategorizaciju);
        navigationController.navigateToCategorizationRequestOverview(zahtevZaKategorizaciju.getId());
    }

    public void fillCategorizationRequest() {
        zahtevZaKategorizaciju.setUgostitelj(ugostitelj);
        zahtevZaKategorizaciju.setDatumZahteva(LocalDate.now());
        zahtevZaKategorizaciju.setOdobren(false);
        zahtevZaKategorizaciju.setPregledan(false);
    }
}
