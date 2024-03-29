package beans.reservation;

import beans.general.MessageController;
import beans.general.NavigationController;
import beans.general.UserController;
import entities.Reservation;
import entities.Tourist;
import entities.UgostiteljskiObjekat;
import enums.MessageType;
import lombok.Getter;
import lombok.Setter;
import repository.ReservationDomainHelper;
import repository.TouristDomainHelper;
import repository.UgostiteljskiObjekatDomainHelper;
import util.Util;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
@Getter @Setter
public class ReservationController implements Serializable {

    private List<Date> dateRange;
    private Tourist tourist;
    private String touristUsername;
    private int numberOfPeople;
    private UgostiteljskiObjekat ugostiteljskiObjekat;
    private String boravisnaTaksa;
    @Inject
    private ReservationDomainHelper reservationDomainHelper;
    @Inject
    private UgostiteljskiObjekatDomainHelper ugostiteljskiObjekatDomainHelper;
    @Inject
    private TouristDomainHelper touristDomainHelper;
    @Inject
    private UserController userController;
    @Inject
    private Util util;
    @Inject
    private MessageController messageController;

    @Inject
    private NavigationController navigationController;


    public void init() {
        if (processParams()) {
            //throw ex
        }
        if (userController.touristLoggedIn()) {
            tourist = (Tourist) userController.getLoggedInUser();
            touristUsername = tourist.getUsername();
        }
        numberOfPeople = 1;
    }

    public boolean touristLoggedIn() {
        return userController.getLoggedInUser() instanceof Tourist;
    }

    public void calculateBoravisnaTaksa() {
        int bor = 24000;
        boravisnaTaksa = bor+ "$";
    }

    public void reserve() {
        //checkPreconditions
        if (!touristLoggedIn()) {
            tourist = touristDomainHelper.getByUsername(touristUsername);
            if (tourist == null) {
                messageController.showErrorMessage(MessageType.MediumLiveMessage, "Tourist with username "+touristUsername+" doesn't exist!");
                return;
            }
        }
        Reservation reservation = new Reservation.Builder()
                .boravisnaTaksaPaid(false)
                .boravisnaTaksaPrice(boravisnaTaksa)
                .numberOfPeople(numberOfPeople)
                .tourist(tourist)
                .ugostiteljskiObjekat(ugostiteljskiObjekat)
                .startingDate(dateToLocalDate(dateRange.get(0)))
                .endingDate(dateToLocalDate(dateRange.get(1)))
                .build();

        reservationDomainHelper.createReservation(reservation);
        tourist.getReservationList().add(reservation);
        touristDomainHelper.update(tourist);
        navigationController.navigateToReservationOverview(reservation.getId());
        messageController.showInfoMessage(MessageType.MediumLiveMessage, "Successfully created reservation.");
    }

    private LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private boolean processParams() {
        try {
            long ugostiteljskiObjekatId = Long.parseLong(util.getRequestParam("ugostiteljskiObjekatId"));
            return processDomain(ugostiteljskiObjekatId);
        } catch (Exception e) {
            //log exception
            return false;
        }
    }

    private boolean processDomain(long ugostiteljskiObjekatId) {
        try {
            ugostiteljskiObjekat = ugostiteljskiObjekatDomainHelper.getUgostiteljskiObjekatById(ugostiteljskiObjekatId);
            return true;
        } catch (Exception e) {
            //log exception
            return false;
        }
    }
}
