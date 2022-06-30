package repository;

import com.querydsl.jpa.impl.JPAQuery;
import entities.QUgostiteljskiObjekat;
import entities.UgostiteljskiObjekat;
import repository.data.ResultList;
import repository.search.UgostiteljskiObjekatSearchParams;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

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
}
