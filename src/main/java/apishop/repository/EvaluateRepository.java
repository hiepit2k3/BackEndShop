package apishop.repository;

import apishop.entity.Evaluate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluateRepository extends MongoRepository<Evaluate,String> {

    Page<Evaluate> findAllByAccount_Id(String accountId, Pageable pageable);
    Page<Evaluate> findAllByProduct_Id(String productId, Pageable pageable);
}
