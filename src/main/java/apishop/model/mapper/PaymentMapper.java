package apishop.model.mapper;

import apishop.entity.Brand;
import apishop.entity.Payment;
import apishop.model.dto.PaymentDto;
import apishop.util.FileConverter;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.function.Function;

@Service
public class PaymentMapper implements Function<Payment, PaymentDto> {


    @Override
    public PaymentDto apply(Payment payment) {
        return PaymentDto
                .builder()
                .id(payment.getId())
                .image(Base64.getEncoder().encodeToString(payment.getImage().getData()))
                .name(payment.getName())
                .description(payment.getDescription())
                .build();
    }

    public Payment applyToPayment(PaymentDto paymentDto) throws IOException {
        if (paymentDto.getImageFile() != null) {
            Binary image = FileConverter.convertMultipartFileToBinary(paymentDto.getImageFile());
            return Payment
                    .builder()
                    .id(paymentDto.getId())
                    .image(image)
                    .name(paymentDto.getName())
                    .description(paymentDto.getDescription())
                    .build();
        }
        // Chuyển đổi từ Base64 về dữ liệu binary
        byte[] Data = Base64.getDecoder().decode(paymentDto.getImage());
        return Payment.builder()
                .id(paymentDto.getId())
                .description(paymentDto.getDescription())
                .name(paymentDto.getName())
                .image(new Binary(Data))
                .build();
    }
}
