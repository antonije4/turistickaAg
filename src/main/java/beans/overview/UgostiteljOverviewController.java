package beans.overview;

import beans.general.UserController;
import dto.UgostiteljDTO;
import entities.mappers.UgostiteljMapper;
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
    private UgostiteljDTO ugostitelj;
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
            ugostitelj = UgostiteljMapper.INSTANCE.mapToDTO(ugostiteljDomainHelper.getUgostiteljByUsername(ugostiteljUsername));
            return true;
        } catch (Exception e) {
            //log exception
            return false;
        }
    }


    public void saveChanges() {
        ugostiteljDomainHelper.updateUgostitelj(UgostiteljMapper.INSTANCE.mapToModel(ugostitelj));
        inputDisabled=true;
    }

    public boolean inputDisabled() {
        return inputDisabled;
    }

    public void enableInput() {
        inputDisabled = false;
    }

}
