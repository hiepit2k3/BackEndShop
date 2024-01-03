package apishop.facade;

import apishop.entity.Problem;
import apishop.exception.common.CanNotDeleteException;
import apishop.exception.common.IdMustBeNullException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.ProblemDto;
import apishop.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemFacade {

    private final ProblemService problemService;

    //Tìm kiếm tất cả các vấn đề
    public List<ProblemDto> findAllProblem() throws ArchitectureException {
        List<ProblemDto> list = problemService.findAllProblem();
        if (list.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return list;
    }

    //Tìm kiếm vấn đề theo id
    public ProblemDto findProblemById(String problemId) throws ArchitectureException {
        ProblemDto problemDto = problemService.findProblemById(problemId);
        if (problemDto == null) {
            throw new EntityNotFoundException();
        }
        return problemDto;
    }

    public ProblemDto create(ProblemDto problemDto) throws IdMustBeNullException {
        if (problemDto.getId() != null) throw new IdMustBeNullException(Problem.class.getSimpleName());
        return problemService.saveProblem(problemDto);
    }

    //Lưu vấn đề
    public ProblemDto update(ProblemDto problemDto, String id) throws ArchitectureException {
        findProblemById(id);
        problemDto.setId(id);
        return problemService.saveProblem(problemDto);
    }

    //Xóa vấn đề theo id
    public void deleteProblemById(String problemId) throws ArchitectureException {
        findProblemById(problemId);
        try {
            problemService.deleteProblemById(problemId);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new CanNotDeleteException("problem");
        }
    }
}
