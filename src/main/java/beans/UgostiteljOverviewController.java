package beans;

import dto.UgostiteljDTO;
import entities.Client;
import entities.Ugostitelj;
import entities.User;
import entities.mappers.UgostiteljMapper;
import repository.ClientRepository;
import repository.UgostiteljRepository;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class UgostiteljOverviewController implements Serializable {
    private UgostiteljDTO ugostitelj;
    private boolean inputDisabled;

    @Inject
    private UserController userController;

    @Inject
    private UgostiteljRepository ugostiteljRepository;

    public void init() {
        ugostitelj = (UgostiteljDTO) userController.getLoggedInUser();
        inputDisabled = true;
    }

    public UgostiteljDTO getUgostitelj(){
        return ugostitelj;
    }

    public void saveChanges() {
        ugostiteljRepository.updateUgostitelj(UgostiteljMapper.INSTANCE.mapToModel(ugostitelj));
        inputDisabled=true;
    }

    public boolean inputDisabled() {
        return inputDisabled;
    }

    public void enableInput() {
        inputDisabled = false;
    }

}
