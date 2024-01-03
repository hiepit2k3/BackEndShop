package apishop.controller;

import apishop.exception.core.ArchitectureException;
import apishop.facade.DiscountFacade;
import apishop.model.common.ResponseHandler;
import apishop.model.dto.DiscountDto;
import apishop.model.dto.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static apishop.util.api.ConstantsApi.Discount.DISCOUNT_PATH;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(DISCOUNT_PATH)
public class DiscountController {

    private final DiscountFacade discountFacade;

    /**
     * Only admin can access
     */
    @PostMapping("/create")
    public ResponseEntity<Object> saveCustomer(DiscountDto model) throws ArchitectureException, IOException {
        return ResponseHandler.response(HttpStatus.OK, discountFacade.createDiscount(model), true);
    }

    @PutMapping("/update/{discountId}")
    public ResponseEntity<Object> update(DiscountDto discountDto,
                                         @PathVariable String discountId) throws ArchitectureException, IOException {
        return ResponseHandler.response(HttpStatus.OK,
                discountFacade.updateDiscount(discountId, discountDto), true);
    }

    @DeleteMapping("/delete/{discountId}")
    public ResponseEntity<Object> deleteDiscount(@PathVariable String discountId) throws ArchitectureException {
        discountFacade.deleteDiscountById(discountId);
        return ResponseHandler.response(HttpStatus.OK, "Delete successfully!", true);
    }

    /**
     * Anyone can access
     */
    @GetMapping
    public ResponseEntity<Object> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String columSort
    ) throws ArchitectureException {
        return ResponseHandler.response(
                HttpStatus.OK,
                discountFacade.findAll(new SearchCriteria(page, size, columSort)),
                true
        );
    }

    /**
     * Anyone can access
     */
    @GetMapping("/id/{discountId}")
    public ResponseEntity<Object> findDiscountById(@PathVariable String discountId) throws ArchitectureException {
        return ResponseHandler.response(
                HttpStatus.OK,
                discountFacade.findDiscountById(discountId),
                true
        );
    }

    /**
     * Anyone can access
     */
    @GetMapping("/is-active")
    public ResponseEntity<Object> findAllIsActived() throws ArchitectureException {
        return ResponseHandler.response(
                HttpStatus.OK,
                discountFacade.findAllIsActived(),
                true
        );
    }
}
