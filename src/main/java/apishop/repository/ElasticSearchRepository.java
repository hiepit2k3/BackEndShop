//package edu.poly.duantotnghiep.repository;
//
//import edu.poly.duantotnghiep.model.dto.ElasticSearch;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.annotations.Query;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//
//
//public interface ElasticSearchRepository extends ElasticsearchRepository<ElasticSearch, String> {
//
//    @Query("""
//            {
//                "multi_match":
//                {
//                    "query": "?0",
//                    "fields": ["name", "hashtags", "brand", "category", "season", "gender"]
//                }
//            }
//            """)
//    Page<ElasticSearch> search(String key, Pageable pageable);
//    @Override
//    Page<ElasticSearch> findAll(Pageable pageable);
//
//
//}
