package repository;

import com.querydsl.jpa.impl.JPAQuery;
import entities.QReservation;
import entities.Reservation;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ReservationDomainHelper extends DomainHelper {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QReservation qReservation = QReservation.reservation;

    public void createReservation(Reservation reservation) {
        entityManager.persist(reservation);
    }

    public void updateReservation(Reservation reservation) {
        entityManager.merge(reservation);
    }

    public Reservation getReservationById(long reservationId) {
        JPAQuery<Reservation> query = new JPAQuery<>(entityManager).select(qReservation)
                .from(qReservation)
                .where(qReservation.id.eq(reservationId));
        return query.fetchOne();
    }

    public void deleteReservation(Reservation reservation) {
        entityManager.remove(reservation);
    }
}
