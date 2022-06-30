package beans;


import dto.UserDTO;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UserController implements Serializable {

    private UserDTO user;

    @Inject
    private NavigationController navigationController;

    public boolean loggedIn() {
        return user != null;
    }

    public void logIn(UserDTO user) {
        this.user = user;
    }

    public void logOut() {
        user = null;
        navigationController.navigateToHome();
    }

    public String username() {
        return user.getUsername();
    }

    public UserDTO getLoggedInUser() {
        return user;
    }

}
