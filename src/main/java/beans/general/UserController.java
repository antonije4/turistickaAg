package beans.general;


import entities.PrivilegedUser;
import entities.Tourist;
import entities.Ugostitelj;
import entities.User;
import repository.MessageDomainHelper;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UserController implements Serializable {

    private User user;

    @Inject
    private NavigationController navigationController;
    @Inject
    private MessageDomainHelper messageDomainHelper;

    public boolean loggedIn() {
        return user != null;
    }

    public boolean ugostiteljLoggedIn() {
        return user instanceof Ugostitelj;
    }

    public boolean privilegedUserLoggedIn() {
        return user instanceof PrivilegedUser;
    }
    public boolean touristLoggedIn() {return user instanceof Tourist;}

    public boolean hasUnreadMessages() {
        if (ugostiteljLoggedIn()) {
            Ugostitelj ugostitelj = (Ugostitelj) user;
            return messageDomainHelper.inboxHasUnreadMessages(ugostitelj.getInbox().getId());
        } else
            return false;
    }

    public void logIn(User user) {
        this.user = user;
    }

    public void logOut() {
        user = null;
        navigationController.navigateToHome();
    }

    public String username() {
        return user.getUsername();
    }

    public User getLoggedInUser() {
        return user;
    }

}
