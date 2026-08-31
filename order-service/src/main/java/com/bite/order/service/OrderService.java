package com.bite.order.service;

import com.bite.order.mapper.OrderMapper;
import com.bite.order.model.OrderInfo;
import com.bite.order.model.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaServiceInstance;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    // 计数器
    private static AtomicInteger count = new AtomicInteger(1);

    public OrderInfo selectOrderById(Integer orderId) {
        OrderInfo orderInfo = orderMapper.selectOrderById(orderId);
//        // 根据应用名称获取服务列表
//        List<ServiceInstance> instances = discoveryClient.getInstances("product-service");
//        // 服务可能有多个，轮询获得实例
//        int index = count.getAndIncrement() % instances.size();
//        String uri = instances.get(index).getUri().toString();
        String url = "http://product-service/product/" + orderInfo.getProductId();
        log.info("远程调用url:{}", url);
        ProductInfo productInfo = restTemplate.getForObject(url, ProductInfo.class);
        orderInfo.setProductInfo(productInfo);
        return orderInfo;
    }
}
