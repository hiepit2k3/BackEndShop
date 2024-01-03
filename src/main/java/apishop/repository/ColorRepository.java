package apishop.repository;

import apishop.entity.Color;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends MongoRepository<Color,String> {
    List<Color> findAll();
}
