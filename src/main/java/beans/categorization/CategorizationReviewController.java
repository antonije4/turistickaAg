package beans.categorization;

import entities.CategorizationRequest;
import lombok.Getter;
import lombok.Setter;
import repository.CategorizationRequestDomainHelper;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Getter @Setter
public class CategorizationReviewController implements Serializable {

    private List<CategorizationRequest> categorizationRequestList;

    @Inject
    private CategorizationRequestDomainHelper categorizationRequestDomainHelper;

    public void init() {
        categorizationRequestDomainHelper.
    }
}
