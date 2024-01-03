package apishop.service;

import apishop.entity.Problem;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.FeedbackDto;
import apishop.model.dto.SearchCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FeedbackService {

    Page<FeedbackDto> findAllFeedback(SearchCriteria searchCriteria);

    Page<FeedbackDto> findAllByProblemId(SearchCriteria searchCriteria, String problemId);

    FeedbackDto findFeedbackById(String feedbackId);

    List<FeedbackDto> findFeedbackByEmail(String feedbackEmail);

    List<FeedbackDto> findFeedbackByPhoneNumber(String phoneNumber);

    FeedbackDto saveFeedback(FeedbackDto feedbackDt, Problem problem);

    Page<FeedbackDto> findByStatus(Boolean status, SearchCriteria searchCriteria);

    FeedbackDto updateStatus(Boolean status, String id) throws EntityNotFoundException;

    void deleteFeedbackById(String feedbackId);
}
