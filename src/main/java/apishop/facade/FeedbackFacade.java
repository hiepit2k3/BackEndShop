package apishop.facade;

import apishop.entity.Feedback;
import apishop.entity.Problem;
import apishop.exception.common.ForeignKeyIsNotFound;
import apishop.exception.common.IdMustBeNullException;
import apishop.exception.common.InvalidParamException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.FeedbackDto;
import apishop.model.dto.SearchCriteria;
import apishop.repository.ProblemRepository;
import apishop.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackFacade {

    private final FeedbackService feedbackService;
    private final ProblemRepository problemRepository;

    public Page<FeedbackDto> findAll(SearchCriteria searchCriteria) throws ArchitectureException {
        Page<FeedbackDto> listFeedbacks = feedbackService.findAllFeedback(searchCriteria);
        if (listFeedbacks.isEmpty()) {
            throw new EntityNotFoundException();
//               trường hợp không tồn tại sẽ bắn lỗi
        }
        return listFeedbacks;
    }

    public Page<FeedbackDto> findAllByProblemId(SearchCriteria searchCriteria, String problemId) throws ArchitectureException {
        Page<FeedbackDto> listFeedbacks = feedbackService.findAllByProblemId(searchCriteria, problemId);
        if (listFeedbacks.isEmpty()) {
            throw new EntityNotFoundException();
//                trường hợp không tồn tại sẽ bắn lỗi
        }
        return listFeedbacks;
    }

    public FeedbackDto findFeedbackById(String feedbackId) throws ArchitectureException {
//            Checking params
        return checkNotNull(feedbackId);
    }

    public List<FeedbackDto> findFeedbackByEmail(String feedbackEmail) throws ArchitectureException {
//            Checking params
        if (feedbackEmail.isEmpty()) throw new InvalidParamException();
        List<FeedbackDto> existFeedbackDto = feedbackService.findFeedbackByEmail(feedbackEmail);
        if (existFeedbackDto.isEmpty()) {
            throw new EntityNotFoundException();
//                trường hợp không tồn tại sẽ bắn lỗi
        }
        return existFeedbackDto;
    }

    public List<FeedbackDto> findFeedbackByPhoneNumber(String phoneNumber) throws ArchitectureException {
        if (phoneNumber.isEmpty()) throw new InvalidParamException();
        List<FeedbackDto> existFeedbackDto = feedbackService.findFeedbackByPhoneNumber(phoneNumber);
        if (existFeedbackDto.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return existFeedbackDto;
    }

    public FeedbackDto createFeedback(FeedbackDto feedbackDto) throws ArchitectureException {
        if(feedbackDto.getId() != null) throw new IdMustBeNullException(Feedback.class.getSimpleName());
        Optional<Problem> problem = problemRepository.findById(feedbackDto.getProblemId());
        if(problem.isEmpty())
            throw new ForeignKeyIsNotFound("Problem");
        return feedbackService.saveFeedback(feedbackDto, problem.get());
    }

    private FeedbackDto checkNotNull(String feedbackId) throws EntityNotFoundException {
        FeedbackDto existFeedbackDto = feedbackService.findFeedbackById(feedbackId);
        if (existFeedbackDto == null) {
            throw new EntityNotFoundException();
        }
        return existFeedbackDto;
    }

    public Page<FeedbackDto> getAllFeedbackStatus(Boolean status, SearchCriteria searchCriteria) throws EntityNotFoundException {
        Page<FeedbackDto> feedback = feedbackService.findByStatus(status, searchCriteria);
        if(feedback == null) throw new EntityNotFoundException();
        return feedback;
    }

    public FeedbackDto updateStatus(Boolean status,String id) throws EntityNotFoundException {
        return feedbackService.updateStatus(status,id);
    }
}
