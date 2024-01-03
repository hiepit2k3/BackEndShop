package apishop.service.impl;

import apishop.entity.Order;
import apishop.entity.OrderDetail;
import apishop.entity.ProductVariant;
import apishop.model.dto.OrderDetailDto;
import apishop.model.dto.SearchCriteria;
import apishop.model.mapper.OrderDetailMapper;
import apishop.repository.OrderDetailRepository;
import apishop.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

import static apishop.util.method.Search.getPageable;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    //region
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;
//    private final S3AmazonService s3AmazonService;
    //endregion

    @Override
    public Page<OrderDetailDto> findAllOrderDetail(SearchCriteria searchCriteria){
        Page<OrderDetail> orderDetails = orderDetailRepository.findAll(getPageable(searchCriteria));
        return orderDetails.map(orderDetailMapper::apply);
    }

    @Override
    public List<OrderDetailDto> findAllByOrderId(String orderId){
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderId(orderId);
        return orderDetails.stream()
                .map(orderDetailMapper::apply)
                .toList();
    }


    @Override
    public List<OrderDetailDto> saveAll(List<OrderDetailDto> orderDetailDtoList,
                                        List<ProductVariant> productVariantList,
                                        Order order) {
        List<OrderDetail> list =
                orderDetailRepository.saveAll(
                        orderDetailMapper.applyAllToOrderDetail(
                                orderDetailDtoList, productVariantList, order));
        return list.stream()
                .map(orderDetailMapper::apply)
                .toList();
    }

}
