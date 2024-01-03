package apishop.service;

import apishop.model.dto.ProblemDto;

import java.util.List;

public interface ProblemService {

    List<ProblemDto> findAllProblem();

    ProblemDto findProblemById(String problemId);


    ProblemDto saveProblem(ProblemDto problemDto);

    void deleteProblemById(String problemId);
}
