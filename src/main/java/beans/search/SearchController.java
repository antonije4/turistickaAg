package beans.search;

import repository.data.ResultList;
import repository.search.SearchParams;

import javax.faces.model.SelectItem;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface SearchController {
    void reset();
    void search();
    SearchParams getSearchParams();
    ResultList<?> getResults();
    List<SelectItem> maxResults = Stream.of(new SelectItem(10, "10"),
                    new SelectItem(50, "50"),
                    new SelectItem(100, "100"),
                    new SelectItem(1000, "1000"),
                    new SelectItem(10000, "10000"),
                    new SelectItem(-1, "All"))
            .collect(Collectors.toList());
}
