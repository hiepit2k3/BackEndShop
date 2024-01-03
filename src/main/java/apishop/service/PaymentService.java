package apishop.service;


import apishop.model.dto.PaymentDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface PaymentService {

    List<PaymentDto> findAllPayment();

    PaymentDto findPaymentById(String paymentId);

    List<PaymentDto> findPaymentByName(String paymentName);

    PaymentDto savePayment(PaymentDto paymentDto) throws IOException;

    void deletePaymentById(String paymentId);
}
