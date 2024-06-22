package repository;


import com.querydsl.jpa.impl.JPAQuery;
import entities.PrivilegovaniKorisnik;
import entities.QPrivilegovaniKorisnik;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PrivilegedUserDomainHelper extends DomainHelper {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QPrivilegovaniKorisnik qPrivilegedUser = QPrivilegovaniKorisnik.privilegovaniKorisnik;

    public PrivilegovaniKorisnik getByEmail(String email) {
        JPAQuery<PrivilegovaniKorisnik> query = new JPAQuery<>(entityManager).select(qPrivilegedUser)
                .from(qPrivilegedUser)
                .where(qPrivilegedUser.email.eq(email));
        return query.fetchOne();
    }

    public PrivilegovaniKorisnik getByUsername(String username) {
        JPAQuery<PrivilegovaniKorisnik> query = new JPAQuery<>(entityManager).select(qPrivilegedUser)
                .from(qPrivilegedUser)
                .where(qPrivilegedUser.username.eq(username));
        return query.fetchOne();
    }

    public PrivilegovaniKorisnik getByUsernamePassword(String username, String password) {
        JPAQuery<PrivilegovaniKorisnik> query = new JPAQuery<>(entityManager).select(qPrivilegedUser)
                .from(qPrivilegedUser)
                .where(qPrivilegedUser.username.eq(username).and(qPrivilegedUser.password.eq(password)));
        return query.fetchOne();
    }

    public void update(PrivilegovaniKorisnik privilegovaniKorisnik) {
        entityManager.persist(privilegovaniKorisnik);
    }

    public void create(PrivilegovaniKorisnik privilegovaniKorisnik) {
        entityManager.persist(privilegovaniKorisnik);
    }
}
