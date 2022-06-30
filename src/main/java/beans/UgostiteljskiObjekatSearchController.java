package beans;

import repository.data.ResultList;
import repository.search.SearchParams;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class UgostiteljskiObjekatSearchController implements SearchController {

    public void init() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void search() {

    }

    @Override
    public SearchParams getSearchParams() {
        return null;
    }

    @Override
    public ResultList<?> getResultList() {
        return null;
    }
}
