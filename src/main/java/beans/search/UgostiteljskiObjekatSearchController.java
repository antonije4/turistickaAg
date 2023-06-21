package beans.search;

import entities.UgostiteljskiObjekat;
import lombok.Getter;
import lombok.Setter;
import repository.UgostiteljskiObjekatDomainHelper;
import repository.data.ResultList;
import repository.search.SearchParams;
import repository.search.UgostiteljskiObjekatSearchParams;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
@Getter @Setter
public class UgostiteljskiObjekatSearchController implements SearchController, Serializable {

    @Inject
    private UgostiteljskiObjekatDomainHelper ugostiteljskiObjekatDomainHelper;

    private UgostiteljskiObjekatSearchParams searchParams = new UgostiteljskiObjekatSearchParams();

    private ResultList<UgostiteljskiObjekat> results = new ResultList<>();

    public void init() {
        searchParams.reset();
        results.clear();
    }

    @Override
    public void reset() {
        searchParams.reset();
        results.clear();
    }

    @Override
    public void search() {
        results = ugostiteljskiObjekatDomainHelper.search(searchParams);
    }

    @Override
    public ResultList<?> getResults() {
        return results;
    }
}
