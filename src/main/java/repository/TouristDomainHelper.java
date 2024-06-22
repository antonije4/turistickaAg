package repository;


import com.querydsl.jpa.impl.JPAQuery;
import entities.QTurista;
import entities.QUstanova;
import entities.Turista;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TouristDomainHelper extends DomainHelper {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QTurista qTourist = QTurista.turista;

    public Turista getByEmail(String email) {
        JPAQuery<Turista> query = new JPAQuery<>(entityManager).select(qTourist)
                .from(qTourist)
                .where(qTourist.email.eq(email));
        return query.fetchOne();
    }

    public Turista getByUsername(String username) {
        JPAQuery<Turista> query = new JPAQuery<>(entityManager).select(qTourist)
                .from(qTourist)
                .where(qTourist.username.eq(username));
        return query.fetchOne();
    }

    public Turista getByUsernamePassword(String username, String password) {
        JPAQuery<Turista> query = new JPAQuery<>(entityManager).select(qTourist)
                .from(qTourist)
                .where(qTourist.username.eq(username).and(qTourist.password.eq(password)));
        return query.fetchOne();
    }

    public void update(Turista turista) {
        entityManager.merge(turista);
    }

    public void create(Turista turista) {
        entityManager.persist(turista);
    }
}
