package apishop.service.impl;

import apishop.entity.Feedback;
import apishop.entity.Problem;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.FeedbackDto;
import apishop.model.dto.SearchCriteria;
import apishop.model.mapper.FeedbackMapper;
import apishop.repository.FeedbackRepository;
import apishop.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static apishop.util.method.Search.getPageable;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    //region
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    //endregion


    @Override
    public Page<FeedbackDto> findAllFeedback(SearchCriteria searchCriteria){
        Page<Feedback> feedbacks = feedbackRepository.findAll(getPageable(searchCriteria));
        return feedbacks.map(feedbackMapper :: apply);
    }

    @Override
    public Page<FeedbackDto> findAllByProblemId(SearchCriteria searchCriteria, String problemId) {
        Page<Feedback> feedbacks = feedbackRepository.findAllByProblemId(problemId,getPageable(searchCriteria));
        return feedbacks.map(feedbackMapper :: apply);
    }

    @Override
    public FeedbackDto findFeedbackById(String feedbackId) {
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        return feedback.map(feedbackMapper::apply).orElse(null);
    }

    @Override
    public List<FeedbackDto> findFeedbackByEmail(String feedbackEmail) {
        List<Feedback> feedbacks = feedbackRepository.findByEmail(feedbackEmail);
        if(feedbacks != null) {
            return feedbacks.stream().map(feedbackMapper::apply).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<FeedbackDto> findFeedbackByPhoneNumber(String phoneNumber) {
        List<Feedback> feedbacks = feedbackRepository.findByPhoneNumber(phoneNumber);
        if(feedbacks != null) {
            return feedbacks.stream().map(feedbackMapper::apply).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public FeedbackDto saveFeedback(FeedbackDto feedbackDto, Problem problem) {
        return feedbackMapper.apply(feedbackRepository.save(feedbackMapper.applyToFeedback(feedbackDto, problem)));
    }

    @Override
    public Page<FeedbackDto> findByStatus(Boolean status, SearchCriteria searchCriteria) {
        Page<Feedback> feedbackPage = feedbackRepository.findByStatus(status,getPageable(searchCriteria));
        return  feedbackPage.map(feedbackMapper::apply);
    }

    @Override
    public FeedbackDto updateStatus(Boolean status, String id) throws EntityNotFoundException {
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        if(feedback.isEmpty()) throw new EntityNotFoundException();
        feedback.get().setStatus(status);
        return feedbackMapper.apply(feedbackRepository.save(feedback.get()));
    }

    @Override
    public void deleteFeedbackById(String feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }
}
