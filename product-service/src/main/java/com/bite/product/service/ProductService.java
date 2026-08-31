package com.bite.product.service;

import com.bite.product.mapper.ProductMapper;
import com.bite.product.model.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    public ProductInfo selectProductById(Integer id) {
        log.info("接收到参数:{}", id);
        return productMapper.selectProductById(id);
    }
}
