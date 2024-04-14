package com.GlamourByNora.api.service;

import com.GlamourByNora.api.dto.ProductDto;
import com.GlamourByNora.api.model.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<?> createNewProduct(ProductDto productDto);
    List<Product> getAllProducts();
    ResponseEntity<?> getProductById(Long productId);
    ResponseEntity<?> getProductByPageable(int page, int size);
    ResponseEntity<?> updateProductById(Long productId, ProductDto productDto);
    ResponseEntity<?> deleteProductById(Long productId);
}