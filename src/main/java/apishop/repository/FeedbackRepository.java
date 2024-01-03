package apishop.repository;

import apishop.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends MongoRepository<Feedback, String> {
    List<Feedback> findByEmail(String email);

    Page<Feedback> findAllByProblemId(String problemId, Pageable pageable);

    List<Feedback> findByPhoneNumber(String phoneNumber);

    Page<Feedback> findByStatus(Boolean status , Pageable pageable);
}
