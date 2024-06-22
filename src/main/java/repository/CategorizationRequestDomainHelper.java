package repository;

import com.querydsl.jpa.impl.JPAQuery;
import entities.QZahtevZaKategorizaciju;
import entities.ZahtevZaKategorizaciju;
import repository.data.ResultList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CategorizationRequestDomainHelper extends DomainHelper {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QZahtevZaKategorizaciju qZahtevZaKategorizaciju = QZahtevZaKategorizaciju.zahtevZaKategorizaciju;

    public void saveCategorizationRequest(ZahtevZaKategorizaciju zahtevZaKategorizaciju) {
        entityManager.persist(zahtevZaKategorizaciju);
    }

    public void updateCategorizationRequest(ZahtevZaKategorizaciju request) {
        entityManager.merge(request);
    }

    public ResultList<ZahtevZaKategorizaciju> getUnReviewedRequests() {
        JPAQuery<ZahtevZaKategorizaciju> query = new JPAQuery<>(entityManager).select(qZahtevZaKategorizaciju)
                .from(qZahtevZaKategorizaciju)
                .where(qZahtevZaKategorizaciju.pregledan.eq(false));

        List<ZahtevZaKategorizaciju> resultList = query.fetch();
        return ResultList.create(resultList, resultList.size());
    }

    public ZahtevZaKategorizaciju getCategorizationRequestById(long id) {
        JPAQuery<ZahtevZaKategorizaciju> query = new JPAQuery<>(entityManager).select(qZahtevZaKategorizaciju)
                .from(qZahtevZaKategorizaciju)
                .where(qZahtevZaKategorizaciju.id.eq(id));
        return query.fetchOne();
    }

    public void deleteCategorizationRequest(ZahtevZaKategorizaciju zahtevZaKategorizaciju) {
        entityManager.remove(zahtevZaKategorizaciju);
    }
}
