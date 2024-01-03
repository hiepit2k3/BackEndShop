package apishop.model.mapper;

import apishop.entity.Feedback;
import apishop.entity.Problem;
import apishop.model.dto.FeedbackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class FeedbackMapper implements Function<Feedback, FeedbackDto> {


    @Override
    public FeedbackDto apply(Feedback feedback) {
        return FeedbackDto
                .builder()
                .id(feedback.getId())
                .email(feedback.getEmail())
                .description(feedback.getDescription())
                .date(feedback.getDate())
                .problemId(feedback.getProblem().getId())
                .phoneNumber(feedback.getPhoneNumber())
                .status(feedback.getStatus())
                .problemName(feedback.getProblem().getName())
                .build();
    }

    public Feedback applyToFeedback(FeedbackDto feedbackDto, Problem problem) {
        return Feedback
                .builder()
                .id(feedbackDto.getId())
                .email(feedbackDto.getEmail())
                .description(feedbackDto.getDescription())
                .problem(problem)
                .phoneNumber(feedbackDto.getPhoneNumber())
                .status(feedbackDto.getStatus() != null && feedbackDto.getStatus())
                .date(feedbackDto.getDate() == null ? new Date() : feedbackDto.getDate())
                .build();
    }
}
