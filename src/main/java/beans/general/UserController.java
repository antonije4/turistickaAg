package beans.general;


import entities.Korisnik;
import entities.PrivilegovaniKorisnik;
import entities.Turista;
import entities.Ugostitelj;
import repository.MessageDomainHelper;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UserController implements Serializable {

    private Korisnik korisnik;

    @Inject
    private NavigationController navigationController;
    @Inject
    private MessageDomainHelper messageDomainHelper;

    public boolean loggedIn() {
        return korisnik != null;
    }

    public boolean ugostiteljLoggedIn() {
        return korisnik instanceof Ugostitelj;
    }

    public boolean privilegedUserLoggedIn() {
        return korisnik instanceof PrivilegovaniKorisnik;
    }
    public boolean touristLoggedIn() {return korisnik instanceof Turista;}

    public boolean hasUnreadMessages() {
        if (ugostiteljLoggedIn()) {
            Ugostitelj ugostitelj = (Ugostitelj) korisnik;
            return messageDomainHelper.inboxHasUnreadMessages(ugostitelj.getSanduce().getId());
        } else
            return false;
    }

    public void logIn(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public void logOut() {
        korisnik = null;
        navigationController.navigateToHome();
    }

    public String username() {
        return korisnik.getUsername();
    }

    public Korisnik getLoggedInUser() {
        return korisnik;
    }

}
