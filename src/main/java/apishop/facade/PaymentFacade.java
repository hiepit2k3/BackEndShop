package apishop.facade;

import apishop.entity.Payment;
import apishop.exception.common.CanNotDeleteException;
import apishop.exception.common.IdMustBeNullException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.PaymentDto;
import apishop.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentService paymentService;

    //Tìm tất cả các phương thức thanh toán
    public List<PaymentDto> findAllPayment() throws ArchitectureException {
        List<PaymentDto> paymentDtos = paymentService.findAllPayment();
        if (paymentDtos.isEmpty())
            throw new EntityNotFoundException();
        return paymentDtos;
    }

    //Tìm phương thức thanh toán theo id
    public PaymentDto findPaymentById(String paymentId) throws ArchitectureException {
        PaymentDto paymentDto = paymentService.findPaymentById(paymentId);
        if (paymentDto == null)
            throw new EntityNotFoundException();
        return paymentDto;
    }

    //Tìm phương thức thanh toán theo tên
    public List<PaymentDto> findPaymentByName(String paymentName) throws ArchitectureException {
        List<PaymentDto> paymentDto = paymentService.findPaymentByName(paymentName);
        if (paymentDto.isEmpty())
            throw new EntityNotFoundException();
        return paymentDto;
    }

    //Thêm phương thức thanh toán
    public PaymentDto createPayment(PaymentDto paymentDto) throws IdMustBeNullException, IOException {
        if (paymentDto.getId() != null) throw new IdMustBeNullException(Payment.class.getSimpleName());
        return paymentService.savePayment(paymentDto);
    }

    //Xóa phương thức thanh toán
    public void deletePaymentById(String paymentId) throws ArchitectureException {
        findPaymentById(paymentId);
        try {
            paymentService.deletePaymentById(paymentId);
        } catch (DataIntegrityViolationException e) {
            throw new CanNotDeleteException("payment");
        }
    }

    //Cập nhật phương thức thanh toán
    public PaymentDto updatePayment(PaymentDto paymentDto, String id) throws IOException, ArchitectureException {
        findPaymentById(id);
        paymentDto.setId(id);
        paymentDto.setIsUpdate(true);
        return paymentService.savePayment(paymentDto);
    }
}
