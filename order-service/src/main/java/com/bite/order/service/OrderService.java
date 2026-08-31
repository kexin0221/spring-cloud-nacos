package com.bite.order.service;

import com.bite.order.mapper.OrderMapper;
import com.bite.order.model.OrderInfo;
import com.bite.order.model.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RestTemplate restTemplate;

    public OrderInfo selectOrderById(Integer orderId) {
        OrderInfo orderInfo = orderMapper.selectOrderById(orderId);
        String url = "http://product-service/product/" + orderInfo.getProductId();
        log.info("url:{}", url);
        ProductInfo productInfo = restTemplate.getForObject(url, ProductInfo.class);
        orderInfo.setProductInfo(productInfo);
        return orderInfo;
    }
}
