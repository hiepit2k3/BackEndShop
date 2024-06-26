package apishop.repository;

import apishop.entity.Hashtag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashtagRepository extends MongoRepository<Hashtag, String> {
    Page<Hashtag> findByNameContains(String name, Pageable pageable);

    Page<Hashtag> findAll(Pageable pageable);
}