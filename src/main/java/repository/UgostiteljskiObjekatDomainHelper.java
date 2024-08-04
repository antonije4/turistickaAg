package repository;

import com.querydsl.core.util.StringUtils;
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
import java.time.LocalDate;
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

        if (!StringUtils.isNullOrEmpty(ugostiteljskiObjekatSearchParams.getNaziv())) {
            addFilter(query, ugostiteljskiObjekatSearchParams.getNaziv(), qUgostiteljskiObjekat.naziv::like);
        }
        if (!StringUtils.isNullOrEmpty(ugostiteljskiObjekatSearchParams.getUgostiteljUsername())) {
            addFilter(query, ugostiteljskiObjekatSearchParams.getUgostiteljUsername(), qUgostiteljskiObjekat.ugostitelj.username::likeIgnoreCase);
        }
        addFilter(query, ugostiteljskiObjekatSearchParams.getKategorizovan(), qUgostiteljskiObjekat.kategorizovan::eq);

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

    public List<UgostiteljskiObjekat> getUgostiteljskiObjekatCategorizationExpiryDateLessThan(LocalDate date) {
        JPAQuery<UgostiteljskiObjekat> query = new JPAQuery<>(entityManager).select(qUgostiteljskiObjekat)
                .from(qUgostiteljskiObjekat)
                .where(qUgostiteljskiObjekat.istekKategorizacije.before(date).and(qUgostiteljskiObjekat.notifikovanOIstekuKategorizacije.eq(false)));
        return query.fetch();
    }
}
