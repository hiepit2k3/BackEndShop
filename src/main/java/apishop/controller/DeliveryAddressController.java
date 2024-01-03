package apishop.controller;

import apishop.exception.core.ArchitectureException;
import apishop.facade.DeliveryAddressFacade;
import apishop.model.common.ResponseHandler;
import apishop.model.dto.DeliveryAddressDto;
import apishop.model.dto.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static apishop.util.api.ConstantsApi.DeliveryAddress.DELIVERY_ADDRESS_PATH;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(DELIVERY_ADDRESS_PATH)
public class DeliveryAddressController {

    private final DeliveryAddressFacade deliveryAddressFacade;

    @PostMapping("/create")
    public ResponseEntity<Object> createDeliveryAddress(@RequestBody DeliveryAddressDto deliveryAddressDto) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK,
                deliveryAddressFacade.create(deliveryAddressDto), true);
    }

    @PutMapping("/update/{idAddress}")
    public ResponseEntity<Object> updateDeliveryAddress(@PathVariable String idAddress, @RequestBody DeliveryAddressDto deliveryAddressDto) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK,
                deliveryAddressFacade.update(deliveryAddressDto, idAddress), true);
    }

    @GetMapping("/id/{idAddress}")
    public ResponseEntity<Object> findById(@PathVariable String idAddress) throws ArchitectureException {
        DeliveryAddressDto dto = deliveryAddressFacade.findById(idAddress);
        return ResponseHandler.response(HttpStatus.OK, dto, true);
    }

    @DeleteMapping("/delete/{idAddress}")
    public ResponseEntity<Object> deleteById(@PathVariable String idAddress) throws ArchitectureException {
        deliveryAddressFacade.deleteById(idAddress);
        return ResponseHandler.response(HttpStatus.OK, "Delete successfully!", true);
    }

    @GetMapping("/account")
    public ResponseEntity<Object> findAllByAccountId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String columnSort
    ) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK,
                deliveryAddressFacade.findAllByAccountId(new SearchCriteria(page, size, columnSort)), true);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String columnSort
    ) throws ArchitectureException {
        Page<DeliveryAddressDto> list = deliveryAddressFacade.findAll(new SearchCriteria(page, size, columnSort));
            return ResponseHandler.response(HttpStatus.OK, list, true);
    }
}
