package apishop.controller;

import apishop.exception.core.ArchitectureException;
import apishop.facade.HashtagFacade;
import apishop.model.common.ResponseHandler;
import apishop.model.dto.HashtagDto;
import apishop.model.dto.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static apishop.util.api.ConstantsApi.Hashtag.HASHTAG_PATH;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(HASHTAG_PATH)
public class HashtagController {

    private final HashtagFacade hashtagFacade;

    @GetMapping
    public ResponseEntity<Object> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "id") String columSort
    ) throws ArchitectureException {
        return ResponseHandler.response(
                HttpStatus.OK,
                hashtagFacade.findAllHashtag(new SearchCriteria(page, size, columSort)),
                true
        );
    }

    @GetMapping("/id/{hashtagId}")
    public ResponseEntity<Object> findHashtagById(@PathVariable String hashtagId) throws ArchitectureException {
        return ResponseHandler.response(
                HttpStatus.OK,
                hashtagFacade.findHashtagById(hashtagId),
                true
        );
    }

    @GetMapping("/name/{hashtagName}")
    public ResponseEntity<Object> findAllHashtagByName(
            @PathVariable String hashtagName,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "id") String columSort )
            throws ArchitectureException {
        return ResponseHandler.response(
                HttpStatus.OK,
                hashtagFacade.findAllHashtagByName(hashtagName, new SearchCriteria(page, size, columSort)),
                true
        );
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createHashtag(@RequestBody HashtagDto hashtagDto) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK, hashtagFacade.createHashtag(hashtagDto), true);

    }

    @PutMapping("/update/{hashtagId}")
    public ResponseEntity<Object> updateHashtag(@RequestBody HashtagDto hashtagDto,@PathVariable String hashtagId) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK,
                hashtagFacade.updateHashtag(hashtagDto, hashtagId), true);
    }

    @DeleteMapping("/delete/{hashtagId}")
    public ResponseEntity<Object> deleteHashtag(@PathVariable String hashtagId) throws ArchitectureException {
        hashtagFacade.deleteHashtagById(hashtagId);
        return ResponseHandler.response(HttpStatus.OK, "Delete successfully", true);
    }
}
