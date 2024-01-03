package apishop.controller;

import apishop.exception.core.ArchitectureException;
import apishop.facade.EvaluateFacade;
import apishop.model.common.ResponseHandler;
import apishop.model.dto.EvaluateDto;
import apishop.model.dto.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static apishop.util.api.ConstantsApi.Evaluate.EVALUATE_PATH;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(EVALUATE_PATH)
public class EvaluateController {

    private final EvaluateFacade evaluateFacade;

    @PostMapping("/create")
    public ResponseEntity<Object> createEvaluate(@RequestBody EvaluateDto model,
                                                 @RequestParam String orderDetailId
    ) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK,
                evaluateFacade.create(model, orderDetailId), true);
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<Object> updateEvaluate(@PathVariable String id,@RequestBody EvaluateDto model) throws ArchitectureException {
//        return ResponseHandler.response(HttpStatus.OK, evaluateFacade.update(model, id), true);
//    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK,
                evaluateFacade.findById(id), true);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String columnSort
    ) throws ArchitectureException {

        return ResponseHandler.response(HttpStatus.OK,
                evaluateFacade.findAll(new SearchCriteria(page, size, columnSort)), true);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Object> findAllByProductId(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String columnSort
    ) throws ArchitectureException {

        return ResponseHandler.response(HttpStatus.OK,
                evaluateFacade.findAllProductId(id, new SearchCriteria(page, size, columnSort)), true);
    }

    @GetMapping("/account")
    public ResponseEntity<Object> findAllByAccountId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String columnSort
    ) throws ArchitectureException {

        return ResponseHandler.response(HttpStatus.OK,
                evaluateFacade.findAllAccountId(new SearchCriteria(page, size, columnSort)), true);
    }
}
