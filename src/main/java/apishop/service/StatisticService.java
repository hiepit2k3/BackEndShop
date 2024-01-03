package apishop.service;

import apishop.model.dto.GetTopAccountDto;
import apishop.model.dto.IncomeDto;
import apishop.model.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface StatisticService {

//    List<GetTopAccountDto> getTopAccount(Integer top);
//
//    List<ProductResponse> getTopProduct(Integer top);

    List<IncomeDto> getIncomeByDate(Integer year, Integer month);
}
