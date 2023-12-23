package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.ProductDto;
import com.GlamourByNora.api.model.Product;
import com.GlamourByNora.api.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/product/create")
    public ResponseEntity<?> createNewProduct(@Valid @RequestBody ProductDto productDto, HttpServletRequest request){
       return productService.createNewProduct(productDto, request);
    }
    @GetMapping("/product/list")
    public List<Product> getProductList(){
        return productService.getProductList();
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductList(@PathVariable(name = "productId") Long productId){
        return productService.getProductById(productId);
    }
    @PutMapping("/product/update/{id}")
    public ResponseEntity<?> updateProductById(@Valid @PathVariable(name = "id") Long id, @RequestBody ProductDto productDto){
        return productService.updateProductById(id, productDto);
    }
    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable( name = "id") Long productId){
        return productService.deleteProductById(productId);
    }
}
