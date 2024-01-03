//package edu.poly.duantotnghiep.facade;
//
//import edu.poly.duantotnghiep.exception.core.ArchitectureException;
//import edu.poly.duantotnghiep.exception.entity.EntityNotFoundException;
//import edu.poly.duantotnghiep.model.dto.ElasticSearch;
//import edu.poly.duantotnghiep.model.dto.SearchCriteria;
//import edu.poly.duantotnghiep.repository.ElasticSearchRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Service;
//
//import static edu.poly.duantotnghiep.util.method.Search.getPageable;
//
//@Service
//@RequiredArgsConstructor
//public class ElasticSearchFacade {
//
//    private final ElasticSearchRepository elasticSearchRepository;
//
//    public Page<ElasticSearch> search(String key, SearchCriteria searchCriteria) throws ArchitectureException {
//        Page<ElasticSearch> elasticSearches = elasticSearchRepository.search(key, getPageable(searchCriteria));
//        if (elasticSearches.isEmpty()) {
//            throw new EntityNotFoundException();
//        }
//        return elasticSearches;
//    }
//
//    public void deleteById(String id) {
//        elasticSearchRepository.deleteById(id);
//    }
//
//}
