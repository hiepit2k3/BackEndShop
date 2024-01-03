package apishop.model.mapper;

import apishop.entity.Problem;
import apishop.model.dto.ProblemDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProblemMapper implements Function<Problem, ProblemDto> {


    @Override
    public ProblemDto apply(Problem problem) {
        return ProblemDto
                .builder()
                .id(problem.getId())
                .name(problem.getName())
                .build();
    }

    public Problem applyToProblem(ProblemDto problemDto) {
        return Problem
                .builder()
                .id(problemDto.getId())
                .name(problemDto.getName())
                .build();
    }
}
