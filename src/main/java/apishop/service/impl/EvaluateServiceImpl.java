package apishop.service.impl;

import apishop.entity.Account;
import apishop.entity.Evaluate;
import apishop.entity.OrderDetail;
import apishop.entity.Product;
import apishop.model.dto.EvaluateDto;
import apishop.model.dto.SearchCriteria;
import apishop.model.mapper.AccountMapper;
import apishop.model.mapper.EvaluateMapper;
import apishop.model.mapper.ProductMapper;
import apishop.model.response.EvaluateResponse;
import apishop.repository.EvaluateRepository;
import apishop.repository.OrderDetailRepository;
import apishop.service.EvaluateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static apishop.util.method.Search.getPageable;

@Service
@RequiredArgsConstructor
public class EvaluateServiceImpl implements EvaluateService {
    private final EvaluateRepository evaluateRepository;
    private final EvaluateMapper evaluateMapper;
    private final AccountMapper accountMapper;
    private final ProductMapper productMapper;
    private final OrderDetailRepository orderDetailRepository;
    /*
     * create new Evaluate
     * return new Evaluate
     */

    @Override
    public EvaluateDto save(EvaluateDto evaluateDto,
                            Account account,
                            Product product,
                            OrderDetail orderDetail
    ) {
        if(orderDetail != null){
            orderDetail.setIsEvaluate(true);
            orderDetailRepository.save(orderDetail);
        }
        Evaluate evaluate = evaluateRepository.save(
                evaluateMapper.applyToEvaluate(evaluateDto, account, product));



        return evaluateMapper.apply(evaluate);
    }

    /*
     * Find Evaluate by Id
     * @param id
     * return Evaluate
     */

    @Override
    public EvaluateResponse findById(String id) {
        Optional<Evaluate> evaluate = evaluateRepository.findById(id);
        return EvaluateResponse
                .apply(evaluateMapper.apply(evaluate.get()),
                        productMapper.apply(evaluate.get().getProduct()),
                        accountMapper.apply(evaluate.get().getAccount()));
    }

    @Override
    public EvaluateDto findEvaluateById(String id) {
        Optional<Evaluate> evaluate = evaluateRepository.findById(id);
        return evaluateMapper.apply(evaluate.get());
    }

    /*
     * Update Evaluate
     * return Evaluate
     */

    /*
     * Find all Evaluate
     * @param page and size
     */
    @Override
    public Page<EvaluateResponse> findAll(SearchCriteria searchCriteria) {
        Page<Evaluate> evaluates = evaluateRepository.findAll(getPageable(searchCriteria));
        return getEvaluateResponsePage(evaluates);
    }

    private PageImpl<EvaluateResponse> getEvaluateResponsePage(Page<Evaluate> evaluates) {
        List<EvaluateResponse> evaluateResponses = evaluates.getContent().stream()
                .map(evaluate -> EvaluateResponse
                        .apply(evaluateMapper.apply(evaluate),
                                productMapper.apply(evaluate.getProduct()),
                                accountMapper.apply(evaluate.getAccount())))
                .collect(Collectors.toList());

        return new PageImpl<>(evaluateResponses, evaluates.getPageable(), evaluates.getTotalElements());
    }


    @Override
    public Page<EvaluateResponse> findAllAccountId(String accountId, SearchCriteria searchCriteria) {
        Page<Evaluate> evaluates = evaluateRepository.findAllByAccount_Id(accountId, getPageable(searchCriteria));
        return getEvaluateResponsePage(evaluates);
    }

    @Override
    public Page<EvaluateResponse> findAllProductId(String productId, SearchCriteria searchCriteria) {
        Page<Evaluate> evaluates = evaluateRepository.findAllByProduct_Id(productId, getPageable(searchCriteria));
        return getEvaluateResponsePage(evaluates);
    }
}