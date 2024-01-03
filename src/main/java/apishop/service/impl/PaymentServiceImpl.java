package apishop.service.impl;

import apishop.entity.Payment;
import apishop.model.dto.PaymentDto;
import apishop.model.mapper.PaymentMapper;
import apishop.repository.PaymentRepository;
import apishop.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    //region
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    //endregion

    @Override
    public List<PaymentDto> findAllPayment() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream().map(paymentMapper::apply).collect(Collectors.toList());
    }

    @Override
    public PaymentDto findPaymentById(String paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        return payment.map(paymentMapper::apply).orElse(null);
    }


    @Override
    public List<PaymentDto> findPaymentByName(String paymentName) {
        List<Payment> payments = paymentRepository.findByNameContains(paymentName);
        if(payments != null) {
            return payments.stream().map(paymentMapper::apply).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public PaymentDto savePayment(PaymentDto paymentDto) throws IOException {
        if(paymentDto.getIsUpdate()){
            Optional<Payment> payment = paymentRepository.findById(paymentDto.getId());

        }
        return paymentMapper.apply(paymentRepository.save(paymentMapper.applyToPayment(paymentDto)));
    }


    @Override
    public void deletePaymentById(String paymentId) {
        paymentRepository.deleteById(paymentId);
    }
}
