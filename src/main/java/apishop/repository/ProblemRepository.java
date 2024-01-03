package apishop.repository;

import apishop.entity.Problem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository  extends MongoRepository<Problem, String> {
    Problem findByNameContains(String name);
}
