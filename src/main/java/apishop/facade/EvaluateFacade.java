package apishop.facade;

import apishop.entity.Account;
import apishop.entity.Evaluate;
import apishop.entity.OrderDetail;
import apishop.entity.Product;
import apishop.exception.common.DescriptionException;
import apishop.exception.common.IdMustBeNullException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.EvaluateDto;
import apishop.model.dto.SearchCriteria;
import apishop.model.response.EvaluateResponse;
import apishop.repository.OrderDetailRepository;
import apishop.repository.ProductRepository;
import apishop.service.EvaluateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static apishop.util.method.Authentication.getAccount;

@Service
@RequiredArgsConstructor
public class EvaluateFacade {

    private final EvaluateService evaluateService;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    //Tạo đánh giá
    public EvaluateDto create(EvaluateDto evaluateDto, String orderDetailId) throws ArchitectureException {
        if (evaluateDto.getId() != null)
            throw new IdMustBeNullException(Evaluate.class.getSimpleName());
        Account account = getAccount();
        OrderDetail orderDetail =
                orderDetailRepository.findByIdAndAccountId(orderDetailId,
                        account.getId());

        if(orderDetail == null || orderDetail.getIsEvaluate())
            throw new DescriptionException("This product has been evaluated or isn't your order");

        Product product = orderDetail.getProductVariant().getProduct();

        return evaluateService.save(evaluateDto, account, product, orderDetail);
    }

    //Tìm kiếm đánh giá theo id
    public EvaluateResponse findById(String id) throws ArchitectureException {
        return checkIdIsNotNull(id);
    }

    private EvaluateResponse checkIdIsNotNull(String id) throws EntityNotFoundException {
        EvaluateResponse evaluateResponse = evaluateService.findById(id);
        if (evaluateResponse == null) {
            throw new EntityNotFoundException();
        }
        return evaluateResponse;
    }

    //Cập nhật đánh giá
//    public EvaluateDto update(EvaluateDto evaluateDto, String id) throws ArchitectureException {
//
//        EvaluateDto exist = evaluateService.findEvaluateById(id);
//        if (exist == null) {
//            throw new EntityNotFoundException();
//        }
//
//        if(exist.getIsUpdate())
//            throw new DescriptionException("This evaluate has been updated");
//
//        evaluateDto.setId(id);
//        evaluateDto.setIsUpdate(true);
//
//        Account account = getAccount();
//        Product product = productRepository
//                .findByIdWithAccountHaveOrder( account.getId(),evaluateDto.getProductId());
//
//        if(product == null)
//            throw new DescriptionException("This product isn't your order");
//
//        return evaluateService.save(evaluateDto, account, product, null);
//    }

    //tìm kiếm tất cả đánh giá
    public Page<EvaluateResponse> findAll(SearchCriteria searchCriteria) throws ArchitectureException{
        Page<EvaluateResponse> list = evaluateService.findAll(searchCriteria);
        if (list.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return list;
    }
    //tìm kiếm tất cả đánh giá theo id tài khoản
    public Page<EvaluateResponse> findAllAccountId(SearchCriteria searchCriteria) throws ArchitectureException{
        Account account = getAccount();
        Page<EvaluateResponse> evaluateDtos = evaluateService.findAllAccountId(account.getId(), searchCriteria);
        if (evaluateDtos.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return evaluateDtos;
    }

    //tìm kiếm tất cả đánh giá theo id sản phẩm
    public Page<EvaluateResponse> findAllProductId(String productId, SearchCriteria searchCriteria) throws ArchitectureException{
        Page<EvaluateResponse> list = evaluateService.findAllProductId(productId, searchCriteria);
        if (list.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return list;
    }
}
