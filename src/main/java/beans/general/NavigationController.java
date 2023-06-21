package beans.general;

import entities.Tourist;
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
    private static final String RESERVATION = "/reservation.xhtml";


    private static final String UGOSTITELJ_OVERVIEW = "/overview/testOverview.xhtml";
    private static final String UGOSTITELJSKI_OBJEKAT_OVERVIEW = "/overview/ugostiteljskiObjekatOverview.xhtml";
    private static final String CLIENT_OVERVIEW = "/overview/clientOverview.xhtml";
    private static final String RESERVATION_OVERVIEW = "/overview/reservationOverview.xhtml";
    private static final String TOURIST_OVERVIEW = "/overview/touristOverview.xhtml";


    private static final String UGOSTITELJ_SEARCH = "/search/ugostiteljSearch.xhtml";
    private static final String UGOSTITELJSKI_OBJEKAT_SEARCH = "/search/ugostiteljskiObjekatSearch.xhtml";

    private static final String SUBMIT_CATEGORIZATION_REQUEST = "/categorization/submitCategorizationRequest.xhtml";
    private static final String CATEGORIZATION_REVIEW = "/categorization/categorizationReview.xhtml";
    private static final String INBOX = "/inbox/inbox.xhtml";

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
            externalContext.redirect(externalContext.getRequestContextPath() + goToUgostiteljOverview(username));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToUgostiteljskiObjekatOverview(long id) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + goToUgostiteljskiObjekatOverview(""+id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToReservationOverview(long id) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + goToReservationOverview(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToReservation(long id) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + goToReservation(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToTouristOverview(String username) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + goToTouristOverview(username));
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
    public String goToUgostiteljskiObjekatSearch() {
        return createRedirectLink(UGOSTITELJSKI_OBJEKAT_SEARCH);
    }
    public String goToReservation(long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("ugostiteljskiObjekatId", id);
        return createRedirectLinkWithParams(RESERVATION, params);
    }

    public String goToReservationOverview(long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("reservationId", id);
        return createRedirectLinkWithParams(RESERVATION_OVERVIEW, params);
    }

    public String goToLogin() {
        return createRedirectLink(LOGIN);
    }
    public String goToSubmitCategorizationRequest() {
        return createRedirectLink(SUBMIT_CATEGORIZATION_REQUEST);
    }

    public String goToCategorizationReview() {
        return createRedirectLink(CATEGORIZATION_REVIEW);
    }
    public String goToInbox() {
        return createRedirectLink(INBOX);
    }


    public String goToUserOverview(String username) {
        User user = userController.getLoggedInUser();
        if (user instanceof Tourist) {
            return goToTouristOverview(username);
        } else {
            return goToUgostiteljOverview(username);
        }
    }

    public String goToUgostiteljOverview(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("ugostiteljUsername", username);
        return createRedirectLinkWithParams(UGOSTITELJ_OVERVIEW, params);
    }

    public String goToTouristOverview(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("touristUsername", username);
        return createRedirectLinkWithParams(TOURIST_OVERVIEW, params);
    }

    public String goToUgostiteljskiObjekatOverview(String id) {
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
