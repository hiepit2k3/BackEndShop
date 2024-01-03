package apishop.controller;

import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.facade.FeedbackFacade;
import apishop.model.common.ResponseHandler;
import apishop.model.dto.FeedbackDto;
import apishop.model.dto.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static apishop.util.api.ConstantsApi.Feedback.FEEDBACK_PATH;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(FEEDBACK_PATH)
public class FeedbackController {
    private final FeedbackFacade feedbackFacade;


    @PostMapping("/create")
    public ResponseEntity<Object> saveFeedback(@RequestBody FeedbackDto model) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK, feedbackFacade.createFeedback(model), true);
    }

    @GetMapping("/problem/{problemId}")
    public ResponseEntity<Object> getFeedbackByProblemId(@PathVariable String problemId,
                                                      @RequestParam(defaultValue = "0") Integer page,
                                                      @RequestParam(defaultValue = "10") Integer size,
                                                      @RequestParam(defaultValue = "!date") String columSort) throws ArchitectureException{
        return ResponseHandler.response(HttpStatus.OK,feedbackFacade.findAllByProblemId(new SearchCriteria(page,size,columSort),problemId),true);
    }

    @GetMapping
    public ResponseEntity<Object> getAllFeedback(
                                                      @RequestParam(defaultValue = "0") Integer page,
                                                      @RequestParam(defaultValue = "10") Integer size,
                                                      @RequestParam(defaultValue = "!date") String columSort) throws ArchitectureException{
        return ResponseHandler.response(HttpStatus.OK,feedbackFacade.findAll(new SearchCriteria(page,size,columSort)),true);
    }

    @GetMapping("/id/{feedbackId}")
    public ResponseEntity<Object> getFeedbackById(@PathVariable String feedbackId) throws ArchitectureException{
        return ResponseHandler.response(HttpStatus.OK,feedbackFacade.findFeedbackById(feedbackId),true);
    }

    @GetMapping("/email/{feedbackEmail}")
    public ResponseEntity<Object> getFeedbackEmail(@PathVariable String feedbackEmail) throws ArchitectureException{
        return ResponseHandler.response(HttpStatus.OK,feedbackFacade.findFeedbackByEmail(feedbackEmail),true);
    }

    @GetMapping("/phone/{feedbackPhoneNumber}")
    public ResponseEntity<Object> getFeedbackPhoneNumber(@PathVariable String feedbackPhoneNumber) throws ArchitectureException{
        return ResponseHandler.response(HttpStatus.OK,feedbackFacade.findFeedbackByPhoneNumber(feedbackPhoneNumber),true);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Object> findAllFeedbackByStatus(@PathVariable Boolean status,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "!date") String columnSort) throws EntityNotFoundException, EntityNotFoundException {
        return ResponseHandler.response(HttpStatus.OK, feedbackFacade.getAllFeedbackStatus(status,new SearchCriteria(page,size,columnSort)),true);
    }
    
    @PutMapping("set-status/{id}/{status}")
    public ResponseEntity<Object> setStatus(@PathVariable Boolean status,@PathVariable String id) throws EntityNotFoundException {
        return ResponseHandler.response(HttpStatus.OK,feedbackFacade.updateStatus(status,id),true);
    }
}
