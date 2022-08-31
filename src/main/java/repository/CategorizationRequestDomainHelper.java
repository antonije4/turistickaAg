package repository;

import com.querydsl.jpa.impl.JPAQuery;
import entities.CategorizationRequest;
import entities.QCategorizationRequest;
import repository.data.ResultList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CategorizationRequestDomainHelper extends DomainHelper {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QCategorizationRequest qCategorizationRequest = QCategorizationRequest.categorizationRequest;

    public void saveCategorizationRequest(CategorizationRequest categorizationRequest) {
        entityManager.persist(categorizationRequest);
    }

    public void updateCategorizationRequest(CategorizationRequest request) {
        entityManager.merge(request);
    }

    public ResultList<CategorizationRequest> getUnReviewedRequests() {
        JPAQuery<CategorizationRequest> query = new JPAQuery<>(entityManager).select(qCategorizationRequest)
                .from(qCategorizationRequest)
                .where(qCategorizationRequest.reviewed.eq(false));

        List<CategorizationRequest> resultList = query.fetch();
        return ResultList.create(resultList, resultList.size());
    }
}
