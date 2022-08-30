package beans.general;


import beans.general.NavigationController;
import dto.UserDTO;
import entities.User;

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

    public boolean loggedIn() {
        return user != null;
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
