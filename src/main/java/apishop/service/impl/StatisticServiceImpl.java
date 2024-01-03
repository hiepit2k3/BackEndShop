package apishop.service.impl;

import apishop.model.dto.GetTopAccountDto;
import apishop.model.dto.IncomeDto;
import apishop.model.response.ProductResponse;
import apishop.repository.AccountRepository;
import apishop.repository.OrderRepository;
import apishop.repository.ProductRepository;
import apishop.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatisticServiceImpl implements StatisticService {

    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

//    @Transactional(readOnly = true)
//    @Override
//    public List<GetTopAccountDto> getTopAccount(Integer top) {
//        Object[] objArray = accountRepository.getTopAccounts(top);
//
//        return Arrays.stream(objArray)
//                .map(obj -> {
//                    Object[] row = (Object[]) obj;
//                    GetTopAccountDto dto = new GetTopAccountDto();
//                    dto.setId((Long) row[0]);
//                    dto.setFullName((String) row[1]);
//                    dto.setImage((String) row[2]);
//                    dto.setUsername((String) row[3]);
//                    dto.setEmail((String) row[4]);
//                    dto.setTotalOrders((Double) row[5]);
//                    dto.setTotalProducts((Integer) row[6]);
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }

//    @Transactional(readOnly = true)
//    @Override
//    public List<ProductResponse> getTopProduct(Integer top) {
//        Object[] objArray = productRepository.getTopProducts(top);
//
//        return Arrays.stream(objArray)
//                .map(obj -> {
//                    Object[] row = (Object[]) obj;
//                    ProductResponse dto = new ProductResponse();
//                    dto.setId((String) row[0]);
//                    dto.setName((String) row[1]);
//                    dto.setOrder_count((Integer) row[2]);
//                    dto.setMin_price((Double) row[3]);
//                    dto.setMax_price((Double) row[4]);
//                    dto.setRate((Double) row[5]);
//                    dto.setMain_image((Binary) row[6]);
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }

    @Transactional(readOnly = true)
    @Override
    public List<IncomeDto> getIncomeByDate(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();

        if (month != null) {
            calendar.set(year, month - 1, 1);
            Date firstDayOfMonth = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date lastDayOfMonth = calendar.getTime();
            System.out.println(lastDayOfMonth);
            return orderRepository.getIncome(firstDayOfMonth, lastDayOfMonth);
        } else {
            calendar.set(year, 0, 1);
            Date firstDayOfYear = calendar.getTime();

            calendar.set(Calendar.MONTH, 11);
            calendar.set(Calendar.DAY_OF_MONTH, 31);
            Date lastDayOfYear = calendar.getTime();

            return orderRepository.getIncome(firstDayOfYear, lastDayOfYear);
        }
    }

//    private List<IncomeDto> processIncomeData(Object[] objArray) {
//        System.out.println(objArray.length);
//        return Arrays.stream(objArray)
//                .map(obj -> {
//                    Object[] row = (Object[]) obj;
//                    BigDecimal bd = BigDecimal.valueOf((Double) row[2]);
//                        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
//
//                    IncomeDto dto = new IncomeDto();
//                    if (row[0] instanceof Date) {
//                        dto.setDate((Date) row[0]);
//                    } else if (row[0] instanceof Integer) {
//                        dto.setMonth((Integer) row[0]);
//                    }
//                    dto.setTotalProducts((Integer) row[1]);
//                    dto.setTotalIncome(bd.doubleValue());
//                    dto.setTotalOrder((Integer) row[3]);
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
}
