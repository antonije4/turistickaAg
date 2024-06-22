package beans.overview;


import beans.general.MessageController;
import beans.general.NavigationController;
import beans.general.UserController;
import entities.Rezervacija;
import entities.Turista;
import entities.UgostiteljskiObjekat;
import entities.Korisnik;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
@Getter @Setter
public class ReservationOverviewController extends BaseOverview {

    private Rezervacija rezervacija;
    private List<Date> dateRange = new ArrayList<>();
    private boolean inputEnabled;
    @Inject
    private ReservationDomainHelper reservationDomainHelper;
    @Inject
    private TouristDomainHelper touristDomainHelper;
    @Inject
    private UgostiteljskiObjekatDomainHelper ugostiteljskiObjekatDomainHelper;
    @Inject
    private NavigationController navigationController;
    @Inject
    private MessageController messageController;
    @Inject
    private UserController userController;
    @Inject
    private Util util;
    private long reservationId;


    public void init() {
        super.init();
        dateRange.add(localDateToDate(rezervacija.getPocetniDatum()));
        dateRange.add(localDateToDate(rezervacija.getKrajnjiDatum()));
    }

    private Date localDateToDate(LocalDate localDate) {
        return Date.from(
                localDate.atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
        );
    }

    public boolean canEdit() {
        return rezervacija.getPocetniDatum().isAfter(LocalDate.now()) && (isUgostiteljskiObjekatOwnerLoggedIn() || isTouristOwnerLoggedIn());
    }

    public boolean isUgostiteljskiObjekatOwnerLoggedIn() {
        if (!userController.loggedIn()) {
            return false;
        }
        Korisnik korisnik = userController.getLoggedInUser();
        return korisnik.getId() == rezervacija.getUgostiteljskiObjekat().getUgostitelj().getId() && userController.ugostiteljLoggedIn();
    }

    public boolean isTouristOwnerLoggedIn() {
        if (!userController.loggedIn()) {
            return false;
        }
        Korisnik korisnik = userController.getLoggedInUser();
        return korisnik.getId() == rezervacija.getTurista().getId() && userController.touristLoggedIn();
    }

    public void enableInput() {
        inputEnabled = true;
    }

    public void saveChanges() {
        rezervacija.setPocetniDatum(dateToLocalDate(dateRange.get(0)));
        rezervacija.setKrajnjiDatum(dateToLocalDate(dateRange.get(1)));
        reservationDomainHelper.updateReservation(rezervacija);
        inputEnabled = false;
        messageController.showInfoMessage(MessageType.MediumLiveMessage, "Successfully updated reservation!");
    }

    public void delete() {
        unlinkFromTourist();
        unlinkFromUgostiteljskiObjekat();
        reservationDomainHelper.deleteReservation(rezervacija);
        navigationController.navigateToUgostiteljskiObjekatOverview(rezervacija.getUgostiteljskiObjekat().getId());
    }

    private void unlinkFromTourist() {
        Turista turista = rezervacija.getTurista();
        turista.unlinkReservation(rezervacija);
        touristDomainHelper.update(turista);
    }

    private void unlinkFromUgostiteljskiObjekat() {
        UgostiteljskiObjekat ugostiteljskiObjekat = rezervacija.getUgostiteljskiObjekat();
        ugostiteljskiObjekat.unlinkReservation(rezervacija);
        ugostiteljskiObjekatDomainHelper.updateUgostiteljskiObjekat(ugostiteljskiObjekat);
    }

    public boolean canEditBoravisnaTaksa() {
        return inputEnabled && userController.ugostiteljLoggedIn();
    }

    protected boolean processParams() {
        try {
            reservationId = Long.parseLong(util.getRequestParam("reservationId"));
            return true;
        } catch (Exception e) {
            //log exception
            return false;
        }
    }

    protected boolean processDomain() {
        try {
            rezervacija = reservationDomainHelper.getReservationById(reservationId);
            return true;
        } catch (Exception e) {
            //log exception
            return false;
        }
    }

    private LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
