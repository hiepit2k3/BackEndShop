package apishop.controller;


import apishop.exception.core.ArchitectureException;
import apishop.facade.PaymentFacade;
import apishop.model.common.ResponseHandler;
import apishop.model.dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static apishop.util.api.ConstantsApi.Payment.PAYMENT_PATH;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(PAYMENT_PATH)
public class PaymentController {

    private final PaymentFacade paymentFacade;

    @GetMapping
    public ResponseEntity<Object> getAll(
    ) throws ArchitectureException {
        return ResponseHandler.response(
                HttpStatus.OK,
                paymentFacade.findAllPayment(),
                true
        );
    }

    @GetMapping("/id/{paymentId}")
    public ResponseEntity<Object> findPaymentById(@PathVariable String paymentId) throws ArchitectureException {
        return ResponseHandler.response(
                HttpStatus.OK,
                paymentFacade.findPaymentById(paymentId),
                true
        );
    }

    @GetMapping("/name/{paymentName}")
    public ResponseEntity<Object> findPaymentByName(@PathVariable String paymentName) throws ArchitectureException {
        return ResponseHandler.response(
                HttpStatus.OK,
                paymentFacade.findPaymentByName(paymentName),
                true
        );
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createPayment(PaymentDto paymentDto) throws ArchitectureException, IOException {
        return ResponseHandler.response(HttpStatus.OK, paymentFacade.createPayment(paymentDto), true);

    }

    @PutMapping("/update/{paymentId}")
    public ResponseEntity<Object> updatePayment(PaymentDto paymentDto,@PathVariable String paymentId) throws ArchitectureException, IOException {
        return ResponseHandler.response(HttpStatus.OK, paymentFacade.updatePayment(paymentDto,paymentId), true);
    }

    @DeleteMapping("/delete/{paymentId}")
    public ResponseEntity<Object> deletePayment(@PathVariable String paymentId) throws ArchitectureException {
        paymentFacade.deletePaymentById(paymentId);
        return ResponseHandler.response(HttpStatus.OK, "Delete successfully", true);
    }
}
