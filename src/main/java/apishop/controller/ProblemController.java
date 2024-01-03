package apishop.controller;

import apishop.exception.core.ArchitectureException;
import apishop.facade.ProblemFacade;
import apishop.model.common.ResponseHandler;
import apishop.model.dto.ProblemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static apishop.util.api.ConstantsApi.Problem.PROBLEM_PATH;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(PROBLEM_PATH)
public class ProblemController {
    private final ProblemFacade problemFacade;
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody ProblemDto problemDto) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK,problemFacade.create(problemDto),true);
    }

    @PutMapping("/update/{problemId}")
    public ResponseEntity<Object> updateProblem(@RequestBody ProblemDto problemDto,@PathVariable("problemId") String problemId) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK,
                problemFacade.update(problemDto, problemId),true);
    }
    @DeleteMapping("/delete/{problemId}")
    public ResponseEntity<Object> delete(@PathVariable String problemId) throws ArchitectureException {
        problemFacade.deleteProblemById(problemId);
        return ResponseHandler.response(HttpStatus.OK,"delete successfully!",true);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK, problemFacade.findAllProblem(),true);
    }

    @GetMapping("/id/{problemId}")
    public ResponseEntity<Object> findProblemById(@PathVariable String problemId) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK, problemFacade.findProblemById(problemId),true);
    }
}
