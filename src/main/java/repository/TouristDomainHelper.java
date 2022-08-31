package repository;


import com.querydsl.jpa.impl.JPAQuery;
import entities.QTourist;
import entities.Tourist;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TouristDomainHelper extends DomainHelper {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QTourist qTourist = QTourist.tourist;

    public Tourist getByEmail(String email) {
        JPAQuery<Tourist> query = new JPAQuery<>(entityManager).select(qTourist)
                .from(qTourist)
                .where(qTourist.email.eq(email));
        return query.fetchOne();
    }

    public Tourist getByUsername(String username) {
        JPAQuery<Tourist> query = new JPAQuery<>(entityManager).select(qTourist)
                .from(qTourist)
                .where(qTourist.username.eq(username));
        return query.fetchOne();
    }

    public Tourist getByUsernamePassword(String username, String password) {
        JPAQuery<Tourist> query = new JPAQuery<>(entityManager).select(qTourist)
                .from(qTourist)
                .where(qTourist.username.eq(username).and(qTourist.password.eq(password)));
        return query.fetchOne();
    }

    public void update(Tourist tourist) {
        entityManager.persist(tourist);
    }

    public void create(Tourist tourist) {
        entityManager.persist(tourist);
    }
}
