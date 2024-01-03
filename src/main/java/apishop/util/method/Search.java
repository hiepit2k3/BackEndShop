package apishop.util.method;
import apishop.model.dto.SearchCriteria;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Search {

    public static Pageable getPageable(SearchCriteria searchCriteria) {

       return searchCriteria.getColumSort().startsWith("!")
                ?
               PageRequest.of(
                       searchCriteria.getPage(),
                       searchCriteria.getSize(),
                       Sort.by(Sort.Direction.DESC,
                               searchCriteria.getColumSort().substring(1)))
                :
               PageRequest.of(
                       searchCriteria.getPage(),
                       searchCriteria.getSize(),
                       Sort.by(Sort.Direction.ASC,
                               searchCriteria.getColumSort())
               );

    }
}
