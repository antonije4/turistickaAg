package beans.search;

import dto.UgostiteljDTO;
import entities.Ugostitelj;
import entities.mappers.UgostiteljMapper;
import repository.UgostiteljDomainHelper;
import repository.data.ResultList;
import repository.search.UgostiteljSearchParams;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class UgostiteljSearchController implements SearchController, Serializable {

    @Inject
    private UgostiteljDomainHelper ugostiteljDomainHelper;

    private UgostiteljSearchParams searchParams = new UgostiteljSearchParams();
    private ResultList<UgostiteljDTO> results = new ResultList<>();

    public void init() {

    }

    @Override
    public void reset() {
        searchParams.reset();
        results.clear();
    }

    @Override
    public void search() {
        ResultList<Ugostitelj> ugostiteljResultList = ugostiteljDomainHelper.search(searchParams);
        ResultList<UgostiteljDTO> ugostiteljDTOResultList = new ResultList<>();
        ugostiteljDTOResultList.setTotalResultCount(ugostiteljResultList.getTotalResultCount());
        ugostiteljDTOResultList.setList(UgostiteljMapper.INSTANCE.mapUgostiteljListToDTOList(ugostiteljResultList.getList()));
        results = ugostiteljDTOResultList;
    }

    @Override
    public UgostiteljSearchParams getSearchParams() {
        return searchParams;
    }

    @Override
    public ResultList<?> getResults() {
        return results;
    }
}
