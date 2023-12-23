package com.GlamourByNora.api.service;

import com.GlamourByNora.api.dto.ProductDto;
import com.GlamourByNora.api.model.Product;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    public ResponseEntity<?> createNewProduct(ProductDto productDto, HttpServletRequest request);
    public List<Product> getProductList();
    public ResponseEntity<?> getProductById(Long productId);
    public ResponseEntity<?> updateProductById(Long productId, ProductDto productDto);
    public ResponseEntity<?> deleteProductById(Long productId);
}
