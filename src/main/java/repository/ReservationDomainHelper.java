package repository;

import com.querydsl.jpa.impl.JPAQuery;
import entities.QRezervacija;
import entities.Rezervacija;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ReservationDomainHelper extends DomainHelper {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QRezervacija qRezervacija = QRezervacija.rezervacija;

    public void createReservation(Rezervacija rezervacija) {
        entityManager.persist(rezervacija);
    }

    public void updateReservation(Rezervacija rezervacija) {
        entityManager.merge(rezervacija);
    }

    public Rezervacija getReservationById(long reservationId) {
        JPAQuery<Rezervacija> query = new JPAQuery<>(entityManager).select(qRezervacija)
                .from(qRezervacija)
                .where(qRezervacija.id.eq(reservationId));
        return query.fetchOne();
    }

    public void deleteReservation(Rezervacija rezervacija) {
        entityManager.remove(rezervacija);
    }
}
