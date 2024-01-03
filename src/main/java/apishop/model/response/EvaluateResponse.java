package apishop.model.response;

import apishop.model.dto.AccountDto;
import apishop.model.dto.EvaluateDto;
import apishop.model.dto.ProductDto;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class EvaluateResponse {

    private String id;
    @NonNull
    private Integer rate;
    private String comment;
    @NonNull
    private Date date;
    private Boolean isUpdate;
    @NonNull
    private ProductDto productDto;
    private AccountDto accountDto;

    public static EvaluateResponse apply(EvaluateDto evaluateDto,
                                         ProductDto productDto,
                                         AccountDto accountDto) {
        return EvaluateResponse.builder()
                .id(evaluateDto.getId())
                .rate(evaluateDto.getRate())
                .comment(evaluateDto.getComment())
                .date(evaluateDto.getDate())
                .isUpdate(evaluateDto.getIsUpdate())
                .productDto(productDto)
                .accountDto(accountDto)
                .build();
    }
}
