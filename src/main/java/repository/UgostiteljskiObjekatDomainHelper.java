package repository;

import com.querydsl.jpa.impl.JPAQuery;
import entities.QUgostiteljskiObjekat;
import entities.UgostiteljskiObjekat;
import repository.data.ResultList;
import repository.search.UgostiteljskiObjekatSearchParams;
import util.JPAHelper;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UgostiteljskiObjekatDomainHelper extends DomainHelper{

    @PersistenceContext
    private EntityManager entityManager;

    private static final QUgostiteljskiObjekat qUgostiteljskiObjekat = QUgostiteljskiObjekat.ugostiteljskiObjekat;

    public ResultList<UgostiteljskiObjekat> search(UgostiteljskiObjekatSearchParams ugostiteljskiObjekatSearchParams) {
        if (ugostiteljskiObjekatSearchParams == null) {
            return ResultList.create(new ArrayList<>(), 0);
        }

        JPAQuery<UgostiteljskiObjekat> query = new JPAQuery<>(entityManager).select(qUgostiteljskiObjekat)
                .from(qUgostiteljskiObjekat);

        addFilter(query, ugostiteljskiObjekatSearchParams.getName(), qUgostiteljskiObjekat.name::eq);
        addFilter(query, ugostiteljskiObjekatSearchParams.getUgostiteljFirstName(), qUgostiteljskiObjekat.ugostitelj.firstName::eq);
        addFilter(query, ugostiteljskiObjekatSearchParams.getUgostiteljLastName(), qUgostiteljskiObjekat.ugostitelj.lastName::eq);

        List<UgostiteljskiObjekat> resultList = query.fetch();
        return ResultList.create(resultList, resultList.size());
    }

    public void updateUgostiteljskiObjekat(UgostiteljskiObjekat ugostiteljskiObjekat) {
        entityManager.merge(ugostiteljskiObjekat);
    }

    public UgostiteljskiObjekat getUgostiteljskiObjekatById(long id) {
        Query query = entityManager.createQuery("select u from UgostiteljskiObjekat u where u.id=:id");
        query.setParameter("id", id);
        return JPAHelper.getUniqueResult(UgostiteljskiObjekat.class, query);
    }

    public void createUgostiteljskiObjekat(UgostiteljskiObjekat ugostiteljskiObjekat) {
        entityManager.persist(ugostiteljskiObjekat);
    }
}
