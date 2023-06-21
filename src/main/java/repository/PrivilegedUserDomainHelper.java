package repository;


import com.querydsl.jpa.impl.JPAQuery;
import entities.PrivilegedUser;
import entities.QPrivilegedUser;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PrivilegedUserDomainHelper extends DomainHelper {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QPrivilegedUser qPrivilegedUser = QPrivilegedUser.privilegedUser;

    public PrivilegedUser getByEmail(String email) {
        JPAQuery<PrivilegedUser> query = new JPAQuery<>(entityManager).select(qPrivilegedUser)
                .from(qPrivilegedUser)
                .where(qPrivilegedUser.email.eq(email));
        return query.fetchOne();
    }

    public PrivilegedUser getByUsername(String username) {
        JPAQuery<PrivilegedUser> query = new JPAQuery<>(entityManager).select(qPrivilegedUser)
                .from(qPrivilegedUser)
                .where(qPrivilegedUser.username.eq(username));
        return query.fetchOne();
    }

    public PrivilegedUser getByUsernamePassword(String username, String password) {
        JPAQuery<PrivilegedUser> query = new JPAQuery<>(entityManager).select(qPrivilegedUser)
                .from(qPrivilegedUser)
                .where(qPrivilegedUser.username.eq(username).and(qPrivilegedUser.password.eq(password)));
        return query.fetchOne();
    }

    public void update(PrivilegedUser privilegedUser) {
        entityManager.persist(privilegedUser);
    }

    public void create(PrivilegedUser privilegedUser) {
        entityManager.persist(privilegedUser);
    }
}
