package com.example.demo.service;

import com.example.demo.controller.dto.ProductDto;
import com.example.demo.controller.response.exception.SuperMarketException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.util.SystemUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(ProductDto productDto) {
        Product product = SystemUtil.castPropsToDestination(productDto, Product.class);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductDto newProductDto) {
        Product product = getProductById(id);
        SystemUtil.copyPropsFromSrcToDest(newProductDto, product);
        return productRepository.saveAndFlush(product);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        if (product != null)
            productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new SuperMarketException("Product not found"));
    }
}
