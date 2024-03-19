package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.ProductDto;
import com.GlamourByNora.api.model.Product;
import com.GlamourByNora.api.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @PostMapping("/product-create")
    public ResponseEntity<?> createNewProduct(@Valid @RequestBody ProductDto productDto, HttpServletRequest request){
       return productService.createNewProduct(productDto, request);
    }
    @GetMapping("/product/list")
    public List<Product> getProductList(){
        return productService.getProductList();
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable(name = "productId") Long productId){
        return productService.getProductById(productId);
    }
    @GetMapping("/product/page-list")
    public ResponseEntity<?> getProductByPageable(@RequestParam int page, @RequestParam int size){
        return productService.getProductByPageable(page, size);
    }
    @PutMapping("/product/update/{id}")
    public ResponseEntity<?> updateProductById(@Valid @PathVariable(name = "id") Long id, @RequestBody ProductDto productDto, HttpServletRequest request){
        return productService.updateProductById(id, productDto, request);
    }
    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable( name = "id") Long productId, HttpServletRequest request){
        return productService.deleteProductById(productId, request);
    }
}
