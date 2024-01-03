package apishop.model.mapper;

import apishop.entity.Account;
import apishop.entity.Evaluate;
import apishop.entity.Product;
import apishop.model.dto.EvaluateDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

@Service
public class EvaluateMapper implements Function<Evaluate, EvaluateDto> {

    @Override
    public EvaluateDto apply(Evaluate evaluate) {
        return EvaluateDto
                .builder()
                .id(evaluate.getId())
                .comment(evaluate.getComment())
                .rate(evaluate.getRate())
                .isUpdate(evaluate.getIsUpdate())
                .productId(evaluate.getProduct().getId())
                .accountId(evaluate.getAccount().getId())
                .date(evaluate.getDate())
                .build()
                ;
    }

    public Evaluate applyToEvaluate(EvaluateDto evaluateDto,
                                    Account account,
                                    Product product) {
        return Evaluate.builder()
                .id(evaluateDto.getId())
                .rate(evaluateDto.getRate())
                .comment(evaluateDto.getComment())
                .isUpdate(evaluateDto.getIsUpdate() != null && evaluateDto.getIsUpdate())
                .account(account)
                .product(product)
                .date(new Date())
                .build();
    }
}
