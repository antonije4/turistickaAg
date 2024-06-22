package beans.overview;

import beans.general.UserController;
import entities.*;
import entities.mappers.UgostiteljMapper;
import enums.UgostiteljType;
import lombok.Getter;
import lombok.Setter;
import repository.UgostiteljDomainHelper;
import util.Util;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class UgostiteljOverviewController extends BaseOverview{

    private static final String UGOSTITELJ_USERNAME_PARAM = "ugostiteljUsername";
    @Getter @Setter
    private Ugostitelj ugostitelj;
    @Getter @Setter
    private FizickoLice fizickoLice;
    @Getter @Setter
    private PravnoLice pravnoLice;
    @Getter @Setter
    private Preduzetnik preduzetnik;
    @Getter @Setter
    private Ustanova ustanova;
    private boolean inputDisabled;
    private String ugostiteljUsername;

    @Inject
    private UserController userController;

    @Inject
    private UgostiteljDomainHelper ugostiteljDomainHelper;

    @Inject
    private Util util;

    public void init() {
        super.init();
        inputDisabled = true;
        switch (ugostitelj.getTipUgostitelja()) {
            case PravnoLice:
                pravnoLice = (PravnoLice) ugostiteljDomainHelper.getUgostiteljByUsername(ugostitelj.getUsername());
                break;
            case Ustanova:
                ustanova = (Ustanova) ugostiteljDomainHelper.getUgostiteljByUsername(ugostitelj.getUsername());
                break;
            case FizickoLice:
                fizickoLice = (FizickoLice) ugostiteljDomainHelper.getUgostiteljByUsername(ugostitelj.getUsername());
                break;
            case Preduzetnik:
                preduzetnik = (Preduzetnik) ugostiteljDomainHelper.getUgostiteljByUsername(ugostitelj.getUsername());
                break;
        }
    }

    @Override
    protected boolean processParams() {
        try {
            ugostiteljUsername = util.getRequestParam(UGOSTITELJ_USERNAME_PARAM);
            return true;
        } catch (Exception e) {
            //log exception
            return false;
        }
    }

    @Override
    protected boolean processDomain() {
        try {
            ugostitelj = ugostiteljDomainHelper.getUgostiteljByUsername(ugostiteljUsername);
            return true;
        } catch (Exception e) {
            //log exception
            return false;
        }
    }


    public void saveChanges() {
        setUgostiteljType();
        ugostiteljDomainHelper.updateUgostitelj(ugostitelj);
        inputDisabled=true;
    }

    public boolean inputDisabled() {
        return inputDisabled || !currentUgostiteljLoggedIn();
    }

    public void enableInput() {
        inputDisabled = false;
    }

    public boolean currentUgostiteljLoggedIn() {
        if (userController.loggedIn()) {
            return userController.getLoggedInUser().getId() == ugostitelj.getId();
        } else {
            return false;
        }
    }

    public boolean renderPreduzetnikData() {
        return UgostiteljType.Preduzetnik.equals(ugostitelj.getTipUgostitelja());
    }

    public boolean renderUstanovaData() {
        return UgostiteljType.Ustanova.equals(ugostitelj.getTipUgostitelja());
    }

    public boolean renderFizickoLiceData() {
        return UgostiteljType.FizickoLice.equals(ugostitelj.getTipUgostitelja());
    }

    public boolean renderPravnoLiceData() {
        return UgostiteljType.PravnoLice.equals(ugostitelj.getTipUgostitelja());
    }

    private void setUgostiteljType() {
        switch (ugostitelj.getTipUgostitelja()) {
            case Ustanova:
                ugostitelj = UgostiteljMapper.INSTANCE.mergeUstanova(ustanova, ugostitelj);
                break;
            case PravnoLice:
                ugostitelj = UgostiteljMapper.INSTANCE.mergePravnoLice(pravnoLice, ugostitelj);
                break;
            case FizickoLice:
                ugostitelj = UgostiteljMapper.INSTANCE.mergeFizicikoLice(fizickoLice, ugostitelj);
                break;
            case Preduzetnik:
                ugostitelj = UgostiteljMapper.INSTANCE.mergePreduzetnik(preduzetnik, ugostitelj);
                break;
        }
    }
}
