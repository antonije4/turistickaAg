package beans;

import dto.ClientDTO;
import dto.UserDTO;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class NavigationController {

    private static final String HOME = "/index.xhtml";
    private static final String REGISTRATION = "/registration.xhtml";
    private static final String LOGIN= "/login.xhtml";
    private static final String UGOSTITELJ_OVERVIEW = "/overview/ugostiteljOverview.xhtml";
    private static final String CLIENT_OVERVIEW = "/overview/clientOverview.xhtml";

    @Inject
    private UserController userController;

    public String goHome() {
        return createRedirectLink(HOME);
    }

    public void navigateToHome() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + goHome());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToLogin() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + goToLogin());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String goToRegistration() {
        return createRedirectLink(REGISTRATION);
    }

    public String goToLogin() {
        return createRedirectLink(LOGIN);
    }

    public String goToUserOverview( ) {
        UserDTO userDTO = userController.getLoggedInUser();
        if (userDTO instanceof ClientDTO) {
            return createRedirectLink(CLIENT_OVERVIEW);
        } else {
            return createRedirectLink(UGOSTITELJ_OVERVIEW);
        }
    }

    private String createRedirectLink(String page) {
        return String.format("%s?faces-redirect=true", page);
    }
}
