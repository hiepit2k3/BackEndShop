package apishop.facade;

import apishop.exception.common.InvalidParamException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.GetTopAccountDto;
import apishop.model.dto.IncomeDto;
import apishop.model.response.ProductResponse;
import apishop.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticFacade {

    private final StatisticService statisticService;

//    public List<GetTopAccountDto> getTopAccount(Integer top) throws ArchitectureException {
//        List<GetTopAccountDto> list = statisticService.getTopAccount(top);
//        if (list.isEmpty()) throw new EntityNotFoundException();
//        return list;
//    }
//
//    public List<ProductResponse> getTopProduct(Integer top) throws ArchitectureException {
//        List<ProductResponse> list = statisticService.getTopProduct(top);
//        if (list.isEmpty()) throw new EntityNotFoundException();
//        return list;
//    }

    public List<IncomeDto> getIncome(String year, String month) throws ArchitectureException {
        /** Kiểm tra đúng năm chưa */
        if (year.isEmpty()) throw new InvalidParamException();

        /** Kiểm tra năm có đúng kiểu chưa*/
        int yearInt = Integer.parseInt(year);
        if (yearInt < 0 || yearInt > 9999) throw new InvalidParamException();

        List<IncomeDto> list;

        int monthInt = (month != null) ? Integer.parseInt(month) : -1;

        /** Kiểm tra tháng có tồn tại đúng kiểu chưa*/
        if (month != null && monthInt >= 1 && monthInt <= 12)
            list = statisticService.getIncomeByDate(yearInt, monthInt);

        /** Nếu không có tháng */
        else if (month == null)
            list = statisticService.getIncomeByDate(yearInt, null);

        /** Nếu có tháng nhưng không đúng kiểu */
        else
            throw new InvalidParamException();

        if (list.isEmpty())
            throw new EntityNotFoundException();

        return list;
    }
}
