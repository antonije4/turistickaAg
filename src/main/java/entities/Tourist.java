package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
public class Tourist extends User{
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private Date dateOfBirth;

    @OneToMany(mappedBy = "tourist", fetch = FetchType.EAGER)
    private List<Reservation> reservationList;

    public void unlinkReservation(Reservation reservation) {
        reservationList.removeIf(reservation1 -> reservation1.getId() == reservation.getId());
    }
}
