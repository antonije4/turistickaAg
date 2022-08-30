package repository;

import com.querydsl.jpa.impl.JPAQuery;
import entities.QUgostitelj;
import entities.Ugostitelj;
import repository.data.ResultList;
import repository.search.UgostiteljSearchParams;
import util.JPAHelper;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UgostiteljDomainHelper extends DomainHelper {
    @PersistenceContext
    private EntityManager entityManager;

    private static final QUgostitelj qUgostitelj = QUgostitelj.ugostitelj;

    public ResultList<Ugostitelj> search(UgostiteljSearchParams ugostiteljSearchParams) {
        if (ugostiteljSearchParams == null) {
            return ResultList.create(new ArrayList<>(), 0);
        }

        JPAQuery<Ugostitelj> query = new JPAQuery<>(entityManager).select(qUgostitelj)
                .from(qUgostitelj);

        addFilter(query, ugostiteljSearchParams.getFirstName(), qUgostitelj.firstName::eq);
        addFilter(query, ugostiteljSearchParams.getLastName(), qUgostitelj.lastName::eq);
        addFilter(query, ugostiteljSearchParams.getUgostiteljTypes(), qUgostitelj.ugostiteljType::in);
        List<Ugostitelj> resultList = query.fetch();
        return ResultList.create(resultList, resultList.size());
    }

    public void updateUgostitelj(Ugostitelj ugostitelj) {
        entityManager.merge(ugostitelj);
    }

    public Ugostitelj getUgostiteljByEmail(String email) {
        Query query = entityManager.createQuery("select u from Ugostitelj u where u.email=:email");
        query.setParameter("email", email);
        return JPAHelper.getUniqueResult(Ugostitelj.class, query);
    }

    public Ugostitelj getUgostiteljByUsername(String username) {
        Query query = entityManager.createQuery("select u from Ugostitelj u where u.username=:username");
        query.setParameter("username", username);
        return JPAHelper.getUniqueResult(Ugostitelj.class, query);
    }

    public Ugostitelj getUgostiteljByUsernamePassword(String username, String password) {
        Query query = entityManager.createQuery("select u from Ugostitelj u where u.username=:username and u.password=:password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        return JPAHelper.getUniqueResult(Ugostitelj.class, query);
    }

    public void createUgostitelj(Ugostitelj ugostitelj) {
        entityManager.persist(ugostitelj);
    }
}
