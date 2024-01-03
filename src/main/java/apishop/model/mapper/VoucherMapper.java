package apishop.model.mapper;

import apishop.entity.Voucher;
import apishop.model.dto.VoucherDto;
import apishop.util.FileConverter;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.function.Function;

@Service
public class VoucherMapper implements Function<Voucher, VoucherDto> {


    @Override
    public VoucherDto apply(Voucher voucher) {
        return VoucherDto.builder()
                .id(voucher.getId())
                .discount(voucher.getDiscount())
                .name(voucher.getName())
                .description(voucher.getDescription())
                .quantity(voucher.getQuantity())
                .image(Base64.getEncoder().encodeToString(voucher.getImage().getData()))
                .registerDate(voucher.getRegisterDate())
                .expirationDate(voucher.getExpirationDate())
                .minTotal(voucher.getMinTotal())
                .maxDiscount(voucher.getMaxDiscount())
                .typeDiscount(voucher.getTypeDiscount())
                .build();
    }

    public Voucher applyVoucher(VoucherDto voucherDto) throws IOException {
        Binary image = FileConverter.convertMultipartFileToBinary(voucherDto.getImageFile());
        return Voucher.builder()
                .id(voucherDto.getId())
                .discount(voucherDto.getDiscount())
                .name(voucherDto.getName())
                .description(voucherDto.getDescription())
                .quantity(voucherDto.getQuantity())
                .image(image)
                .registerDate(voucherDto.getRegisterDate())
                .expirationDate(voucherDto.getExpirationDate())
                .minTotal(voucherDto.getMinTotal())
                .maxDiscount(voucherDto.getMaxDiscount())
                .typeDiscount(voucherDto.getTypeDiscount())
                .build();
    }
}
