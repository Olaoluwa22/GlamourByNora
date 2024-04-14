package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.ProductDto;
import com.GlamourByNora.api.model.Product;
import com.GlamourByNora.api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @PostMapping("/create-a-product")
    public ResponseEntity<?> createNewProduct(@Valid @RequestBody ProductDto productDto){
       return productService.createNewProduct(productDto);
    }
    @GetMapping("/list-of-products")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable(name = "productId") Long productId){
        return productService.getProductById(productId);
    }
    @GetMapping("/page-list")
    public ResponseEntity<?> getProductByPageable(@RequestParam int page, @RequestParam int size){
        return productService.getProductByPageable(page, size);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProductById(@Valid @PathVariable(name = "id") Long id, @RequestBody ProductDto productDto){
        return productService.updateProductById(id, productDto);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable( name = "id") Long productId){
        return productService.deleteProductById(productId);
    }
}
