package beans.overview;


import beans.general.MessageController;
import beans.general.UserController;
import entities.Reservation;
import enums.MessageType;
import lombok.Getter;
import lombok.Setter;
import repository.ReservationDomainHelper;
import util.Util;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
@Getter @Setter
public class ReservationOverviewController extends BaseOverview {

    private Reservation reservation;
    private List<Date> dateRange = new ArrayList<>();
    private boolean inputEnabled;
    @Inject
    private ReservationDomainHelper reservationDomainHelper;
    @Inject
    private MessageController messageController;
    @Inject
    private UserController userController;
    @Inject
    private Util util;
    private long reservationId;


    public void init() {
        super.init();
        dateRange.add(localDateToDate(reservation.getStartingDate()));
        dateRange.add(localDateToDate(reservation.getEndingDate()));
    }

    private Date localDateToDate(LocalDate localDate) {
        return Date.from(
                localDate.atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
        );
    }

    public boolean canEdit() {
        return reservation.getStartingDate().isAfter(LocalDate.now());
    }

    public void enableInput() {
        inputEnabled = true;
    }

    public void saveChanges() {
        reservation.setStartingDate(dateToLocalDate(dateRange.get(0)));
        reservation.setEndingDate(dateToLocalDate(dateRange.get(1)));
        reservationDomainHelper.updateReservation(reservation);
        inputEnabled = false;
        messageController.showInfoMessage(MessageType.MediumLiveMessage, "Successfully updated reservation!");
    }

    public boolean canEditBoravisnaTaksa() {
        return inputEnabled && userController.isUgostitelj();
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
            reservation = reservationDomainHelper.getReservationById(reservationId);
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
