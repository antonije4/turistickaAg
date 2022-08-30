package beans.general;

import dto.ClientDTO;
import dto.UserDTO;
import entities.Client;
import entities.User;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Named
@RequestScoped
public class NavigationController {

    private static final String HOME = "/index.xhtml";
    private static final String REGISTRATION = "/registration.xhtml";
    private static final String LOGIN= "/login.xhtml";
    private static final String UGOSTITELJSKI_OBJEKAT_REGISTRATION = "/ugostiteljskiObjekatRegistration.xhtml";

    private static final String UGOSTITELJ_OVERVIEW = "/overview/ugostiteljOverview.xhtml";
    private static final String UGOSTITELJSKI_OBJEKAT_OVERVIEW = "/overview/ugostiteljskiObjekatOverview.xhtml";
    private static final String CLIENT_OVERVIEW = "/overview/clientOverview.xhtml";

    private static final String UGOSTITELJ_SEARCH = "/search/ugostiteljSearch.xhtml";

    private static final String SUBMIT_CATEGORIZATION_REQUEST = "/categorization/submitCategorizationRequest.xhtml";

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

    public void navigateToUgostiteljOverview(String username) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + goToUserOverview(username));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToUgostiteljskiObjekatOverview(long id) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + goToUgostiteljskiObjekatOverview(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String goToRegistration() {
        return createRedirectLink(REGISTRATION);
    }

    public String goToUgostiteljSearch() {
        return createRedirectLink(UGOSTITELJ_SEARCH);
    }

    public String goToLogin() {
        return createRedirectLink(LOGIN);
    }
    public String goToSubmitCategorizationRequest() {
        return createRedirectLink(SUBMIT_CATEGORIZATION_REQUEST);
    }

    public String goToUserOverview( ) {
        User user = userController.getLoggedInUser();
        if (user instanceof Client) {
            return createRedirectLink(CLIENT_OVERVIEW);
        } else {
            return createRedirectLink(UGOSTITELJ_OVERVIEW);
        }
    }

    public String goToUserOverview(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("ugostiteljUsername", username);
        return createRedirectLinkWithParams(UGOSTITELJ_OVERVIEW, params);
    }

    public String goToUgostiteljskiObjekatOverview(long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("ugostiteljskiObjekatId", id);
        return createRedirectLinkWithParams(UGOSTITELJSKI_OBJEKAT_OVERVIEW, params);
    }

    public String goToUgostiteljskiObjekatRegistration() {
        return createRedirectLink(UGOSTITELJSKI_OBJEKAT_REGISTRATION);
    }

    private String createRedirectLink(String page) {
        return String.format("%s?faces-redirect=true", page);
    }

    private String createRedirectLinkWithParams(String page, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder(createRedirectLink(page));
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> sb.append(String.format("&%s=%s", k, v)));
        }

        return sb.toString();
    }
}
