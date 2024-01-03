package apishop.controller;

import apishop.exception.core.ArchitectureException;
import apishop.facade.OrderFacade;
import apishop.model.common.ResponseHandler;
import apishop.model.dto.SearchCriteria;
import apishop.model.request.OrderDtoRequest;
import apishop.util.enums.TypeOrder;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static apishop.util.api.ConstantsApi.Order.ORDER_PATH;

@CrossOrigin("*")
@RestController
@RequestMapping(ORDER_PATH)
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody
            OrderDtoRequest orderDtoRequest
    ) throws ArchitectureException, MessagingException {
        System.out.println(orderDtoRequest);
        return ResponseHandler.response(HttpStatus.OK,
                orderFacade.save(orderDtoRequest),true);
    }

    @GetMapping
    public ResponseEntity<Object> getAllOrderByAccountId(@RequestParam(required = false) TypeOrder type,
                                                         @RequestParam(defaultValue = "0") Integer page,
                                                         @RequestParam(defaultValue = "3") Integer size,
                                                         @RequestParam(defaultValue = "!purchaseDate") String columSort) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK,orderFacade
                .findByAllOrders(new SearchCriteria(page, size, columSort), type),true);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getOrderId(@PathVariable String id) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK,orderFacade.findOrderById(id),true);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateOrderStatus(@PathVariable String id,
                                                    @RequestParam TypeOrder typeOrder
    ) throws ArchitectureException, MessagingException {

        return ResponseHandler.response(HttpStatus.OK,
                orderFacade.updateTypeOrder(id, typeOrder),true);
    }

    @GetMapping("/return")
    public ResponseEntity<Object> redirectToFrontEnd(HttpServletRequest request,
                                                     HttpServletResponse response
    ) throws IOException, ArchitectureException, MessagingException {

        orderFacade.redirectToFrontEnd(request, response);
        return ResponseHandler.response(HttpStatus.OK,"Has redirected",true);
    }

    @GetMapping("/payment")
    public ResponseEntity<Object> payment(@RequestParam String orderId,
                                          HttpServletRequest request)
            throws IOException, ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK,
                orderFacade.payment(orderId, request),true);
    }
}
