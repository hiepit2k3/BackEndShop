package apishop.service;


import apishop.entity.Account;
import apishop.entity.OrderDetail;
import apishop.entity.Product;
import apishop.model.dto.EvaluateDto;
import apishop.model.dto.SearchCriteria;
import apishop.model.response.EvaluateResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface EvaluateService {
    EvaluateDto save(EvaluateDto evaluate,
                     Account account,
                     Product product,
                     OrderDetail orderDetail);

    EvaluateResponse findById(String id);

    EvaluateDto findEvaluateById(String id);

    Page<EvaluateResponse> findAll(SearchCriteria searchCriteria);

    Page<EvaluateResponse> findAllAccountId(String accountId, SearchCriteria searchCriteria);

    Page<EvaluateResponse> findAllProductId(String productId, SearchCriteria searchCriteria);
}
