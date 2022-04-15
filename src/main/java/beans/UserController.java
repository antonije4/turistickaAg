package beans;


import entities.Client;
import org.primefaces.event.CloseEvent;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UserController implements Serializable {

    private Client user;

    @Inject
    private NavigationController navigationController;

    public boolean loggedIn() {
        return user != null;
    }

    public void logIn(Client client) {
        user = client;
    }

    public void logOut() {
        user = null;
        navigationController.navigateToHome();
    }

    public String username() {
        return user.getUsername();
    }
}
