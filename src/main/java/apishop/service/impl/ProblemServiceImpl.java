package apishop.service.impl;

import apishop.entity.Problem;
import apishop.model.dto.ProblemDto;
import apishop.model.mapper.ProblemMapper;
import apishop.repository.ProblemRepository;
import apishop.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    //region
    private final ProblemRepository problemRepository;
    private final ProblemMapper problemMapper;
    //endregion

    @Override
    public List<ProblemDto> findAllProblem() {
        List<Problem> problems = problemRepository.findAll();
        return problems.stream().map(problemMapper :: apply).collect(Collectors.toList());
    }

    @Override
    public ProblemDto findProblemById(String problemId) {
        Optional<Problem> problem = problemRepository.findById(problemId);
        return problem.map(problemMapper::apply).orElse(null);
    }

    @Override
    public ProblemDto saveProblem(ProblemDto problemDto) {
        return problemMapper.apply(problemRepository.save(problemMapper.applyToProblem(problemDto)));
    }

    @Override
    public void deleteProblemById(String problemId) {
        problemRepository.deleteById(problemId);
    }
}
