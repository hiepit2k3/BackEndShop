package apishop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class IncomeDto {

    private Integer month;
    private Date date;

    private Integer totalOrder;

    private Integer totalProducts;

    private Double totalIncome;

}
