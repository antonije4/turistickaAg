package beans;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class NavigationController {

    private static final String HOME = "/index.xhtml";
    private static final String REGISTRATION = "/registration.xhtml";
    private static final String LOGIN= "/login.xhtml";

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

    private String createRedirectLink(String page) {
        return String.format("%s?faces-redirect=true", page);
    }
}
