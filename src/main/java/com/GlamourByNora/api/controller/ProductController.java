package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.ProductDto;
import com.GlamourByNora.api.model.Product;
import com.GlamourByNora.api.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/newproduct")
    public ResponseEntity<?> createNewProduct(@Valid @RequestBody ProductDto productDto){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setFragranceFamily(productDto.getFragranceFamily());
        product.setSize(productDto.getSize());
        product.setType(productDto.getType());
        product.setGender(productDto.getGender());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setImageUrl(productDto.getImageUrl());
        product.setIngredients(productDto.getIngredients());
        product.setProductionDate(productDto.getProductionDate());
        product.setExpiryDate(productDto.getExpiryDate());
        product.setAvailability(productDto.isAvailability());
        product.setCountryOfOrigin(productDto.getCountryOfOrigin());
        productRepository.save(product);
        return new ResponseEntity<>("Successfully Created...",HttpStatus.CREATED);
    }
    @GetMapping("/productlist")
    public List<Product> getProductList(){
        return productRepository.findAll();
    }
    @PutMapping("/product/update/{id}")
    public ResponseEntity<?> updateProductById(@Valid @PathVariable(name = "id") Long id, @RequestBody ProductDto productDto){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setFragranceFamily(productDto.getFragranceFamily());
        product.setSize(productDto.getSize());
        product.setType(productDto.getType());
        product.setGender(productDto.getGender());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setImageUrl(productDto.getImageUrl());
        product.setIngredients(productDto.getIngredients());
        product.setProductionDate(productDto.getProductionDate());
        product.setExpiryDate(productDto.getExpiryDate());
        product.setAvailability(productDto.isAvailability());
        product.setCountryOfOrigin(productDto.getCountryOfOrigin());
        productRepository.save(product);
        return new ResponseEntity<>("Product Successfully Updated...",HttpStatus.OK);
    }
    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable( name = "id") Long productId){
        productRepository.deleteById(productId);
        return new ResponseEntity<>("Product Successfully Deleted...",HttpStatus.OK);
    }


}
