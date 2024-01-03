package apishop.repository;

import apishop.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends MongoRepository<Brand,String> {
    Page<Brand> findAllByNameContains(String name, Pageable pageable);
}
